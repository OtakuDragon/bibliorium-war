package br.com.fortium.bibliorium.managedbean;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.fortium.bibliorium.enumeration.DialogType;
import br.com.fortium.bibliorium.persistence.entity.Usuario;
import br.com.fortium.bibliorium.persistence.enumeration.EstadoUsuario;
import br.com.fortium.bibliorium.persistence.enumeration.TipoUsuario;
import br.com.fortium.bibliorium.service.UsuarioService;

@ManagedBean
@ViewScoped
public class ConsultarLeitorMB extends AbstractManagedBean<ConsultarLeitorMB> {

	private static final long serialVersionUID = 8656997963954115958L;
	
	private Usuario leitor;
	private String  cpf;
	
	@EJB
	private UsuarioService usuarioService;
	
	@Override
	protected void init() {

	}
	
	public void pesquisar(){
		if(cpf == null || !cpf.matches("^\\d{11}")){
			getDialogUtil().showDialog(DialogType.ERROR, "CPF inválido, 11 números.");
		}else{
			leitor = usuarioService.buscarComInativos(cpf);
		}
	}
	
	public void reset(){
		cpf = "";
		leitor = null;
	}
	
	public void toggleAcesso(){
		if((leitor.getTipo() == TipoUsuario.BIBLIOTECARIO) && !getUsuarioAutenticado().equals(leitor)){
			getDialogUtil().showDialog(DialogType.SUCCESS, "Não é possivel modificar o acesso de outros bibliotecarios.");
		}
		
		EstadoUsuario estado = leitor.getEstado();
		
		switch(estado){
			case ATIVO:
				leitor.setEstado(EstadoUsuario.INATIVO);
				usuarioService.update(leitor);
				getDialogUtil().showDialog(DialogType.SUCCESS, "Usuário bloqueado com sucesso.");
				break;
			case INATIVO:
				leitor.setEstado(EstadoUsuario.ATIVO);
				usuarioService.update(leitor);
				getDialogUtil().showDialog(DialogType.SUCCESS, "Usuário desbloqueado com sucesso.");
				break;
			case INADIMPLENTE:
				getDialogUtil().showDialog(DialogType.WARNING, "O Usuário está inadimplente.");
		}
	}
	
	public void resetarSenha(){
		leitor.setSenha(leitor.getCpf());
		usuarioService.update(leitor);
		getDialogUtil().showDialog(DialogType.SUCCESS, "Senha resetada com sucesso.");
	}

	public Usuario getLeitor() {
		return leitor;
	}

	public void setLeitor(Usuario leitor) {
		this.leitor = leitor;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

}
