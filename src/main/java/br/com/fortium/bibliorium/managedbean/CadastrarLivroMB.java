package br.com.fortium.bibliorium.managedbean;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import br.com.fortium.bibliorium.data.formatter.view.CadastrarLivroDataFormatter;
import br.com.fortium.bibliorium.enumeration.DialogType;
import br.com.fortium.bibliorium.metadata.Serviceable;
import br.com.fortium.bibliorium.persistence.entity.Categoria;
import br.com.fortium.bibliorium.persistence.entity.Copia;
import br.com.fortium.bibliorium.print.EtiquetaPrintable;
import br.com.fortium.bibliorium.print.Printable;
import br.com.fortium.bibliorium.print.PrintableBuilder;
import br.com.fortium.bibliorium.print.PrintableDataHolder;
import br.com.fortium.bibliorium.service.CategoriaService;
import br.com.fortium.bibliorium.service.CopiaService;
import br.com.fortium.bibliorium.service.LivroService;

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
	
	private List<Categoria> categorias;
	
	public void init(){
		initCategorias();
	}
	
	private void initCategorias(){
		categorias =  categoriaService.buscarCategorias();
	}
	
	public void cadastrarLivro(){
		List<Copia> copias = dataFormatter.getFormattedData();
		copiaService.cadastrarCopias(copias);
		getDialogUtil().showDialog(DialogType.SUCCESS, "Livro cadastrado com sucesso");
		handlePrint(copias);
	}
	
	private void handlePrint(List<Copia> copias){
		Printable[] etiquetas = PrintableBuilder.buildEtiquetas(copias);
		PrintableDataHolder dataHolder = new PrintableDataHolder(EtiquetaPrintable.NAME, etiquetas);
		setPrintable(dataHolder);
	}

	public void validarIsbn(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		String isbn = (String)value;

		if(!(isbn.length() == 10 || isbn.length() == 13)){
			throw new ValidatorException(new FacesMessage("O código ISBN deve ser um número de 10 ou 13 Digítos"));
		}
		
		if(StringUtils.isNumeric(isbn) && livroService.isIsbnAtivo(isbn)){
			throw new ValidatorException(new FacesMessage("ISBN já cadastrado"));
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
