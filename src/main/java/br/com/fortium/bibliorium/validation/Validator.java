package br.com.fortium.bibliorium.validation;

import org.apache.commons.lang3.StringUtils;

import br.com.fortium.bibliorium.validation.exception.ValidationException;

public abstract class Validator<T> {
	
	protected static final String REQUIRED_MESSAGE = "Valor Obrigatório não informado";
	protected static final String REQUIRED_NUMBER_MESSAGE = "Numero Obrigatório não informado ou inválido";
	
	public abstract void validate(T instance) throws ValidationException;

	protected void validateRequiredValues(String... values) throws ValidationException{
		for (String value : values) {
			if(StringUtils.isEmpty(value)){
				throw new ValidationException(REQUIRED_MESSAGE);
			}
		}
	}
	
	protected void validateRequiredNumbers(String... values) throws ValidationException{
		try{
			validateRequiredValues(values);
			for (String value : values) {
				Float.parseFloat(value);
			}
		}catch(ValidationException | NumberFormatException nfe){
			throw new ValidationException(REQUIRED_NUMBER_MESSAGE);
		}
	}
}
