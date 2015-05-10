package br.com.fortium.bibliorium.managedbean;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.fortium.bibliorium.enumeration.DialogType;
import br.com.fortium.bibliorium.persistence.entity.Categoria;
import br.com.fortium.bibliorium.persistence.entity.Livro;
import br.com.fortium.bibliorium.service.CategoriaService;
import br.com.fortium.bibliorium.service.CopiaService;
import br.com.fortium.bibliorium.service.LivroService;

@ManagedBean
@ViewScoped
public class EditarLivroMB extends AbstractManagedBean<EditarLivroMB> {

	private static final long serialVersionUID = 3217594983812828318L;
	
	private Livro livro;
	private Livro livroOriginal;
	private List<Categoria> categorias;
	
	@EJB
	private CategoriaService categoriaService;
	@EJB
	private LivroService livroService;
	@EJB
	private CopiaService copiaService;
	
	@Override
	protected void init() {
		livro = (Livro) extractSessionAttribute("livroEdit");
		if(livro == null){
			redirectToHome();
		}else{
			setCategorias(categoriaService.buscarCategorias());
			try {
				livroOriginal = (Livro) livro.clone();
			} catch (CloneNotSupportedException e) {
				getLogger().error(e);
			}
		}
	}
	
	public String salvar(){
		if(!livroOriginal.equals(livro)){
			livroService.update(livro);
			getDialogUtil().showDialog(DialogType.SUCCESS, "Livro alterado com sucesso!");
		}
		
		return "/pages/ALL/pesquisarLivro";
	}
	
	public String excluir(){
		if(livro.getCopiasIndisponiveis().size() > 0){
			getDialogUtil().showDialog(DialogType.ERROR, "Não é possivel excluir este livro, ele possui copias emprestadas ou reservadas.");
			return null;
		}else{
			copiaService.desativarCopias(livro);
			getDialogUtil().showDialog(DialogType.SUCCESS, "Livro removido com sucesso!");
			return "/pages/ALL/pesquisarLivro";
		}
	}
	
	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}

}
