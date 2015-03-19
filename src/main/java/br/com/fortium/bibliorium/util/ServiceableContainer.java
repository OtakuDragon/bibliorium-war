package br.com.fortium.bibliorium.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import br.com.fortium.bibliorium.metadata.Serviceable;
import br.com.fortium.bibliorium.service.Service;
import br.com.fortium.bibliorium.util.exception.ServiceableException;

public abstract class ServiceableContainer {

	@SuppressWarnings("unchecked")
	protected <T> void setServiceables(T instance) throws ReflectiveOperationException, ServiceableException{
		Class<T> serviceableContainerClass = (Class<T>)instance.getClass();
		Map<Class<? extends Service>, Service> allServices = getAllServices(instance);
				
		for (Field field : serviceableContainerClass.getDeclaredFields()) {
			field.setAccessible(Boolean.TRUE);
			Serviceable serviceable = field.getAnnotation(Serviceable.class);
			if(serviceable != null){
				if(ServiceableUtility.class.isAssignableFrom(field.getType())){
					Map<Class<? extends Service>, Service> serviceableUtilityServices = getServicesForServiceable(serviceable, allServices);
					injectServiceable(instance, field, serviceableUtilityServices);
				}else{
					throw new ServiceableException("O atributo "+ field.getName()+" Anotado com @Serviceable não implementa a interface ServiceableUtility");
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private <T> Map<Class<? extends Service>, Service> getAllServices(T instance) throws ReflectiveOperationException{
		Class<T> serviceableContainerClass = (Class<T>)instance.getClass();
		Map<Class<? extends Service>, Service> retorno = new HashMap<Class<? extends Service>, Service>();
		
		for (Field field : serviceableContainerClass.getDeclaredFields()) {
			field.setAccessible(Boolean.TRUE);
			
			if(Service.class.isAssignableFrom(field.getType())){
				retorno.put((Class<? extends Service>) field.getType(), (Service)field.get(instance));
			}
		}
		
		return retorno;
	}
	
	private <T> Map<Class<? extends Service>, Service> getServicesForServiceable(Serviceable serviceable, Map<Class<? extends Service>, Service> allServices) throws ReflectiveOperationException, ServiceableException{
		Map<Class<? extends Service>, Service> retorno = new HashMap<Class<? extends Service>, Service>();
		
		for (Class<? extends Service> serviceClass : serviceable.value()) {
			Service service = allServices.get(serviceClass);
			if(service == null){
				throw new ServiceableException("Serviço definido na anotação @Serviceable, não definido no managed bean ou não instanciado corretamente");
			}
			retorno.put(serviceClass, service);
		}
		
		return retorno;
	}
	
	private <T> void injectServiceable(T instance, Field serviceableField, Map<Class<? extends Service>, Service> services) throws ReflectiveOperationException, ServiceableException{
		try{
			serviceableField.set(instance, serviceableField.getType().newInstance());
			ServiceableUtility servUtil = (ServiceableUtility)serviceableField.get(instance);
			servUtil.setServices(services);
		}catch(InstantiationException e){
			throw new ServiceableException("O atributo "+ serviceableField.getName()+" Anotado com @Serviceable não possui um construtor sem parametros e isto é obrigatorio");
		}
	}
	
}
