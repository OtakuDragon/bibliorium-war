package br.com.fortium.bibliorium.managedbean;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Named;

import br.com.fortium.bibliorium.managedbean.generic.AbstractManagedBean;
import br.com.fortium.bibliorium.persistence.entity.Usuario;
import br.com.fortium.bibliorium.persistence.enumeration.TipoUsuario;
import br.com.fortium.bibliorium.service.UsuarioService;

@Named
@RequestScoped
public class LoginMB extends AbstractManagedBean<LoginMB>{

	private static final long serialVersionUID = -345315455106409853L;

	public static final String MENSAGEM_ACESSO_NEGADO = "MensagemAcessoNegado";
	
	private String cpf;
	private String senha;
	
	@EJB
	private UsuarioService usuarioService;
	
	public LoginMB() {
		super(LoginMB.class);
	}
	
	@Override
	protected void init() {
		// Nenhuma inicializa��o necessaria
	}
	
	public String efetuarLogin(){
		
		TipoUsuario tipo = usuarioService.autenticarUsuario(cpf, senha);
		
		if(tipo == null){
			addMessage(FacesMessage.SEVERITY_ERROR, "Usu�rio/Senha inv�lidos, Tente Novamente.", null);
			return null;
		}else{
			getSession().setAttribute(Usuario.AUTENTICADO, tipo);
			return getIndexPageForUserType(tipo);
		}
	}
	
	public String efetuarLogOff(){
		getSession().invalidate();
		addMessage(FacesMessage.SEVERITY_INFO, "Tchau, Volte Sempre!", null);
		return "/pages/login.xhtml";
	}
	
	public void exibirMsgAcessoNegado(ComponentSystemEvent event){
		String mensagem = (String)getSession().getAttribute(MENSAGEM_ACESSO_NEGADO);
		if(mensagem != null){
			addMessage(FacesMessage.SEVERITY_ERROR, mensagem, null);
		}
		getSession().setAttribute(MENSAGEM_ACESSO_NEGADO, null);
	}
	
	public String redirectToIndex(){
		TipoUsuario tipo = (TipoUsuario)getSession().getAttribute(Usuario.AUTENTICADO);
		return getIndexPageForUserType(tipo);
	}
	
	private String getIndexPageForUserType(TipoUsuario tipo){
		switch(tipo){
			case ALUNO:
			case PROFESSOR:
				return "/pages/LEITOR/index.xhtml";
			case BIBLIOTECARIO:
				return "/pages/BIBLIOTECARIO/index.xhtml";
			default:
				addMessage(FacesMessage.SEVERITY_ERROR, "Tipo de usu�rio inv�lido.", null);
				return null;
		}
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}