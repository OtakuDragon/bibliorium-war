package br.com.fortium.bibliorium.managedbean;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import br.com.fortium.bibliorium.data.formatter.view.CadastrarLivroDataFormatter;
import br.com.fortium.bibliorium.enumeration.DialogType;
import br.com.fortium.bibliorium.managedbean.generic.AbstractManagedBean;
import br.com.fortium.bibliorium.metadata.Serviceable;
import br.com.fortium.bibliorium.metadata.Validator;
import br.com.fortium.bibliorium.persistence.entity.Categoria;
import br.com.fortium.bibliorium.persistence.entity.Copia;
import br.com.fortium.bibliorium.print.EtiquetaPrintable;
import br.com.fortium.bibliorium.print.PrintableBuilder;
import br.com.fortium.bibliorium.service.CategoriaService;
import br.com.fortium.bibliorium.service.CopiaService;
import br.com.fortium.bibliorium.service.LivroService;
import br.com.fortium.bibliorium.util.exception.PrintableException;
import br.com.fortium.bibliorium.util.exception.ValidationException;
import br.com.fortium.bibliorium.validation.CadastroLivroValidator;

@Named
@RequestScoped
public class CadastrarLivroMB extends AbstractManagedBean<CadastrarLivroMB> {

	private static final long serialVersionUID = 9169829006153798046L;

	@EJB
	private CategoriaService categoriaService; 
	@EJB
	private CopiaService copiaService; 
	@EJB
	private LivroService livroService;
	
	@Serviceable(CategoriaService.class)
	private CadastrarLivroDataFormatter dataFormatter;
	
	@Validator
	@Serviceable(LivroService.class)
	private CadastroLivroValidator validator;
	
	private List<Categoria> categorias;
	
	public CadastrarLivroMB() {
		super(CadastrarLivroMB.class);
	}
	
	public void init(){
		initCategorias();
	}
	
	private void initCategorias(){
		categorias =  categoriaService.buscarCategorias();
	}
	
	public void cadastrarLivro(){
		try{
			validator.validate(this);
			List<Copia> copias = dataFormatter.getFormattedData();
			copiaService.cadastrarCopias(copias);
			getDialogUtil().showDialog(DialogType.SUCCESS, "Livro cadastrado com sucesso");
			print(EtiquetaPrintable.NAME, PrintableBuilder.buildEtiquetas(copias));
		}catch(ValidationException | PrintableException e){
			getDialogUtil().showDialog(DialogType.ERROR, e.getMessage());
			getLogger().error(e);
		}
	}
	
	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}

	public CadastrarLivroDataFormatter getDataFormatter() {
		return dataFormatter;
	}

	public void setDataFormatter(CadastrarLivroDataFormatter dataFormatter) {
		this.dataFormatter = dataFormatter;
	}

}
