package br.com.fortium.bibliorium.managedbean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.com.fortium.bibliorium.util.DialogUtil;

public abstract class AbstractManagedBean implements Serializable {

	private static final long serialVersionUID = 5525739374811866856L;
	
	private DialogUtil dialogUtil;
	
	protected AbstractManagedBean(){
		dialogUtil = new DialogUtil();
	}
	
	public HttpSession getSession(){
		return (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	}
	
	public void addMessage(Severity severity, String message, String detail){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, message, detail));
	}

	public DialogUtil getDialogUtil() {
		return dialogUtil;
	}
}
