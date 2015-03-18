package br.com.fortium.bibliorium.util.exception;

public class PrintableException extends Exception {

	private static final long serialVersionUID = -2987382423466410579L;

	public PrintableException(String message) {
		super(message);
	}
	
	public PrintableException(String message, Throwable throwable) {
		super(message, throwable);
	}
	
}
