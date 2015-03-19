package br.com.fortium.bibliorium.util;

import java.util.Map;

import br.com.fortium.bibliorium.service.Service;
import br.com.fortium.bibliorium.util.exception.ServiceableException;

public abstract class AbstractServiceableUtility implements ServiceableUtility {
	
	private Map<Class<? extends Service>, Service> services;
	
	@Override
	public void setServices(Map<Class<? extends Service>, Service> services){
		this.services = services;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <D extends Service> D getService(Class<D> serviceClass) throws ServiceableException {
		if(services == null){
			throw new ServiceableException("Serviços não setados, a classe que conter membros anotados deve extender de ServiceableContainer");
		}
		
		Service service = services.get(serviceClass);
		
		if(service == null){
			throw new ServiceableException("Serviço não disponivel para este serviceable, este tipo foi declarado na anotação?");
		}
		
		return (D) services.get(serviceClass);
	}
}
