package br.com.fortium.bibliorium.util;

import br.com.fortium.bibliorium.service.Service;
import br.com.fortium.bibliorium.util.exception.NullServiceException;

public abstract class AbstractServiceableUtility<T extends Service> implements ServiceableUtility<T> {
	
	private T service;
	
	public void setService(T service){
		this.service = service;
	}
	
	public T getService() throws NullServiceException {
		if(service == null){
			throw new NullServiceException();
		}
		return service;
	}
}
