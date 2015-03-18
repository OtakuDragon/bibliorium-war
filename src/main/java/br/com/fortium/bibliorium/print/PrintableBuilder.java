package br.com.fortium.bibliorium.print;

import java.util.ArrayList;
import java.util.List;

import br.com.fortium.bibliorium.persistence.entity.Copia;
import br.com.fortium.bibliorium.persistence.entity.Livro;

public class PrintableBuilder {

	public static Printable[] buildEtiquetas(List<Copia> copias){
		List<Printable> retorno = new ArrayList<Printable>();
		
		for (Copia copia : copias) {
			EtiquetaPrintable printable = new EtiquetaPrintable();
			
			Livro livro = copia.getLivro();
			
			printable.setTitulo  (livro.getTitulo());
			printable.setIsbn    (livro.getIsbn());
			printable.setCodLivro(livro.toString());
			printable.setCodCopia(copia.getId().toString());
			
			retorno.add(printable);
		}
		return retorno.toArray(new Printable[]{});
	}
}
