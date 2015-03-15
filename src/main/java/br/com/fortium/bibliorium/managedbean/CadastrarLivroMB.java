package br.com.fortium.bibliorium.managedbean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.primefaces.model.UploadedFile;

import br.com.fortium.bibliorium.enumeration.DialogType;
import br.com.fortium.bibliorium.persistence.entity.Categoria;
import br.com.fortium.bibliorium.persistence.entity.Livro;
import br.com.fortium.bibliorium.service.CategoriaService;
import br.com.fortium.bibliorium.validation.CadastroLivroValidator;
import br.com.fortium.bibliorium.validation.exception.ValidationException;

@Named
@RequestScoped
public class CadastrarLivroMB extends AbstractManagedBean<CadastrarLivroMB> {

	private static final long serialVersionUID = 9169829006153798046L;

	@EJB
	private CategoriaService categoriaService; 
	
	private Livro livro;
	private String quantidade;
	private UploadedFile foto;
	
	private List<Categoria> categorias;
	
	public CadastrarLivroMB() {
		super(CadastrarLivroMB.class, new CadastroLivroValidator());
	}
	
	@PostConstruct
	public void init(){
		initCategorias();
		setLivro(new Livro());
	}
	
	private void initCategorias(){
		categorias =  categoriaService.buscarCategorias();
		categorias.add(new Categoria(0, "a"));
	}
	
	public void cadastrarLivro(){
		try{
			validate();
			getDialogUtil().showDialog(DialogType.SUCCESS, "Livro cadastrado com sucesso");
		}catch(ValidationException e){
			getDialogUtil().showDialog(DialogType.ERROR, e.getMessage());
			getLogger().error(e);
		}
		
	}

	public UploadedFile getFoto() {
		return foto;
	}
	public void setFoto(UploadedFile foto) {
		this.foto = foto;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}

	public String getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(String quantidade) {
		this.quantidade = quantidade;
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}
}
