package br.com.fortium.bibliorium.util;

import br.com.fortium.bibliorium.service.Service;
import br.com.fortium.bibliorium.util.exception.NullServiceException;

public interface ServiceableUtility<T extends Service> {
	void setService(T service);
	T getService() throws NullServiceException;
}
