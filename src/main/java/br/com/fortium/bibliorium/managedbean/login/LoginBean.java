package br.com.fortium.bibliorium.managedbean.login;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ComponentSystemEvent;

import br.com.fortium.bibliorium.managedbean.AbstractManagedBean;
import br.com.fortium.bibliorium.persistence.entity.Usuario;
import br.com.fortium.bibliorium.persistence.enumeration.TipoUsuario;
import br.com.fortium.bibliorium.service.UsuarioService;

@ManagedBean
@RequestScoped
public class LoginBean extends AbstractManagedBean{
	
	public static final String MENSAGEM_ACESSO_NEGADO = "MensagemAcessoNegado";
	
	private String cpf;
	private String senha;
	
	@EJB
	private UsuarioService usuarioService;
	
	public String efetuarLogin(){
		
		TipoUsuario tipo = usuarioService.autenticarUsuario(cpf, senha);
		
		if(tipo == null){
			addMessage(FacesMessage.SEVERITY_ERROR, "Usuário/Senha inválidos, Tente Novamente.", null);
			return null;
		}else{
			getSession().setAttribute(Usuario.AUTENTICADO, tipo);
			switch(tipo){
				case ALUNO:
				case PROFESSOR:
					return "LEITOR/index.xhtml";
				case BIBLIOTECARIO:
					return "BIBLIOTECARIO/index.xhtml";
				default:
					addMessage(FacesMessage.SEVERITY_ERROR, "Tipo de usuário inválido.", null);
					return null;
			}
		}
	}
	
	public void exibirMsgAcessoNegado(ComponentSystemEvent event){
		String mensagem = (String)getSession().getAttribute(MENSAGEM_ACESSO_NEGADO);
		if(mensagem != null){
			addMessage(FacesMessage.SEVERITY_ERROR, mensagem, null);
		}
		getSession().setAttribute(MENSAGEM_ACESSO_NEGADO, null);
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
