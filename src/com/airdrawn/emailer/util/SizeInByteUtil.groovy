package com.airdrawn.emailer.util;

import java.text.DecimalFormat;

public class SizeInByteUtil {

	private static DecimalFormat oneDecimal = new DecimalFormat("0.0");
	
	/**
	* Given an integer, return a string that is in an approximate, but human
	* readable format.
	* It uses the bases 'k', 'm', and 'g' for 1024, 1024**2, and 1024**3.
	* @param number the number to format
	* @return a human readable form of the integer
	*/
   public static String humanReadableInt(long number) {
	   long absNumber = Math.abs(number);
	   double result = number;
	   String suffix = "";
	   
	   if (absNumber < 1024) {
		   return new DecimalFormat("##0.#").format(number)+"B"
	   } else if (absNumber < 1024 * 1024) {
		   result = number / 1024.0;
		   suffix = "KB";
	   } else if (absNumber < 1024 * 1024 * 1024) {
		   result = number / (1024.0 * 1024);
		   suffix = "MB";
	   } else {
		   result = number / (1024.0 * 1024 * 1024);
		   suffix = "GB";
	   }
	   return oneDecimal.format(result) + suffix;
   }
   
}
