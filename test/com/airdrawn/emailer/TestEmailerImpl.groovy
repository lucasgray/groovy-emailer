package com.airdrawn.emailer;

import static org.junit.Assert.*;

import com.airdrawn.emailer.Email;
import com.airdrawn.emailer.Mailman;
import com.airdrawn.emailer.MailmanFactory;
import com.itextpdf.text.Document 
import com.itextpdf.text.Image 
import com.itextpdf.text.Paragraph 
import com.itextpdf.text.pdf.PdfReader 
import com.itextpdf.text.pdf.PdfStamper 
import com.itextpdf.text.pdf.PdfWriter 
import org.junit.After;
import org.junit.Before;
import org.junit.Test 

class TestEmailerImpl {


	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testSendSomeEmails() {
		Email email= new Email(to:["email@email.com"],subject:"email",message:"mornin",from:"email@email.com")
		
		email.send()
		
		Email email2= new Email(to:["email@email.com"],subject:"email",message:"mornin",from:"email@email.com")
		Email email3= new Email(to:["email@email.com"],subject:"email",message:"mornin",from:"email@email.com")
		
		def li = [email,email2,email3]
		
		li.send()
	}

}
