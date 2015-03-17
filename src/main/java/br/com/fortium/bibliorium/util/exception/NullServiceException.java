package br.com.fortium.bibliorium.util.exception;

public class NullServiceException extends RuntimeException {

	private static final long serialVersionUID = 2575171349626682482L;
	
	public NullServiceException() {
		super("Serviço indefinido, defina ele com setService() antes de utiliza-lo");
	}

}
