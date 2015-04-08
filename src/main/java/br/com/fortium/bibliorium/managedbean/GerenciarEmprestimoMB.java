package br.com.fortium.bibliorium.managedbean;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.fortium.bibliorium.persistence.entity.Copia;
import br.com.fortium.bibliorium.persistence.entity.Usuario;
import br.com.fortium.bibliorium.persistence.enumeration.EstadoCopia;
import br.com.fortium.bibliorium.service.CopiaService;
import br.com.fortium.bibliorium.service.EmprestimoService;
import br.com.fortium.bibliorium.service.UsuarioService;

@ManagedBean
@ViewScoped
public class GerenciarEmprestimoMB extends AbstractManagedBean<GerenciarEmprestimoMB> {

	private static final long serialVersionUID = 2249645974635438267L;
	
	private String codCopia;
	private String cpf;
	
	private Copia copia;
	private Usuario leitor;
	
	private Boolean displayBuscaLeitor;
	private Boolean actionPicked;
	
	@EJB
	private CopiaService copiaService;
	
	@EJB
	private EmprestimoService emprestimoService;
	
	@EJB
	private UsuarioService usuarioService;
	
	@Override
	protected void init() {
		
	}
	
	public void buscarCopia(){
		Long idCopia = null;
		
		try{
			idCopia = Long.parseLong(codCopia);
			copia = copiaService.buscar(idCopia);
		}catch(NumberFormatException e){
			copia = null;
		}
	}
	
	public void emprestar(){
		setDisplayBuscaLeitor(Boolean.TRUE);
		setActionPicked(Boolean.TRUE);
	}
	
	public void buscarLeitor(){
		leitor = usuarioService.buscar(cpf);
	}
	
	public void reset(){
		setCpf(null);
		setCopia(null);
		setLeitor(null);
		setCodCopia(null);
		setActionPicked(Boolean.FALSE);
		setDisplayBuscaLeitor(Boolean.FALSE);
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

	public Boolean getDisplayBuscaLeitor() {
		return displayBuscaLeitor;
	}

	public void setDisplayBuscaLeitor(Boolean displayBuscaLeitor) {
		this.displayBuscaLeitor = displayBuscaLeitor;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Usuario getLeitor() {
		return leitor;
	}

	public void setLeitor(Usuario leitor) {
		this.leitor = leitor;
	}

	public Boolean getActionPicked() {
		return actionPicked;
	}

	public void setActionPicked(Boolean actionPicked) {
		this.actionPicked = actionPicked;
	}

}
