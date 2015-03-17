package br.com.fortium.bibliorium.data.formatter.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.fortium.bibliorium.persistence.entity.Categoria;
import br.com.fortium.bibliorium.persistence.entity.Copia;
import br.com.fortium.bibliorium.persistence.entity.Livro;
import br.com.fortium.bibliorium.persistence.enumeration.EstadoCopia;
import br.com.fortium.bibliorium.service.CategoriaService;
import br.com.fortium.bibliorium.util.AbstractServiceableUtility;

public class CadastrarLivroDataFormatter extends AbstractServiceableUtility<CategoriaService> implements ViewDataFormatter<List<Copia>>{
	
	private List<Copia> data;
	
	private Livro livro;
	private String titulo;
	private String edicao;
	private String editora;
	private String numPaginas;
	private String autores;
	private String quantidade;
	private String isbn;
	private String categoria;

	public CadastrarLivroDataFormatter() {
		livro = new Livro();
	}
	
	@Override
	public List<Copia> getFormattedData() {
		data = new ArrayList<Copia>();
		
		Categoria catObj = getService().buscar(categoria);
		
		livro.setCategoria(catObj);
		livro.setTitulo(titulo);
		livro.setEdicao(edicao);
		livro.setEditora(editora);
		livro.setDataCadastro(new Date());
		livro.setNumPaginas(Integer.parseInt(numPaginas));
		livro.setAutores(autores);
		livro.setIsbn(isbn);
		
		for (int i = 0; i < Integer.parseInt(quantidade); i++) {
			data.add(new Copia(livro, EstadoCopia.DISPONIVEL));
		}
		
		return data;
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getEdicao() {
		return edicao;
	}

	public void setEdicao(String edicao) {
		this.edicao = edicao;
	}

	public String getEditora() {
		return editora;
	}

	public void setEditora(String editora) {
		this.editora = editora;
	}

	public String getNumPaginas() {
		return numPaginas;
	}

	public void setNumPaginas(String numPaginas) {
		this.numPaginas = numPaginas;
	}

	public String getAutores() {
		return autores;
	}

	public void setAutores(String autores) {
		this.autores = autores;
	}

	public String getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(String quantidade) {
		this.quantidade = quantidade;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
}
