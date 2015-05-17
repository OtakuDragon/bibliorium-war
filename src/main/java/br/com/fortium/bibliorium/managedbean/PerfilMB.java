package br.com.fortium.bibliorium.managedbean;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.fortium.bibliorium.persistence.entity.Usuario;
import br.com.fortium.bibliorium.service.UsuarioService;

@ManagedBean
@ViewScoped
public class PerfilMB extends AbstractManagedBean<PerfilMB> {

	private static final long serialVersionUID = -3316966153212939392L;
	
	private Usuario usuario;
	private boolean editing;
	
	@EJB
	private UsuarioService usuarioService; 
	
	@Override
	protected void init() {
		usuario = getUsuarioAutenticado();
	}
	
	public void editar(){
		usuarioService.update(usuario);
		toggleEditing();
	}

	public void toggleEditing(){
		setEditing(!isEditing());
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public boolean isEditing() {
		return editing;
	}

	public void setEditing(boolean editing) {
		this.editing = editing;
	}

}
