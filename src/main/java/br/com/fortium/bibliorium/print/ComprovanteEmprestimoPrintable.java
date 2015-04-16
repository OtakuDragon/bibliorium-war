package br.com.fortium.bibliorium.print;

import java.util.LinkedHashMap;
import java.util.Map;

public class ComprovanteEmprestimoPrintable implements Printable {

	private Map<String,String> printableInfo;
	public static String NAME = "COMPROVANTE_EMPRESTIMO";
	
	public ComprovanteEmprestimoPrintable() {
		printableInfo = new LinkedHashMap<String,String>();
	}
	
	@Override
	public Map<String, String> getPrintableInfo() {
		return printableInfo;
	}

	public void setNome(String nome) {
		printableInfo.put("Nome", nome);
	}

	public void setCpf(String cpf) {
		printableInfo.put("CPF", cpf);
	}

	public void setTituloLivro(String tituloLivro) {
		printableInfo.put("Título do Livro", tituloLivro);
	}

	public void setIsbn(String isbn) {
		printableInfo.put("ISBN", isbn);
	}

	public void setCodCopia(String codCopia) {
		printableInfo.put("Cód. Cópia", codCopia);
	}

	public void setTipo(String tipo) {
		printableInfo.put("Tipo", tipo);
	}
	
	public void setCodEmprestimo(String codEmprestimo) {
		printableInfo.put("Cód Emprestimo", codEmprestimo);
	}

	public void setDataEmprestimo(String dataEmprestimo) {
		printableInfo.put("Data Empréstimo", dataEmprestimo);
	}

	public void setDataPrevista(String dataDevolucao) {
		printableInfo.put("Data prevista", dataDevolucao);
	}

	public void setCodValidacao(String codValidacao) {
		printableInfo.put("Cod. Validação", codValidacao);
	}
	
	public void setDataFechamento(String dataFechamento) {
		printableInfo.put("Data Fechamento", dataFechamento);
	}
	
	public void setValorMulta(String valorMulta) {
		printableInfo.put("Multa", valorMulta);
	}

	public void setValorPago(String valorPago) {
		printableInfo.put("Valor Pago", valorPago);
	}

}
