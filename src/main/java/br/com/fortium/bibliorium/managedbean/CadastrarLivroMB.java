package br.com.fortium.bibliorium.managedbean;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.primefaces.model.UploadedFile;

import br.com.fortium.bibliorium.data.formatter.view.CadastrarLivroDataFormatter;
import br.com.fortium.bibliorium.enumeration.DialogType;
import br.com.fortium.bibliorium.persistence.entity.Categoria;
import br.com.fortium.bibliorium.persistence.entity.Copia;
import br.com.fortium.bibliorium.service.CategoriaService;
import br.com.fortium.bibliorium.service.CopiaService;
import br.com.fortium.bibliorium.validation.CadastroLivroValidator;
import br.com.fortium.bibliorium.validation.exception.ValidationException;

@Named
@RequestScoped
public class CadastrarLivroMB extends AbstractManagedBean<CadastrarLivroMB> {

	private static final long serialVersionUID = 9169829006153798046L;

	@EJB
	private CategoriaService categoriaService; 
	@EJB
	private CopiaService copiaService; 
	
	private CadastrarLivroDataFormatter dataFormatter;
	
	private List<Categoria> categorias;
	
	public CadastrarLivroMB() {
		super(CadastrarLivroMB.class, new CadastroLivroValidator());
	}
	
	@PostConstruct
	public void init(){
		initCategorias();
		dataFormatter = new CadastrarLivroDataFormatter(categoriaService);
	}
	
	private void initCategorias(){
		categorias =  categoriaService.buscarCategorias();
	}
	
	public void cadastrarLivro() throws Exception{
		try{
			validate();
			List<Copia> copias = dataFormatter.getFormattedData();
			saveFoto(dataFormatter.getFoto());
			copiaService.cadastrarCopias(copias);
			getDialogUtil().showDialog(DialogType.SUCCESS, "Livro cadastrado com sucesso");
		}catch(ValidationException | IOException e){
			getDialogUtil().showDialog(DialogType.ERROR, e.getMessage());
			getLogger().error(e);
		}
		
	}

	private void saveFoto(UploadedFile foto) throws Exception{
		String fileName = foto.getFileName();
		if(!fileName.isEmpty()){
			//Definir um folder padrão
			foto.write("D:\\Users\\Otaku_Dragon\\Desktop\\Bibliorium\\a.png");
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
