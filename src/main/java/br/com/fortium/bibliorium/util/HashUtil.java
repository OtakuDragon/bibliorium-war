package br.com.fortium.bibliorium.util;

import java.util.Date;

public class HashUtil {
	
	public static String encode(Date date){
		StringBuilder time = new StringBuilder(String.valueOf(date.getTime())).reverse();
		
		long start  = Long.parseLong(time.substring(0, 4));
		long end    = Long.parseLong(time.substring(time.length() -4, time.length()));
		long result = start + end;

		time.replace(time.length() -4, time.length(), String.valueOf(result));

		return time.toString();
	}
	
	public static boolean validate(Date date, String code){
		return encode(date).equalsIgnoreCase(code);
	}

}
