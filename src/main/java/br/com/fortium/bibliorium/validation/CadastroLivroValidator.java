package br.com.fortium.bibliorium.validation;

import java.util.Map;

import br.com.fortium.bibliorium.data.formatter.view.CadastrarLivroDataFormatter;
import br.com.fortium.bibliorium.managedbean.CadastrarLivroMB;
import br.com.fortium.bibliorium.service.LivroService;
import br.com.fortium.bibliorium.service.Service;
import br.com.fortium.bibliorium.util.ServiceableUtility;
import br.com.fortium.bibliorium.util.ServiceableUtilityAdapter;
import br.com.fortium.bibliorium.util.exception.ValidationException;

public class CadastroLivroValidator implements Validator<CadastrarLivroMB>, ServiceableUtility{

	private ServiceableUtilityAdapter serviceUtilAdapter; 
	
	public CadastroLivroValidator() {
		serviceUtilAdapter = new ServiceableUtilityAdapter();
	}
	
	@Override
	public void validate(CadastrarLivroMB instance) throws ValidationException {
		CadastrarLivroDataFormatter formatter = instance.getDataFormatter();
		
		if(formatter.getCategoria() == null){
			throw new ValidationException("Categoria não foi definida");
		}
		
		if(getService(LivroService.class).isIsbnCadastrado(formatter.getIsbn())){
			throw new ValidationException("ISBN já cadastrado");
		}
	
	}

	@Override
	public void setServices(Map<Class<? extends Service>, Service> services) {
		serviceUtilAdapter.setServices(services);
	}

	@Override
	public <D extends Service> D getService(Class<D> serviceClass) {
		return serviceUtilAdapter.getService(serviceClass);
	}

}
