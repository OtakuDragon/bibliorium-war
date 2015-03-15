package br.com.fortium.bibliorium.validation;

import br.com.fortium.bibliorium.managedbean.CadastrarLivroMB;
import br.com.fortium.bibliorium.persistence.entity.Livro;
import br.com.fortium.bibliorium.validation.exception.ValidationException;

public class CadastroLivroValidator extends Validator<CadastrarLivroMB> {

	@Override
	public void validate(CadastrarLivroMB instance) throws ValidationException {
		Livro livro = instance.getLivro();
		
		validateRequiredValues(livro.getTitulo(), livro.getEditora(), livro.getAutores(), livro.getAutores(), livro.getEdicao());
		validateRequiredNumbers(livro.getIsbn(), livro.getNumPaginas(), instance.getQuantidade());
		
		if(livro.getCategoria().getIdCategoria() == null){
			throw new ValidationException("Categoria não foi definida");
		}
		
		if(!(livro.getIsbn().length() == 10 || livro.getIsbn().length() == 13)){
			throw new ValidationException("ISPB Inválido");
		}
	
	}

}
