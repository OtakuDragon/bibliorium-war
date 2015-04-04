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
public class CadastrarLeitorMB extends AbstractManagedBean<CadastrarLeitorMB> {

	private static final long serialVersionUID = 1938994385587953115L;
	
	private Usuario leitor;
	
	@EJB
	private UsuarioService usuarioService;
	
	@Override
	protected void init() {
		setLeitor(new Usuario());
	}
	
	public void cadastrar(){
		leitor.setSenha(leitor.getCpf());
		leitor.setEstado(EstadoUsuario.ATIVO);
		leitor.setDataCadastro(new Date());
		
		usuarioService.cadastrar(leitor);
		
		getDialogUtil().showDialog(DialogType.SUCCESS, "Leitor cadastrado com sucesso!");
	}
	
	public void validarCpf(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		String cpf = (String)value;

		if(!cpf.matches("^\\d{11}")){
			throw new ValidatorException(new FacesMessage("CPF Inv�lido (11 N�meros)."));
		}
		
		if(usuarioService.isCpfCadastrado(cpf)){
			throw new ValidatorException(new FacesMessage("CPF j� cadastrado"));
		}
	}
	
	public void validarEmail(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		String email = (String)value;
		
		if(!email.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")){
			throw new ValidatorException(new FacesMessage("Email Inv�lido."));
		}
		
		if(usuarioService.isEmailCadastrado(email)){
			throw new ValidatorException(new FacesMessage("Email j� cadastrado"));
		}
	}
	
	public TipoUsuario[] getTiposUsuario(){
		return TipoUsuario.values();
	}
	
	public Usuario getLeitor() {
		return leitor;
	}

	public void setLeitor(Usuario leitor) {
		this.leitor = leitor;
	}


}
