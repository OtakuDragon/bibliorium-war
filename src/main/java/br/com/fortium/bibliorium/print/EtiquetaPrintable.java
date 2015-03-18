package br.com.fortium.bibliorium.print;

import java.util.LinkedHashMap;
import java.util.Map;

public class EtiquetaPrintable implements Printable {

	private Map<String,String> printableInfo;
	
	public EtiquetaPrintable() {
		printableInfo = new LinkedHashMap<String,String>();
	}
	
	@Override
	public Map<String, String> getPrintableInfo() {
		return printableInfo;
	}

	@Override
	public String getName() {
		return "ETIQUETA_LIVRO";
	}

	public void setTitulo(String titulo) {
		printableInfo.put("Título", titulo);
	}


	public void setIsbn(String isbn) {
		printableInfo.put("ISBN", isbn);
	}


	public void setCodLivro(String codLivro) {
		printableInfo.put("Cód. Livro", codLivro);
	}


	public void setCodCopia(String codCopia) {
		printableInfo.put("Cód. Copia", codCopia);
	}

}
