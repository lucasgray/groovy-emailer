package com.airdrawn.emailer

import java.util.List 
import java.util.Properties

import javax.activation.DataHandler 
import javax.activation.DataSource 
import javax.mail.Address 
import javax.mail.BodyPart 
import javax.mail.Message 
import javax.mail.Multipart 
import javax.mail.Session 
import javax.mail.Transport
import javax.mail.internet.InternetAddress 
import javax.mail.internet.MimeBodyPart 
import javax.mail.internet.MimeMessage
import javax.mail.internet.MimeMultipart 
import javax.mail.internet.MimeMessage.RecipientType
import javax.mail.util.ByteArrayDataSource 
import com.airdrawn.emailer.util.PropertiesManager 
import org.slf4j.Logger 
import org.slf4j.LoggerFactory;

import java.util.concurrent.*

/**
 * Implementation of email stuff.
 * 
 * @author LEG238
 *
 */
protected class MailmanImpl implements Mailman {
	
	static final THREADS = 5
	static final String newLine = System.getProperty("line.separator")

	def props
	def session

	def actuallySendIt = Boolean.parseBoolean(PropertiesManager.getInstance().getProperty("sendEmail"))
	def sendToOverride = PropertiesManager.getInstance().getProperty("emailOverride")
	
	static final Logger logger = LoggerFactory.getLogger(Mailman.class)
	
	protected MailmanImpl() {

		props = new Properties()
		props.put "mail.smtp.host", PropertiesManager.getInstance().getProperty("emailServ") 
		
		session = Session.getDefaultInstance(props, null)
		session.setDebug false
		
	}
	
	/**
	 * sends an email 
	 * 
	 * @param Email single email
	 */
	@Override
	void sendEmail(Email email) throws MailmanException {
		try {
			if (actuallySendIt) {
				
				logger.info("Sending ${email}")
				Message message = new MimeMessage(session)
				
				if (sendToOverride) {
					message.setRecipient RecipientType.TO, new InternetAddress(sendToOverride)
				} else {
					Address[] iato = email.to.collect {new InternetAddress(it)}
					Address[] iacc = email.cc?.collect {new InternetAddress(it)}
					
					message.setRecipients RecipientType.TO, iato
					if (iacc) message.setRecipients RecipientType.CC, iacc
				}
				
				message.setFrom new InternetAddress(email.from)
				message.setSubject email.subject
				
				Multipart multipart = new MimeMultipart()
				
				MimeBodyPart messagePart = new MimeBodyPart()
				messagePart.setText email.message
				multipart.addBodyPart(messagePart)
				
				email.attachments?.each {
					
					BodyPart fileBodyPart = new MimeBodyPart()
					DataSource source = new ByteArrayDataSource(it.data,it.mimetype)
					fileBodyPart.setDataHandler(new DataHandler(source))
					fileBodyPart.setFileName(it.filename)
					
					multipart.addBodyPart(fileBodyPart)
				}
				
				message.setContent(multipart)
				
				Transport.send message
			} else {
				logSynchronizedMessage(email)
			}
		} catch (Exception e) {
			throw new MailmanException("error occurred trying to send an email",e);
		}
		
	}

	private synchronized void logSynchronizedMessage(Email email) {
		
		logger.info("[EMAIL] Would have sent this email:")
		logger.info("[EMAIL] Subject: ${email.subject}")
		logger.info("[EMAIL] To: ${email.to}")
		logger.info("[EMAIL] Cc: ${email.cc}")
		logger.info("[EMAIL] From: ${email.from}")
		logger.info("[EMAIL] Subject: ${email.subject}")
		logger.info("[EMAIL] Message: ${email.message}")
		
		email.attachments?.each {
			logger.info("[EMAIL] ATTACHMENT: ${it}")
		}
		
	}
	
	
	/**
	 * send a bunch of emails
	 * 
	 * @param List<Email> emails
	 */
	void sendEmails(List<Email> emails) {
		
		def threads = []
		ExecutorService threadExecutor = Executors.newFixedThreadPool(THREADS)
		
		emails.each{ email ->
			def th = new Thread({
				sendEmail(email)
			})
			logger.debug("putting thread in list")
			threads << th
		}
		
		threads.each {threadExecutor.execute it}
		threadExecutor.shutdown()
		threadExecutor.awaitTermination(1, TimeUnit.MINUTES)
		
	}
	
	/**
	 * send a bunch of emails
	 * 
	 * @param List<Email> emails
	 */
	void sendEmailsSerial(List<Email> emails) {
		emails.each{ email -> sendEmail(email) }
	}
}
