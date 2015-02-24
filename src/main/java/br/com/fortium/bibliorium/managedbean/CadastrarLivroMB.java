package br.com.fortium.bibliorium.managedbean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.primefaces.model.UploadedFile;

import br.com.fortium.bibliorium.persistence.entity.Categoria;
import br.com.fortium.bibliorium.service.CategoriaService;

@Named
@RequestScoped
public class CadastrarLivroMB extends AbstractManagedBean {
	
	private static final long serialVersionUID = 9169829006153798046L;

	@EJB
	private CategoriaService categoriaService; 
	
	private String titulo;
	private String editora;
	private String autores;
	private String isbn;
	private String edicao;
	private String categoria;
	private List<String> opcoesCategorias;
	private Integer numPaginas;
	private Integer quantidade;
	private UploadedFile foto;
	
	@PostConstruct
	public void init(){
		initCategorias();
	}
	
	private void initCategorias(){
		List<Categoria> categorias =  categoriaService.buscarCategorias();
		
		opcoesCategorias = new ArrayList<String>();
		
		for (Categoria categoria : categorias) {
			opcoesCategorias.add(categoria.getNome());
		}
	}
	
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getEditora() {
		return editora;
	}
	public void setEditora(String editora) {
		this.editora = editora;
	}
	public String getAutores() {
		return autores;
	}
	public void setAutores(String autores) {
		this.autores = autores;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getEdicao() {
		return edicao;
	}
	public void setEdicao(String edicao) {
		this.edicao = edicao;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public List<String> getOpcoesCategorias() {
		return opcoesCategorias;
	}
	public void setOpcoesCategorias(List<String> opcoesCategorias) {
		this.opcoesCategorias = opcoesCategorias;
	}
	public Integer getNumPaginas() {
		return numPaginas;
	}
	public void setNumPaginas(Integer numPaginas) {
		this.numPaginas = numPaginas;
	}
	public Integer getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	public UploadedFile getFoto() {
		return foto;
	}
	public void setFoto(UploadedFile foto) {
		this.foto = foto;
	}
}
