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
		printableInfo.put("Título", titulo);
	}

	public void setCategoria(String categoria) {
		printableInfo.put("Categoria", categoria);
	}

	public void setIsbn(String isbn) {
		printableInfo.put("ISBN", isbn);
	}
	
	public void setCorredor(String corredor) {
		printableInfo.put("Corredor", corredor);
	}
	
	public void setEstante(String estante) {
		printableInfo.put("Estante", estante);
	}

	public void setPrateleira(String prateleira) {
		printableInfo.put("Prateleira", prateleira);
	}

	public void setCodLivro(String codLivro) {
		printableInfo.put("Cód. Livro", codLivro);
	}


	public void setCodCopia(String codCopia) {
		printableInfo.put("Cód. Copia", codCopia);
	}

}
