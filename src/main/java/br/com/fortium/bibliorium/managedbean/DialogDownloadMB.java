package br.com.fortium.bibliorium.managedbean;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import br.com.fortium.bibliorium.enumeration.DialogType;
import br.com.fortium.bibliorium.managedbean.generic.AbstractManagedBean;
import br.com.fortium.bibliorium.print.Printable;
import br.com.fortium.bibliorium.print.PrintableDataHolder;
import br.com.fortium.bibliorium.util.OutputPrintableUtil;
import br.com.fortium.bibliorium.util.exception.PrintableException;

@Named
@RequestScoped
public class DialogDownloadMB extends AbstractManagedBean<DialogDownloadMB> {

	private static final long serialVersionUID = 712333695975406665L;
	
	private OutputPrintableUtil printUtil;
	
	public DialogDownloadMB() {
		super(DialogDownloadMB.class);
	}

	@Override
	protected void init() {
		this.printUtil  = new OutputPrintableUtil();
	}

	public void print() {
		try{
			Object dhObj = extractSessionAttribute(Printable.DATA_HOLDER_KEY);
			boolean printed = Boolean.FALSE;
			
			if(dhObj != null){
				PrintableDataHolder printableDataHolder = (PrintableDataHolder) dhObj;
				printUtil.download(getResponse(), printableDataHolder.getPrintableName(), printableDataHolder.getPrintables());
				getFacesContext().responseComplete();
				printed = Boolean.TRUE;
			}
			
			setSessionAttribute(Printable.PRINTED_FLAG_KEY, printed);
		}catch(PrintableException e){
			getDialogUtil().showDialog(DialogType.ERROR, "Erro na geração da impressão");
			getLogger().error(e);
		}
	}
	
	public void refresh() {
		extractSessionAttribute(Printable.DATA_HOLDER_KEY);
		if(isPrinted()){
			refresh();
		}
	}
	
	private boolean isPrinted(){
		Boolean printedFlag = (Boolean) extractSessionAttribute(Printable.PRINTED_FLAG_KEY);
		
		if(printedFlag == null){
			printedFlag = Boolean.FALSE;
		}
		
		return printedFlag;
	}

}
