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
public class ConsultarUsuarioMB extends AbstractManagedBean<ConsultarUsuarioMB> {

	private static final long serialVersionUID = 8656997963954115958L;
	
	private Usuario usuario;
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
			usuario = usuarioService.buscarComInativos(cpf);
		}
	}
	
	public void reset(){
		cpf = "";
		usuario = null;
	}
	
	public void toggleAcesso(){
		if((usuario.getTipo() == TipoUsuario.BIBLIOTECARIO) && !getUsuarioAutenticado().equals(usuario)){
			getDialogUtil().showDialog(DialogType.SUCCESS, "Não é possivel modificar o acesso de outros bibliotecarios.");
		}
		
		EstadoUsuario estado = usuario.getEstado();
		
		switch(estado){
			case ATIVO:
				usuario.setEstado(EstadoUsuario.INATIVO);
				usuarioService.update(usuario);
				getDialogUtil().showDialog(DialogType.SUCCESS, "Usuário bloqueado com sucesso.");
				break;
			case INATIVO:
				usuario.setEstado(EstadoUsuario.ATIVO);
				usuarioService.update(usuario);
				getDialogUtil().showDialog(DialogType.SUCCESS, "Usuário desbloqueado com sucesso.");
				break;
			case INADIMPLENTE:
				getDialogUtil().showDialog(DialogType.WARNING, "O Usuário está inadimplente.");
		}
	}
	
	public void resetarSenha(){
		usuario.setSenha(usuario.getCpf());
		usuarioService.update(usuario);
		getDialogUtil().showDialog(DialogType.SUCCESS, "Senha resetada com sucesso.");
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

}
