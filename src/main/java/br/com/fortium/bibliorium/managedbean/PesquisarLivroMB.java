package br.com.fortium.bibliorium.managedbean;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import br.com.fortium.bibliorium.persistence.entity.Livro;
import br.com.fortium.bibliorium.service.LivroService;

@Named
@RequestScoped
public class PesquisarLivroMB extends AbstractManagedBean<PesquisarLivroMB> {

	private static final long serialVersionUID = -5949416609941531609L;

	private List<Livro>  livros;
	private Livro livroDetalhe;

	@EJB
	private LivroService livroService;
	
	@Override
	protected void init() {
		livros = livroService.list();
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
	
}
