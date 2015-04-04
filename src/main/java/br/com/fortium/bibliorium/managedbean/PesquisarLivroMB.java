package br.com.fortium.bibliorium.managedbean;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import br.com.fortium.bibliorium.persistence.entity.Categoria;
import br.com.fortium.bibliorium.persistence.entity.Livro;
import br.com.fortium.bibliorium.service.CategoriaService;
import br.com.fortium.bibliorium.service.LivroService;

@Named
@RequestScoped
public class PesquisarLivroMB extends AbstractManagedBean<PesquisarLivroMB> {

	private static final long serialVersionUID = -5949416609941531609L;

	private List<Livro>  livros;
	private Livro livroDetalhe;
	private Livro filtro;
	
	private List<Categoria> categorias;

	@EJB
	private CategoriaService categoriaService;
	@EJB
	private LivroService livroService;
	
	@Override
	protected void init() {
		livros = livroService.list();
		filtro = new Livro();
		setCategorias(categoriaService.buscarCategorias());
	}
	
	public void pesquisar(){
		livros = livroService.buscarPorFiltro(filtro);
		filtro = new Livro();
	}

	public Livro getFiltro() {
		return filtro;
	}

	public void setFiltro(Livro filtro) {
		this.filtro = filtro;
	}

	public List<Livro> getLivros() {
		return livros;
	}

	public void setLivros(List<Livro> livros) {
		this.livros = livros;
	}

	public Livro getLivroDetalhe() {
		return livroDetalhe;
	}

	public void setLivroDetalhe(Livro livroDetalhe) {
		this.livroDetalhe = livroDetalhe;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}
	
}
