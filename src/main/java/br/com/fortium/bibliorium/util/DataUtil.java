package br.com.fortium.bibliorium.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jboss.logging.Logger;

public class DataUtil {
	
	public static String getDataS(Date date, String pattern) {
		String dataS = null;
		
		if(date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			return sdf.format(date);
		}
		
		return dataS;
	}
	
	public static Date getDataD(String dateS, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			return sdf.parse(dateS);
		} catch (ParseException e) {
			Logger.getLogger(DataUtil.class).error(e);
		}
		
		return null;
	}
}
