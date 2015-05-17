package br.com.fortium.bibliorium.managedbean;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.fortium.bibliorium.enumeration.DialogType;
import br.com.fortium.bibliorium.persistence.entity.Emprestimo;
import br.com.fortium.bibliorium.print.ComprovanteEmprestimoPrintable;
import br.com.fortium.bibliorium.print.Printable;
import br.com.fortium.bibliorium.print.PrintableBuilder;
import br.com.fortium.bibliorium.print.PrintableDataHolder;
import br.com.fortium.bibliorium.print.PrintableBuilder.TipoComprovante;
import br.com.fortium.bibliorium.service.EmprestimoService;

@ManagedBean
@ViewScoped
public class LiquidarMultaMB extends AbstractManagedBean<LiquidarMultaMB> {

	private static final long serialVersionUID = -1778399635451707204L;
	
	private String     codEmprestimo;
	private Emprestimo emprestimo;
	
	@EJB
	private EmprestimoService emprestimoService; 
	
	@Override
	protected void init() {
	
	}

	public void pesquisar(){
		Long id = null;
		
		try{
			id = Long.parseLong(codEmprestimo);
			emprestimo = emprestimoService.buscarEmprestimo(id);
		}catch(NumberFormatException e){
			emprestimo = null;
		}
	}
	
	public void liquidar(){
		emprestimoService.liquidarMulta(emprestimo);
		printComprovante(emprestimo, TipoComprovante.RECIBO);
		getDialogUtil().showDialog(DialogType.SUCCESS, "Multa Liquidada com sucesso");
	}
	
	public void reset(){
		setCodEmprestimo(null);
		setEmprestimo(null);
	}
	
	private void printComprovante(Emprestimo emprestimo, PrintableBuilder.TipoComprovante tipo){
		Printable comprovante = PrintableBuilder.buildComprovanteEmprestimo(emprestimo, PrintableBuilder.TipoComprovante.EMPRESTIMO);
		PrintableDataHolder dataHolder = new PrintableDataHolder(ComprovanteEmprestimoPrintable.NAME, comprovante);
		setPrintable(dataHolder);
	}
	
	public String getCodEmprestimo() {
		return codEmprestimo;
	}

	public void setCodEmprestimo(String codEmprestimo) {
		this.codEmprestimo = codEmprestimo;
	}

	public Emprestimo getEmprestimo() {
		return emprestimo;
	}

	public void setEmprestimo(Emprestimo emprestimo) {
		this.emprestimo = emprestimo;
	}

}
