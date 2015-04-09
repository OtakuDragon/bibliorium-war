package br.com.fortium.bibliorium.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.jboss.logging.Logger;

import br.com.fortium.bibliorium.persistence.enumeration.TipoUsuario;

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
	
	public static Calendar getCalendar(Date date){
		Calendar calendar = Calendar.getInstance();
		if(date != null){
			calendar.setTime(date);
		}
		return calendar;
	}
	
	public static Date calcularDataDevolucao(TipoUsuario tipo){
		switch(tipo){
			case ALUNO:
			case BIBLIOTECARIO:
				return addDiasSemana(new Date(), 7);
			case PROFESSOR:
				return addDiasSemana(new Date(), 15);
			default:
				return null;
		}
		
	}
	
	public static Date calcularDataFimReserva(){
		return addDiasSemana(new Date(), 7);
	}
	
	private static Date addDiasSemana(Date date, Integer qtd){
		Calendar calendar = getCalendar(date);
		calendar.add(Calendar.DAY_OF_WEEK, qtd);
		
		if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY){
			calendar.add(Calendar.DAY_OF_WEEK, 2);
		}else if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
			calendar.add(Calendar.DAY_OF_WEEK, 1);
		}
		
		return calendar.getTime();
	}
}
