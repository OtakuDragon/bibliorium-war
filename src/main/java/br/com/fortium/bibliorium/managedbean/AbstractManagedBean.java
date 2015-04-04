package br.com.fortium.bibliorium.managedbean;

import java.io.Serializable;
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

import br.com.fortium.bibliorium.persistence.entity.Usuario;
import br.com.fortium.bibliorium.print.Printable;
import br.com.fortium.bibliorium.print.PrintableDataHolder;
import br.com.fortium.bibliorium.util.DialogUtil;
import br.com.fortium.bibliorium.util.ServiceableContainer;
import br.com.fortium.bibliorium.util.exception.ServiceableException;

public abstract class AbstractManagedBean<T> extends ServiceableContainer implements Serializable {

	private static final long serialVersionUID = 5525739374811866856L;
	
	private Logger logger;
	
	public AbstractManagedBean(){
		this.logger = Logger.getLogger(getMBInstance().getClass());
	}
	
	@PostConstruct
	private void abstractInit() throws ServiceableException{
		try{
			setServiceables(getMBInstance());
		}catch(ReflectiveOperationException e){
			throw new ServiceableException("Erro na injeção de utilities no managed bean", e);
		}
		init();
	}
	
	protected abstract void init();
	
	protected DialogUtil getDialogUtil() {
		return DialogUtil.getInstance();
	}
	
	protected Logger getLogger(){
		return logger;
	}
	
	protected void setPrintable(PrintableDataHolder printableDataHolder){
		getSession().setAttribute(Printable.DATA_HOLDER_KEY, printableDataHolder);
	}
	
	@SuppressWarnings("unchecked")
	private T getMBInstance(){
		return (T) this;
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
	    Message management
	=============================
	*/
	
	protected void addMessage(Severity severity, String message, String detail){
		addMessage(null, severity, message, detail);
	}
	
	protected void addValidationMessage(String clientId, String message){
		addMessage(clientId, FacesMessage.SEVERITY_ERROR, message, null);
	}
	
	protected void addMessage(String clientId, Severity severity, String message, String detail){
		getFacesContext().addMessage(clientId, new FacesMessage(severity, message, detail));
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
	
	protected Usuario getUsuarioAutenticado(){
		return (Usuario)getSessionAttribute(Usuario.AUTENTICADO);
	}
	
}
