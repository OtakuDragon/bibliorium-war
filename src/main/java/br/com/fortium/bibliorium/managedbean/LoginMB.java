package br.com.fortium.bibliorium.managedbean;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Named;

import br.com.fortium.bibliorium.persistence.entity.Usuario;
import br.com.fortium.bibliorium.persistence.enumeration.TipoUsuario;
import br.com.fortium.bibliorium.service.UsuarioService;

@Named
@RequestScoped
public class LoginMB extends AbstractManagedBean<LoginMB>{

	private static final long serialVersionUID = -345315455106409853L;

	public static final String MENSAGEM_ACESSO_NEGADO = "MensagemAcessoNegado";
	public static final String HOME_PAGE_KEY = "homePage";
	
	private String cpf;
	private String senha;
	
	private String contextPath;
	
	@EJB
	private UsuarioService usuarioService;
	
	@Override
	protected void init() {
		// Nenhuma inicialização necessaria
	}
	
	public String efetuarLogin(){
		
		TipoUsuario tipo = usuarioService.autenticarUsuario(cpf, senha);
		
		if(tipo == null){
			addMessage(FacesMessage.SEVERITY_ERROR, "Usuário/Senha inválidos, Tente Novamente.", null);
			return null;
		}else{
			String homePage = getHomePage(tipo);
			saveHomePage(homePage);
			setSessionAttribute(Usuario.AUTENTICADO, tipo);
			return homePage;
		}
	}
	
	public String efetuarLogOff(){
		invalidateSession();
		addMessage(FacesMessage.SEVERITY_INFO, "Tchau, Volte Sempre!", null);
		return "/pages/login.xhtml";
	}
	
	public void exibirMsgAcessoNegado(ComponentSystemEvent event){
		String mensagem = (String) extractSessionAttribute(MENSAGEM_ACESSO_NEGADO);
		if(mensagem != null){
			addMessage(FacesMessage.SEVERITY_ERROR, mensagem, null);
		}
	}
	
	public String redirectToIndex(){
		TipoUsuario tipo = (TipoUsuario)getSessionAttribute(Usuario.AUTENTICADO);
		return getHomePage(tipo);
	}
	
	private String getHomePage(TipoUsuario tipo){
		switch(tipo){
			case ALUNO:
			case PROFESSOR:
				return "/pages/LEITOR/index.xhtml?faces-redirect=true";
			case BIBLIOTECARIO:
				return "/pages/BIBLIOTECARIO/index.xhtml?faces-redirect=true";
			default:
				addMessage(FacesMessage.SEVERITY_ERROR, "Tipo de usuário inválido.", null);
				return null;
		}
	}
	
	private void saveHomePage(String homePage){
		setSessionAttribute(HOME_PAGE_KEY, homePage);
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

	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

}
