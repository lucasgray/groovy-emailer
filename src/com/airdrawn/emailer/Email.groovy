package com.airdrawn.emailer

import com.airdrawn.emailer.util.PropertiesManager

/**
 * PO(J/G)O to represent an email.  Default from field is taken from properties file.  
 * 
 * @author LEG238
 *
 */
class Email {
	
	static {
		DEFAULT_FROM = PropertiesManager.getInstance().getProperty("defaultFrom")
		List.metaClass.send {->
			Mailman mm = MailmanFactory.getEmailer()
			mm.sendEmails(delegate)
		 }
	}
	
	static final String DEFAULT_FROM
	
	/**
	 * List of email recipients
	 */
	List<String> to
	
	/**
	 * List of recipients to cc
	 */
	List<String> cc
	/**
	 * From field.  Default is from properties file
	 */
	String from = DEFAULT_FROM
	/**
	* Subject of email
	*/
	String subject
	/**
	 * String representation of the email body
	 */
	String message
	
	/**
	 * List of attachments to be emailed
	 */
	List<Attachment> attachments = []

	@Override
	public String toString() {
		return "TO: ${to} FROM: ${from} CC: ${cc} SUBJECT: ${subject} MESSAGE: ${message} ATTACHMENTS: ${attachments}"
	}
	
	/**
	 * Shortcut for <pre>MailmanFactory.getEmailer().sendEmail(this)</pre>
	 */
	public void send() {
		MailmanFactory.getEmailer().sendEmail(this)
	}
	
	
}
