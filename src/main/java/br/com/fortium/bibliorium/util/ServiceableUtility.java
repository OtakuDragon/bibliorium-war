package br.com.fortium.bibliorium.util;

import java.util.Map;

import br.com.fortium.bibliorium.service.Service;
import br.com.fortium.bibliorium.util.exception.ServiceableException;

public interface ServiceableUtility {
	void setServices(Map<Class<? extends Service>, Service> services);
	<D extends Service> D getService(Class<D> serviceClass) throws ServiceableException;
}
