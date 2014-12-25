package br.com.fortium.bibliorium.managedbean;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

public abstract class AbstractManagedBean {

	public HttpSession getSession(){
		return (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	}
	
	public void addMessage(Severity severity, String message, String detail){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, message, detail));
	}
}
