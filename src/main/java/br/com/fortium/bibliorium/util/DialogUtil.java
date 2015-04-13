package br.com.fortium.bibliorium.util;

import static br.com.fortium.bibliorium.constantes.DialogConsts.DEFAULT_HEADER;
import static br.com.fortium.bibliorium.constantes.DialogConsts.DEFAULT_MESSAGE;
import static br.com.fortium.bibliorium.constantes.DialogConsts.WARNING_ICON;

import org.primefaces.context.RequestContext;

import br.com.fortium.bibliorium.enumeration.DialogType;
import br.com.fortium.bibliorium.managedbean.AbstractManagedBean;

public class DialogUtil extends AbstractManagedBean<DialogUtil> {

	private static final long serialVersionUID = 1141010581647200671L;
	
	//Estes nome estão definidos, nas expressões do p:dialog em templateBase.xhtml que representam os parametros do dialog.
	private static final String DIALOG_HEADER  = "dialogHeader";
	private static final String DIALOG_ICON    = "dialogIcon";
	private static final String DIALOG_MESSAGE = "dialogMesssage";
	
	private static DialogUtil instance;
	
	public static DialogUtil getInstance(){
		if(instance == null){
			instance = new DialogUtil();
		}
		
		return instance;
	}
	
	private DialogUtil(){
		setSessionAttribute(DIALOG_HEADER, DEFAULT_HEADER);
		setSessionAttribute(DIALOG_ICON, WARNING_ICON);
		setSessionAttribute(DIALOG_MESSAGE, DEFAULT_MESSAGE);
	}
	
	@Override
	protected void init() {
		//Never executed
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
		setSessionAttribute(DIALOG_ICON,    icon);
		setSessionAttribute(DIALOG_HEADER,  header);
		setSessionAttribute(DIALOG_MESSAGE, message);
		showDialog();
	}
	
	private void showDialog(){
		RequestContext context = RequestContext.getCurrentInstance();
		//Executa o script javascript que mostra o p:dialog na tela
		//'dialog' é o nome defininido para o client side widget, no p:dialog que se encontra no templateBase.xhtml
		context.execute("PF('dialog').show()");
		
	}
	
}
