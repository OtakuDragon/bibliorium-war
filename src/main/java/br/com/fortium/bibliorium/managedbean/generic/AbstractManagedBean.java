package br.com.fortium.bibliorium.managedbean.generic;

import java.io.Serializable;
import java.lang.reflect.Field;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jboss.logging.Logger;

import br.com.fortium.bibliorium.print.Printable;
import br.com.fortium.bibliorium.util.DialogUtil;
import br.com.fortium.bibliorium.util.OutputPrintableUtil;
import br.com.fortium.bibliorium.util.ServiceableContainer;
import br.com.fortium.bibliorium.util.exception.PrintableException;
import br.com.fortium.bibliorium.util.exception.ServiceableException;
import br.com.fortium.bibliorium.validation.Validator;

public abstract class AbstractManagedBean<T> extends ServiceableContainer implements Serializable {

	private static final long serialVersionUID = 5525739374811866856L;
	
	private DialogUtil dialogUtil;
	private Logger logger;
	private Class<T> managedBeanClass;
	private OutputPrintableUtil printUtil;
	
	AbstractManagedBean() {}
	
	public AbstractManagedBean(Class<T> managedBeanClass){
		this.dialogUtil = new DialogUtil();
		this.printUtil  = new OutputPrintableUtil();
		this.managedBeanClass = managedBeanClass;
		this.logger = Logger.getLogger(managedBeanClass);
	}
	
	@PostConstruct
	private void abstractInit() throws ReflectiveOperationException, ServiceableException{
		setValidators();
		setServiceables(getMBInstance());
		init();
	}
	
	protected abstract void init();
	
	protected HttpSession getSession(){
		return (HttpSession)getFacesContext().getExternalContext().getSession(true);
	}
	
	protected void addMessage(Severity severity, String message, String detail){
		getFacesContext().addMessage(null, new FacesMessage(severity, message, detail));
	}

	protected DialogUtil getDialogUtil() {
		return dialogUtil;
	}
	
	protected Logger getLogger(){
		return logger;
	}
	
	protected void print(String printableName,Printable... printables) throws PrintableException{
		printUtil.download(getResponse(), printableName, printables);
		getFacesContext().responseComplete();
	}
	
	protected FacesContext getFacesContext(){
		return FacesContext.getCurrentInstance();
	}
	
	protected HttpServletResponse getResponse(){
		return (HttpServletResponse) getFacesContext().getExternalContext().getResponse();
	}
	
	@SuppressWarnings("unchecked")
	private T getMBInstance(){
		return (T) this;
	}
	
	private void setValidators() throws ReflectiveOperationException{
		for (Field field : managedBeanClass.getDeclaredFields()) {
			field.setAccessible(Boolean.TRUE);
			br.com.fortium.bibliorium.metadata.Validator validator = field.getAnnotation(br.com.fortium.bibliorium.metadata.Validator.class);
			if(validator != null){
				if(Validator.class.isAssignableFrom(field.getType())){
					try{
						field.set(getMBInstance(), field.getType().newInstance());
					}catch(InstantiationException e){
						throw new ReflectiveOperationException("O atributo "+ field.getName()+" Anotado com @Validator n�o possui um construtor sem parametros e isto � obrigatorio");
					}
				}else{
					throw new ReflectiveOperationException("O atributo "+ field.getName()+" Anotado com @Validator n�o implementa a interface Validator");
				}
			}
		}
	}
}