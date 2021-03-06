package br.com.fortium.bibliorium.print;

import java.util.Map;

public interface Printable {
	String FILE_EXTENSION = ".txt";
	String RELATION       = "=";
	String SEPARATOR      = "_";
	String DIVISION       = "--------------------------------------------------------";
	String LINE_BREAK     = "\n";
	String DATA_HOLDER_KEY = "printableDataHolder";
	String PRINTED_FLAG_KEY = "printedFlag";
	
	Map<String,String> getPrintableInfo();
}
