package br.com.fortium.bibliorium.managedbean.login;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ComponentSystemEvent;

import br.com.fortium.bibliorium.managedbean.AbstractManagedBean;
import br.com.fortium.bibliorium.persistence.eao.UsuarioEAO;
import br.com.fortium.bibliorium.persistence.entity.Usuario;
import br.com.fortium.bibliorium.persistence.enumeration.TipoUsuario;

@ManagedBean
@RequestScoped
public class LoginBean extends AbstractManagedBean{
	
	public static final String MENSAGEM_ACESSO_NEGADO = "MensagemAcessoNegado";
	
	private String cpf;
	private String senha;
	
	@EJB
	private UsuarioEAO usuarioEAO;
	
	public String efetuarLogin(){
		
		TipoUsuario tipo = usuarioEAO.autenticarUsuario(cpf, senha);
		
		if(tipo == null){
			addMessage(FacesMessage.SEVERITY_ERROR, "Usu�rio/Senha inv�lidos, Tente Novamente.", null);
			return null;
		}else{
			getSession().setAttribute(Usuario.AUTENTICADO, tipo);
			switch(tipo){
				case ALUNO:
				case PROFESSOR:
					return "1/LEITOR/index.xhtml";
				case BIBLIOTECARIO:
					return "1/BIBLIOTECARIO/index.xhtml";
				default:
					addMessage(FacesMessage.SEVERITY_ERROR, "Tipo de usu�rio inv�lido.", null);
					return null;
			}
		}
	}
	
	public void exibirMsgAcessoNegado(ComponentSystemEvent event){
		String mensagem = (String)getSession().getAttribute(MENSAGEM_ACESSO_NEGADO);
		if(mensagem != null){
			addMessage(FacesMessage.SEVERITY_ERROR, mensagem, null);
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