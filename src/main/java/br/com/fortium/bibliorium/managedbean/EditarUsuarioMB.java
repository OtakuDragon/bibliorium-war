package br.com.fortium.bibliorium.managedbean;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.fortium.bibliorium.enumeration.DialogType;
import br.com.fortium.bibliorium.persistence.entity.Usuario;
import br.com.fortium.bibliorium.service.UsuarioService;

@ManagedBean
@ViewScoped
public class EditarUsuarioMB extends AbstractManagedBean<EditarUsuarioMB>{

	private static final long serialVersionUID = -4286768534297216189L;
	
	private Usuario usuario;
	private Usuario usuarioOriginal;
	
	@EJB
	private UsuarioService usuarioService;
	
	@Override
	protected void init() {
		usuario = (Usuario)extractSessionAttribute("usuario");
		if(usuario == null){
			redirectToHome();
		}else{
			try {
				usuarioOriginal = (Usuario)usuario.clone();
			} catch (CloneNotSupportedException e) {
				getLogger().error(e.getMessage(), e);
			}
		}
	}

	public String editar(){
		if(!usuarioOriginal.equals(usuario)){
			usuarioService.update(usuario);
			getDialogUtil().showDialog(DialogType.SUCCESS, "Usuário Alterado com sucesso!");
		}
		
		return "/pages/BIBLIOTECARIO/consultarUsuario";
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}
