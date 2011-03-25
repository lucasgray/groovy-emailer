package com.airdrawn.emailer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestEmailerImplJava {
	
	public TestEmailerImplJava() {
		
	}
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testSendSomeEmails() {
		Email email= new Email();
		email.setTo(Arrays.asList(new String[]{"lucas.e.gray@gmail.com"}));
		email.setSubject("hi");
		email.setMessage("howdy");
		
		email.send();
		
		Email email2= new Email();
		email2.setTo(Arrays.asList(new String[]{"lucas.e.gray@gmail.com"}));
		email2.setSubject("hi");
		email2.setMessage("howdy");
		
		Email email3= new Email();
		email3.setTo(Arrays.asList(new String[]{"lucas.e.gray@gmail.com"}));
		email3.setSubject("hi");
		email3.setMessage("howdy");

		List<Email> li = new ArrayList<Email>();
		li.add(email);
		li.add(email2);
		li.add(email3);
		
		Mailman mm = MailmanFactory.getEmailer();
		mm.sendEmails(li);
	}

}
