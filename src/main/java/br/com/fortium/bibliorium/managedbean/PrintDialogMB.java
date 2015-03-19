package br.com.fortium.bibliorium.managedbean;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import br.com.fortium.bibliorium.enumeration.DialogType;
import br.com.fortium.bibliorium.managedbean.generic.AbstractManagedBean;
import br.com.fortium.bibliorium.util.exception.PrintableException;

@Named
@RequestScoped
public class PrintDialogMB extends AbstractManagedBean<PrintDialogMB> {

	private static final long serialVersionUID = 712333695975406665L;

	public PrintDialogMB() {
		super(PrintDialogMB.class);
	}

	@Override
	protected void init() {
		// Nenhuma inicialização necessária
	}
	
	public void print() {
		try{
			super.print();
		}catch(PrintableException e){
			getDialogUtil().showDialog(DialogType.ERROR, "Erro na geração da impressão");
			getLogger().error(e);
		}
	}

}
