package br.com.fortium.bibliorium.util;

import static br.com.fortium.bibliorium.constantes.DialogConsts.*;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.fortium.bibliorium.enumeration.DialogType;

public class DialogUtil {

	//Estes nome estão definidos, nas expressões do p:dialog em templateBase.xhtml que representam os parametros do dialog.
	private static final String DIALOG_HEADER  = "dialogHeader";
	private static final String DIALOG_ICON    = "dialogIcon";
	private static final String DIALOG_MESSAGE = "dialogMesssage";
	
	private HttpSession session;
	
	private static DialogUtil instance;
	
	public static DialogUtil getInstance(){
		if(instance == null){
			instance = new DialogUtil();
		}
		
		return instance;
	}
	
	private DialogUtil(){
		session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		session.setAttribute(DIALOG_HEADER,   DEFAULT_HEADER);
		session.setAttribute(DIALOG_ICON,     WARNING_ICON);
		session.setAttribute(DIALOG_MESSAGE, DEFAULT_MESSAGE);
	}
	
	public void showDialog(DialogType type){
		showDialog(type.getDefaultIcon(), type.getDefaultHeader(), type.getDefaultMessage());
	}
	
	public void showDialog(DialogType type, String message){
		showDialog(type.getDefaultIcon(), type.getDefaultHeader(), message);
	}
	
	public void showDialog(DialogType type, String header, String message){
		showDialog(type.getDefaultIcon(), header, message);
	}
	
	private void showDialog(String icon, String header, String message){
		session.setAttribute(DIALOG_ICON,    icon);
		session.setAttribute(DIALOG_HEADER,  header);
		session.setAttribute(DIALOG_MESSAGE, message);
		showDialog();
	}
	
	private void showDialog(){
		RequestContext context = RequestContext.getCurrentInstance();
		//Executa o script javascript que mostra o p:dialog na tela
		//'dialog' é o nome defininido para o client side widget, no p:dialog que se encontra no templateBase.xhtml
		context.execute("PF('dialog').show()");
		
	}
	
}
