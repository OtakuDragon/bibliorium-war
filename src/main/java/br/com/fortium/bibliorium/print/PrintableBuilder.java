package br.com.fortium.bibliorium.print;

import java.math.BigDecimal;
import java.text.Format;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import br.com.fortium.bibliorium.persistence.entity.Copia;
import br.com.fortium.bibliorium.persistence.entity.Emprestimo;
import br.com.fortium.bibliorium.persistence.entity.Livro;
import br.com.fortium.bibliorium.persistence.entity.Usuario;
import br.com.fortium.bibliorium.util.DataUtil;
import br.com.fortium.bibliorium.util.HashUtil;

public class PrintableBuilder {

	public static enum TipoComprovante{EMPRESTIMO, DEVOLUCAO}
	
	private static Format currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt","br"));
	
	public static Printable[] buildEtiquetas(List<Copia> copias){
		List<Printable> retorno = new ArrayList<Printable>();
		
		for (Copia copia : copias) {
			EtiquetaPrintable printable = new EtiquetaPrintable();
			
			Livro livro = copia.getLivro();
			
			printable.setTitulo  (livro.getTitulo());
			printable.setIsbn    (livro.getIsbn());
			printable.setCodLivro(livro.getId().toString());
			printable.setCodCopia(copia.getId().toString());
			
			retorno.add(printable);
		}
		return retorno.toArray(new Printable[]{});
	}
	
	public static Printable buildComprovanteEmprestimo(Emprestimo emprestimo, TipoComprovante tipo){
		ComprovanteEmprestimoPrintable retorno = new ComprovanteEmprestimoPrintable();
		
		Usuario leitor = emprestimo.getUsuario();
		Livro   livro  = emprestimo.getCopia().getLivro();
		
		retorno.setNome          (leitor.getNome());
		retorno.setCpf           (leitor.getCpf());
		retorno.setTituloLivro   (livro.getTitulo());
		retorno.setIsbn          (livro.getIsbn());
		retorno.setCodCopia      (emprestimo.getCopia().getId().toString());
		retorno.setTipo          (emprestimo.getTipo().toString());
		retorno.setCodEmprestimo (emprestimo.getId().toString());
		retorno.setDataDevolucao (DataUtil.getDataS(emprestimo.getDataDevolucao(), "dd/MM/yyyy"));
		retorno.setDataEmprestimo(DataUtil.getDataS(emprestimo.getDataEmprestimo(), "dd/MM/yyyy"));
		if(tipo == TipoComprovante.EMPRESTIMO){
			retorno.setCodValidacao  (HashUtil.encode(emprestimo.getDataEmprestimo()));
		}else{
			retorno.setDataFechamento(DataUtil.getDataS(emprestimo.getDataFechamento(), "dd/MM/yyyy"));
			retorno.setCodValidacao  (HashUtil.encode(emprestimo.getDataFechamento()));
			retorno.setValorMulta(currencyFormat.format(emprestimo.getValorMulta() == null ? BigDecimal.ZERO : emprestimo.getValorMulta()));
		}
		
		return retorno;
	}
}
