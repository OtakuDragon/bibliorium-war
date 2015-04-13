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
	
	private Usuario leitor;
	private boolean editing;
	
	@EJB
	private UsuarioService usuarioService; 
	
	@Override
	protected void init() {
		leitor = getUsuarioAutenticado();
	}
	
	public void editar(){
		usuarioService.update(leitor);
		toggleEditing();
	}

	public void toggleEditing(){
		setEditing(!isEditing());
	}
	
	public Usuario getLeitor() {
		return leitor;
	}

	public void setLeitor(Usuario leitor) {
		this.leitor = leitor;
	}

	public boolean isEditing() {
		return editing;
	}

	public void setEditing(boolean editing) {
		this.editing = editing;
	}

}
