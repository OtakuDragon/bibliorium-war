package br.com.fortium.bibliorium.managedbean;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIInput;
import javax.faces.event.AjaxBehaviorEvent;

import br.com.fortium.bibliorium.enumeration.DialogType;
import br.com.fortium.bibliorium.exception.ValidationException;
import br.com.fortium.bibliorium.persistence.entity.Emprestimo;
import br.com.fortium.bibliorium.persistence.entity.Usuario;
import br.com.fortium.bibliorium.persistence.enumeration.TipoEmprestimo;
import br.com.fortium.bibliorium.print.ComprovanteEmprestimoPrintable;
import br.com.fortium.bibliorium.print.Printable;
import br.com.fortium.bibliorium.print.PrintableBuilder;
import br.com.fortium.bibliorium.print.PrintableDataHolder;
import br.com.fortium.bibliorium.service.EmprestimoService;
import br.com.fortium.bibliorium.util.DataUtil;

@ManagedBean
@ViewScoped
public class HistoricoMB extends AbstractManagedBean<HistoricoMB> {

	private static final long serialVersionUID = 5463787346546469928L;
	
	private Usuario leitor;
	private List<String> periodos;
	private Map<String, Date> periodosDate;
	private String opcaoPeriodo;
	private Emprestimo emprestimoDetalhe;
	private List<Emprestimo> emprestimos;
	
	private final SimpleDateFormat formatter = new SimpleDateFormat("MM/yyyy");
	
	@EJB
	private EmprestimoService emprestimoService;

	@Override
	protected void init() {
		leitor      = (Usuario) extractSessionAttribute("leitor");
		
		if(leitor == null){
			leitor = getUsuarioAutenticado();
		}
		
		periodos    = calcularPeriodos(leitor);
		emprestimos = emprestimoService.buscar(leitor, null);
	}

	public void filtrar(AjaxBehaviorEvent e){
		UIInput component = (UIInput)e.getComponent();
		String value      = (String)component.getValue();
		Date data = periodosDate.get(value);
		
		emprestimos = emprestimoService.buscar(leitor, data);
		
	}

	public void renovar(){
		try{
			Emprestimo emprestimo = emprestimoService.renovarEmprestimo(emprestimoDetalhe.getCopia());
			printComprovante(emprestimo);
			getDialogUtil().showDialog(DialogType.SUCCESS, "Empréstimo renovado com sucesso");
		}catch(ValidationException e){
			getDialogUtil().showDialog(DialogType.ERROR, e.getMessage());
		}
	}
	
	private List<String> calcularPeriodos(Usuario leitor) {
		List<String> retorno = new LinkedList<String>();
		periodosDate         = new LinkedHashMap<String, Date>();
		
		Calendar dataPeriodo = DataUtil.getCalendar(leitor.getDataCadastro());
		Calendar hoje        = DataUtil.getCalendar(null);
		
		do{
			String dataS = formatter.format(dataPeriodo.getTime());
			retorno.add(dataS);
			periodosDate.put(dataS, dataPeriodo.getTime());
			
			dataPeriodo.add(Calendar.MONTH, 1);
			
		}while(dataPeriodo.compareTo(hoje) <= 0);
		
		Collections.reverse(retorno);
		
		return retorno;
	}
	
	public List<Emprestimo> getEmprestimos() {
		Collections.sort(emprestimos, new Comparator<Emprestimo>(){
			@Override
			public int compare(Emprestimo o1, Emprestimo o2) {
				if(o1.getId() < o2.getId()){
					return 1;
				}else if(o1.getId() > o2.getId()){
					return -1;
				}
				return 0;
			}
			
		});
		return emprestimos;
	}

	public boolean isRenovavel(){
		return (emprestimoDetalhe != null &&
			    emprestimoDetalhe.getTipo() == TipoEmprestimo.EMPRESTIMO &&
			    emprestimoDetalhe.getDataFechamento() == null &&
			    emprestimoDetalhe.getDataPrevista().after(new Date()));
	}
	
	private void printComprovante(Emprestimo emprestimo){
		Printable comprovante = PrintableBuilder.buildComprovanteEmprestimo(emprestimo, PrintableBuilder.TipoComprovante.EMPRESTIMO);
		PrintableDataHolder dataHolder = new PrintableDataHolder(ComprovanteEmprestimoPrintable.NAME, comprovante);
		setPrintable(dataHolder);
	}
	
	public void setEmprestimos(List<Emprestimo> emprestimos) {
		this.emprestimos = emprestimos;
	}

	public Usuario getLeitor() {
		return leitor;
	}

	public void setLeitor(Usuario leitor) {
		this.leitor = leitor;
	}

	
	public Emprestimo getEmprestimoDetalhe() {
		return emprestimoDetalhe;
	}

	public void setEmprestimoDetalhe(Emprestimo emprestimoDetalhe) {
		this.emprestimoDetalhe = emprestimoDetalhe;
	}

	public List<String> getPeriodos() {
		return periodos;
	}

	public void setPeriodos(List<String> periodos) {
		this.periodos = periodos;
	}

	public Map<String, Date> getPeriodosDate() {
		return periodosDate;
	}

	public void setPeriodosDate(Map<String, Date> periodosDate) {
		this.periodosDate = periodosDate;
	}

	public String getOpcaoPeriodo() {
		return opcaoPeriodo;
	}

	public void setOpcaoPeriodo(String opcaoPeriodo) {
		this.opcaoPeriodo = opcaoPeriodo;
	}


}
