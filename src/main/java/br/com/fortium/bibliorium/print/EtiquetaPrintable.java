package br.com.fortium.bibliorium.print;

import java.util.LinkedHashMap;
import java.util.Map;

public class EtiquetaPrintable implements Printable {

	private Map<String,String> printableInfo;
	public static String NAME = "ETIQUETA_LIVRO";
	
	public EtiquetaPrintable() {
		printableInfo = new LinkedHashMap<String,String>();
	}
	
	@Override
	public Map<String, String> getPrintableInfo() {
		return printableInfo;
	}

	public void setTitulo(String titulo) {
		printableInfo.put("T�tulo", titulo);
	}


	public void setIsbn(String isbn) {
		printableInfo.put("ISBN", isbn);
	}


	public void setCodLivro(String codLivro) {
		printableInfo.put("C�d. Livro", codLivro);
	}


	public void setCodCopia(String codCopia) {
		printableInfo.put("C�d. Copia", codCopia);
	}

}
