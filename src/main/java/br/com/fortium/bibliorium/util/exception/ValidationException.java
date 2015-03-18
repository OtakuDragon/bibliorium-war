package br.com.fortium.bibliorium.util.exception;

public class ValidationException extends Exception {

	private static final long serialVersionUID = -5047952328054715202L;
	
	public ValidationException(String message){
		super(message);
	}

}
