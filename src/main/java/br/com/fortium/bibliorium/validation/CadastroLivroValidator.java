package br.com.fortium.bibliorium.validation;

import br.com.fortium.bibliorium.data.formatter.view.CadastrarLivroDataFormatter;
import br.com.fortium.bibliorium.managedbean.CadastrarLivroMB;
import br.com.fortium.bibliorium.validation.exception.ValidationException;

public class CadastroLivroValidator extends Validator<CadastrarLivroMB> {

	@Override
	public void validate(CadastrarLivroMB instance) throws ValidationException {
		CadastrarLivroDataFormatter formatter = instance.getDataFormatter();
		
		validateRequiredValues(formatter.getTitulo(), formatter.getEditora(), formatter.getAutores(), formatter.getAutores(), formatter.getEdicao());
		validateRequiredNumbers(formatter.getIsbn(), formatter.getNumPaginas(), formatter.getQuantidade());
		
		if(formatter.getCategoria() == null){
			throw new ValidationException("Categoria não foi definida");
		}
		
		if(!(formatter.getIsbn().length() == 10 || formatter.getIsbn().length() == 13)){
			throw new ValidationException("ISPB Inválido");
		}
	
	}

}
