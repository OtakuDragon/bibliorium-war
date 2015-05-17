package br.com.fortium.bibliorium.managedbean;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.fortium.bibliorium.builder.EmprestimoBuilder;
import br.com.fortium.bibliorium.enumeration.DialogType;
import br.com.fortium.bibliorium.exception.ValidationException;
import br.com.fortium.bibliorium.persistence.entity.Copia;
import br.com.fortium.bibliorium.persistence.entity.Emprestimo;
import br.com.fortium.bibliorium.persistence.entity.Usuario;
import br.com.fortium.bibliorium.persistence.enumeration.EstadoCopia;
import br.com.fortium.bibliorium.print.ComprovanteEmprestimoPrintable;
import br.com.fortium.bibliorium.print.Printable;
import br.com.fortium.bibliorium.print.PrintableBuilder;
import br.com.fortium.bibliorium.print.PrintableDataHolder;
import br.com.fortium.bibliorium.service.CopiaService;
import br.com.fortium.bibliorium.service.EmprestimoService;
import br.com.fortium.bibliorium.service.UsuarioService;

@ManagedBean
@ViewScoped
public class GerenciarEmprestimoMB extends AbstractManagedBean<GerenciarEmprestimoMB> {

	private enum Action {EMPRESTIMO, RESERVA, DEVOLUCAO}
	
	private static final long serialVersionUID = 2249645974635438267L;
	
	private String codLivro;
	private String codCopia;
	private String cpf;
	
	private Copia copia;
	private Usuario usuario;
	
	private Boolean displayBuscaUsuario;
	
	private Action action;
	
	@EJB
	private CopiaService copiaService;
	
	@EJB
	private EmprestimoService emprestimoService;
	
	@EJB
	private UsuarioService usuarioService;
	
	@Override
	protected void init() {
		codLivro = getRequest().getParameter("idLivro");
	}
	
	public void buscarCopia(){	
		try{
			Long idLivro = Long.parseLong(codLivro);
			Long idCopia = Long.parseLong(codCopia);
			copia = copiaService.buscar(idLivro, idCopia);
		}catch(NumberFormatException e){
			copia = null;
		}
	}
	
	public void buscarUsuario(){
		usuario = usuarioService.buscar(cpf);
	}
	
	public void emprestar(){
		setDisplayBuscaUsuario(Boolean.TRUE);
		setAction(Action.EMPRESTIMO);
	}
	
	public void reservar(){
		setDisplayBuscaUsuario(Boolean.TRUE);
		setAction(Action.RESERVA);
	}
	
	public void emprestarReserva(){
		Emprestimo reserva = emprestimoService.buscarReserva(copia);
		usuario = reserva.getUsuario();
		cpf    = usuario.getCpf();
		
		emprestimoService.concluirEmprestimo(reserva);
		
		setAction(Action.EMPRESTIMO);
	}
	
	public void cancelarReserva(){
		emprestimoService.cancelarReserva(copia);
		getDialogUtil().showDialog(DialogType.SUCCESS, "Reserva cancelada com sucesso");
		reset();
	}
	
	public void efetuarDevolucao(){
		Emprestimo emprestimo = emprestimoService.buscarEmprestimo(copia);
		emprestimoService.concluirEmprestimo(emprestimo);
		printComprovante(emprestimo, PrintableBuilder.TipoComprovante.EMPRESTIMO);
		reset();
		getDialogUtil().showDialog(DialogType.SUCCESS, "Emprestimo finalizado com sucesso");
	}
	
	public void renovarEmprestimo(){
		try {
			Emprestimo emprestimo = emprestimoService.renovarEmprestimo(copia);
			getDialogUtil().showDialog(DialogType.SUCCESS, "Emprestimo renovado com sucesso");
			printComprovante(emprestimo, PrintableBuilder.TipoComprovante.EMPRESTIMO);
			reset();
		} catch (ValidationException e) {
			getDialogUtil().showDialog(DialogType.ERROR, e.getMessage());
		}
	}

	public void reset(){
		setCpf(null);
		setCopia(null);
		setUsuario(null);
		setCodLivro(null);
		setCodCopia(null);
		setAction(null);
		setDisplayBuscaUsuario(Boolean.FALSE);
	}
	
	public void confirmar(){
		if(getAction() == Action.EMPRESTIMO || getAction() == Action.RESERVA){
			efetuarEmprestimo();
		}
	}

	private void efetuarEmprestimo() {
		Emprestimo emprestimo = null;
		
		try{
			switch(getAction()){
				case EMPRESTIMO:
					emprestimo = EmprestimoBuilder.novoEmprestimo(usuario, copia);
					emprestimoService.efetuarEmprestimo(emprestimo);
					getDialogUtil().showDialog(DialogType.SUCCESS, "Empréstimo realizado com sucesso");
					break;
				case RESERVA:
					emprestimo = EmprestimoBuilder.novaReserva(usuario, copia);
					emprestimoService.efetuarEmprestimo(emprestimo);
					getDialogUtil().showDialog(DialogType.SUCCESS, "Reserva realizada com sucesso");
					break;
				default:
					return;
			}
			
			printComprovante(emprestimo, PrintableBuilder.TipoComprovante.EMPRESTIMO);
			reset();
		}catch(ValidationException e){
			getDialogUtil().showDialog(DialogType.ERROR, e.getMessage());
		}
	}
	
	private void printComprovante(Emprestimo emprestimo, PrintableBuilder.TipoComprovante tipo){
		Printable comprovante = PrintableBuilder.buildComprovanteEmprestimo(emprestimo, PrintableBuilder.TipoComprovante.EMPRESTIMO);
		PrintableDataHolder dataHolder = new PrintableDataHolder(ComprovanteEmprestimoPrintable.NAME, comprovante);
		setPrintable(dataHolder);
	}
	
	public boolean isDisponivel(){
		if(copia == null){
			return Boolean.FALSE;
		}else{
			return copia.getEstado() == EstadoCopia.DISPONIVEL;
		}
	}
	
	public boolean isEmprestada(){
		if(copia == null){
			return Boolean.FALSE;
		}else{
			return copia.getEstado() == EstadoCopia.EMPRESTADA;
		}
	}
	
	public boolean isReservada(){
		if(copia == null){
			return Boolean.FALSE;
		}else{
			return copia.getEstado() == EstadoCopia.RESERVADA;
		}
	}
	
	public Copia getCopia() {
		return copia;
	}

	public void setCopia(Copia copia) {
		this.copia = copia;
	}

	public String getCodCopia() {
		return codCopia;
	}

	public void setCodCopia(String codCopia) {
		this.codCopia = codCopia;
	}

	public Boolean getDisplayBuscaUsuario() {
		return displayBuscaUsuario;
	}

	public void setDisplayBuscaUsuario(Boolean displayBuscaUsuario) {
		this.displayBuscaUsuario = displayBuscaUsuario;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public String getCodLivro() {
		return codLivro;
	}

	public void setCodLivro(String codLivro) {
		this.codLivro = codLivro;
	}

}
