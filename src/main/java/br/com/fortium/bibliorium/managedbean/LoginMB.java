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
	
	@EJB
	private UsuarioService usuarioService;
	
	@Override
	protected void init() {
		// Nenhuma inicializa��o necessaria
	}
	
	public String efetuarLogin(){
		
		Usuario usuario = usuarioService.autenticarUsuario(cpf, senha);
		
		if(usuario == null){
			addMessage(FacesMessage.SEVERITY_ERROR, "Usu�rio/Senha inv�lidos, Tente Novamente.", null);
			return null;
		}else{
			String homePage = getHomePage(usuario.getTipo());
			saveHomePage(homePage);
			setSessionAttribute(Usuario.AUTENTICADO, usuario);
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
		TipoUsuario tipo = getUsuarioAutenticado().getTipo();
		return getHomePage(tipo);
	}
	
	public boolean isBibliotecario(){
		return getUsuarioAutenticado().getTipo() == TipoUsuario.BIBLIOTECARIO;
	}
	
	private String getHomePage(TipoUsuario tipo){
		switch(tipo){
			case ALUNO:
			case PROFESSOR:
				return "/pages/ALL/pesquisarLivro.xhtml?faces-redirect=true";
			case BIBLIOTECARIO:
				return "/pages/BIBLIOTECARIO/gerenciarEmprestimo.xhtml?faces-redirect=true";
			default:
				addMessage(FacesMessage.SEVERITY_ERROR, "Tipo de usu�rio inv�lido.", null);
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

}
