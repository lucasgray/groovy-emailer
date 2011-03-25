package com.airdrawn.emailer

/**
 * Indicates that an error occurred when trying to send email.
 * 
 * @author LEG238
 *
 */
class MailmanException extends RuntimeException {

	public MailmanException(String message, Throwable cause) {
		super(message, cause);
	}

}
