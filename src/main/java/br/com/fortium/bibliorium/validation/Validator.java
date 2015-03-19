package br.com.fortium.bibliorium.validation;

import br.com.fortium.bibliorium.util.exception.ValidationException;

public interface Validator<T> {
	void validate(T instance) throws ValidationException;
}
