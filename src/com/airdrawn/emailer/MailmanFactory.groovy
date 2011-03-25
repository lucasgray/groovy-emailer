package com.airdrawn.emailer

/**
 * Use to create a new Mailman object.  This hides the implmentation from the client interface,
 * as well as how to instantiate.
 * 
 * @author LEG238
 *
 */
class MailmanFactory {

	static Mailman getEmailer() {
		new MailmanImpl()
	}
	
}
