package com.airdrawn.emailer;



/**
 * PO(J/G)O to represent an email attachment.
 *
 * @author LEG238
 *
 */
public class Attachment {
	
	/**
	 * Will become the name of the attached file in the email
	 */
	String filename
	/**
	*  MimeType, ie application/pdf
	*/
	String mimetype
	/**
	*  Byte array representation of the attachment
	*/
	byte[] data
	
	@Override
	public String toString() {
		def filesize = SizeInByteUtil.humanReadableInt(data.length)
		return "FILENAME: ${filename} MIMETYPE: ${mimetype} SIZE: ${filesize} "
	}
}
