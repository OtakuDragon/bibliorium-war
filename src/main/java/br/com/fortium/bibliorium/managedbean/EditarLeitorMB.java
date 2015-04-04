package br.com.fortium.bibliorium.managedbean;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.fortium.bibliorium.enumeration.DialogType;
import br.com.fortium.bibliorium.persistence.entity.Usuario;
import br.com.fortium.bibliorium.service.UsuarioService;

@ManagedBean
@ViewScoped
public class EditarLeitorMB extends AbstractManagedBean<EditarLeitorMB>{

	private static final long serialVersionUID = -4286768534297216189L;
	
	private Usuario leitor;
	private Usuario leitorOriginal;
	
	@EJB
	private UsuarioService usuarioService;
	
	@Override
	protected void init() {
		leitor = (Usuario)extractSessionAttribute("leitor");
		if(leitor == null){
			redirectToHome();
		}else{
			try {
				leitorOriginal = (Usuario)leitor.clone();
			} catch (CloneNotSupportedException e) {
				getLogger().error(e.getMessage(), e);
			}
		}
	}

	public String editar(){
		if(!leitorOriginal.equals(leitor)){
			usuarioService.update(leitor);
			getDialogUtil().showDialog(DialogType.SUCCESS, "Leitor Alterado com sucesso!");
		}
		
		return "/pages/BIBLIOTECARIO/consultarLeitor";
	}
	
	public Usuario getLeitor() {
		return leitor;
	}

	public void setLeitor(Usuario leitor) {
		this.leitor = leitor;
	}
	
}
