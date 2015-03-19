package br.com.fortium.bibliorium.managedbean.generic;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Formatter;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jboss.logging.Logger;
import org.primefaces.context.RequestContext;

import br.com.fortium.bibliorium.managedbean.LoginMB;
import br.com.fortium.bibliorium.print.Printable;
import br.com.fortium.bibliorium.print.PrintableDataHolder;
import br.com.fortium.bibliorium.util.DialogUtil;
import br.com.fortium.bibliorium.util.ServiceableContainer;
import br.com.fortium.bibliorium.util.exception.ServiceableException;

public abstract class AbstractManagedBean<T> extends ServiceableContainer implements Serializable {

	private static final long serialVersionUID = 5525739374811866856L;
	
	private DialogUtil dialogUtil;
	private Logger logger;
	private Class<T> managedBeanClass;
	
	AbstractManagedBean() {}
	
	public AbstractManagedBean(Class<T> managedBeanClass){
		this.dialogUtil = new DialogUtil();
		this.managedBeanClass = managedBeanClass;
		this.logger = Logger.getLogger(managedBeanClass);
	}
	
	@PostConstruct
	private void abstractInit() throws ServiceableException{
		try{
			setValidator();
			setServiceables(getMBInstance());
		}catch(ReflectiveOperationException e){
			throw new ServiceableException("Erro na injeção de utilities no managed bean", e);
		}
		init();
	}
	
	protected abstract void init();
	
	protected void addMessage(Severity severity, String message, String detail){
		getFacesContext().addMessage(null, new FacesMessage(severity, message, detail));
	}

	protected DialogUtil getDialogUtil() {
		return dialogUtil;
	}
	
	protected Logger getLogger(){
		return logger;
	}
	
	protected void setPrintable(PrintableDataHolder printableDataHolder){
		getSession().setAttribute(Printable.DATA_HOLDER_KEY, printableDataHolder);
	}
	
	/*
	=============================
	    Web Objects
	=============================
	*/

	protected FacesContext getFacesContext(){
		return FacesContext.getCurrentInstance();
	}
	
	protected HttpServletResponse getResponse(){
		return (HttpServletResponse) getFacesContext().getExternalContext().getResponse();
	}
	
	protected HttpServletRequest getRequest(){
		return (HttpServletRequest) getFacesContext().getExternalContext().getRequest();
	}
	
	/*
	=============================
	    Navigation management
	=============================
	*/
	
	protected void redirectToHome(){
		String contextPath = getRequest().getContextPath();
		String homePageSuffix = (String)getSession().getAttribute(LoginMB.HOME_PAGE_KEY);

		redirect(contextPath + homePageSuffix);
	}
	
	protected void refresh(){
		redirect(getRequest().getRequestURL().toString());
	}
	
	protected void redirect(String page){
		Formatter formatter = new Formatter();
		Formatter javascript = formatter.format("window.location.assign('%s')", page);
		executeJavascript(javascript.toString());
		formatter.close();
	}
	
	protected void executeJavascript(String javascriptCode){
		RequestContext.getCurrentInstance().execute(javascriptCode);
	}
	
	/*
	=============================
	    Session management
	=============================
	*/
	private HttpSession getSession(){
		return (HttpSession)getFacesContext().getExternalContext().getSession(true);
	}
	
	protected void invalidateSession(){
		getSession().invalidate();
	}
	
	protected void setSessionAttribute(String attributeName, Object value){
		getSession().setAttribute(attributeName, value);
	}
	
	protected Object getSessionAttribute(String attributeName){
		return getSession().getAttribute(attributeName);
	}
	
	protected Object extractSessionAttribute(String attributeName){
		Object retorno = getSessionAttribute(attributeName);
		getSession().removeAttribute(attributeName);
		return retorno;
	}
	
	/*
	=============================
	*/
	
	@SuppressWarnings("unchecked")
	private T getMBInstance(){
		return (T) this;
	}
	
	private void setValidator() throws ReflectiveOperationException{
		for (Field field : managedBeanClass.getDeclaredFields()) {
			field.setAccessible(Boolean.TRUE);
			
			Class<?> validationInterface                     = br.com.fortium.bibliorium.validation.Validator.class;
			Class<? extends Annotation> validationAnnotation = br.com.fortium.bibliorium.metadata.Validator.class;
			
			if(field.isAnnotationPresent(validationAnnotation)){
					if(validationInterface.isAssignableFrom(field.getType())){
					try{
						if(field.get(getMBInstance()) == null){
							field.set(getMBInstance(), field.getType().newInstance());
						}else{
							throw new ReflectiveOperationException("Mais do que um @Validator identificado no managed bean");
						}
					}catch(InstantiationException e){
						throw new ReflectiveOperationException("O atributo "+ field.getName()+" Anotado com @Validator não possui um construtor sem parametros e isto é obrigatorio");
					}
				}else{
					throw new ReflectiveOperationException("O atributo "+ field.getName()+" Anotado com @Validator não implementa a interface Validator");
				}
			}
		}
	}
}
