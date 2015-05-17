package br.com.fortium.bibliorium.managedbean;

import java.util.Date;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;

import br.com.fortium.bibliorium.enumeration.DialogType;
import br.com.fortium.bibliorium.persistence.entity.Usuario;
import br.com.fortium.bibliorium.persistence.enumeration.EstadoUsuario;
import br.com.fortium.bibliorium.persistence.enumeration.TipoUsuario;
import br.com.fortium.bibliorium.service.UsuarioService;

@Named
@RequestScoped
public class CadastrarUsuarioMB extends AbstractManagedBean<CadastrarUsuarioMB> {

	private static final long serialVersionUID = 1938994385587953115L;
	
	private Usuario usuario;
	
	@EJB
	private UsuarioService usuarioService;
	
	@Override
	protected void init() {
		setUsuario(new Usuario());
	}
	
	public void cadastrar(){
		usuario.setSenha(usuario.getCpf());
		usuario.setEstado(EstadoUsuario.ATIVO);
		usuario.setDataCadastro(new Date());
		
		usuarioService.cadastrar(usuario);
		
		getDialogUtil().showDialog(DialogType.SUCCESS, "Usuário cadastrado com sucesso!");
	}
	
	public void validarCpf(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		String cpf = (String)value;

		if(!cpf.matches("^\\d{11}")){
			throw new ValidatorException(new FacesMessage("CPF Inválido (11 Números)."));
		}
		
		if(usuarioService.isCpfCadastrado(cpf)){
			throw new ValidatorException(new FacesMessage("CPF já cadastrado"));
		}
	}
	
	public void validarEmail(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		String email = (String)value;
		
		if(!email.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")){
			throw new ValidatorException(new FacesMessage("Email Inválido."));
		}
		
		if(usuarioService.isEmailCadastrado(email)){
			throw new ValidatorException(new FacesMessage("Email já cadastrado"));
		}
	}
	
	public TipoUsuario[] getTiposUsuario(){
		return TipoUsuario.values();
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


}
