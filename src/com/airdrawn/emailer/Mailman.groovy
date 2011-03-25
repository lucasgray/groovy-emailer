package com.airdrawn.emailer

/**
 * Client interface for Mailman emailer.  
 * Can either send a single Email object, or a list of emails.
 * 
 * Needs an emailer.properties property file, with four properties:
 * 
 * <ul><li>sendEmail</li>
 * <li>emailOverride</li>
 * <li>emailServ</li>
 * <li>defaultFrom</li></ul>
 * 
 * An example of use:
 * 
 * Groovy version:
 * <pre>
 * send one email
 * def email = new Email(to:["lgray2@uwhealth.org"],subject:"email",message:"mornin",from:"noreply@uwhealth.org")
 * email.send()
 * 
 * send many emails
 * def emails = [email1,email2,email3]
 * emails.send() //using ExpandoMetaClass on java.util.list 
 * 
 * 
 * Java version:
import com.lowagie.text.Document 
import com.lowagie.text.Paragraph 
import com.lowagie.text.pdf.PdfWriter 
import org.ehealth.emailer.Email
import org.ehealth.emailer.Mailman
import org.ehealth.emailer.MailmanFactory

...snip...

//get the mailman object 
Mailman mailman = MailmanFactory.getEmailer()

//create the email
def email = new Email(to:["lgray2@uwhealth.org"],subject:"subject",message:"message",from:"noreply@uwhealth.org")

//create a pdf document using iText
Document document = new Document()
ByteArrayOutputStream baos = new ByteArrayOutputStream()
PdfWriter.getInstance(document,baos)
document.open()
document.add(new Paragraph("Hello World"))		
document.close()

//add the pdf as an attachment to the email
email.attachments << new Attachment(filename: "hello.pdf",data: baos.toByteArray(), mimetype: 'application/pdf')

//send the email
mailman.sendEmail(email)
 * </pre>
 * 
 * @author LEG238
 *
 */
interface Mailman {
	
	void sendEmail(Email email)
	void sendEmails(List<Email> emails)
}
