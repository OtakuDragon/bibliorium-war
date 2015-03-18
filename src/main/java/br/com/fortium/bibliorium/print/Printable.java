package br.com.fortium.bibliorium.print;

import java.util.Map;

public interface Printable {
	String FILE_EXTENSION = ".txt";
	String RELATION       = "=";
	String SEPARATOR      = "_";
	String LINE_BREAK     = "\n";
	String OUTPUT_FOLDER  = "C:\\bibliorium\\output\\";
	
	Map<String,String> getPrintableInfo();
	String getName();
}
