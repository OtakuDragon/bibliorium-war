package br.com.fortium.bibliorium.managedbean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.jboss.logging.Logger;

import br.com.fortium.bibliorium.util.DialogUtil;
import br.com.fortium.bibliorium.validation.Validator;
import br.com.fortium.bibliorium.validation.exception.ValidationException;

public abstract class AbstractManagedBean<T> implements Serializable {

	private static final long serialVersionUID = 5525739374811866856L;
	
	private DialogUtil dialogUtil;
	private Logger logger;
	private Validator<T> validator;
	
	public AbstractManagedBean() {}
	
	protected AbstractManagedBean(Class<T> managedBeanClass, Validator<T> validator){
		dialogUtil = new DialogUtil();
		logger = Logger.getLogger(managedBeanClass);
		this.validator = validator;
	}
	
	protected HttpSession getSession(){
		return (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	}
	
	protected void addMessage(Severity severity, String message, String detail){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, message, detail));
	}

	protected DialogUtil getDialogUtil() {
		return dialogUtil;
	}
	
	protected Logger getLogger(){
		return logger;
	}
	
	@SuppressWarnings("unchecked")
	protected void validate() throws ValidationException{
		validator.validate((T)this);
	}

}
