package br.com.fortium.bibliorium.security.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.fortium.bibliorium.managedbean.LoginMB;
import br.com.fortium.bibliorium.persistence.entity.Usuario;
import br.com.fortium.bibliorium.persistence.enumeration.TipoUsuario;

public class SecurityFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest  httpRequest  = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse)response;
		
		String upperUrl = httpRequest.getRequestURL().toString().toUpperCase();
		
		Usuario usuario = (Usuario)httpRequest.getSession(true).getAttribute(Usuario.AUTENTICADO);
		
		TipoUsuario tipo = null;
		
		if(usuario != null){
			tipo = usuario.getTipo();
		}
		
		if(!upperUrl.contains("PAGES/BIBLIOTECARIO") && !upperUrl.contains("PAGES/ALL")){
			chain.doFilter(httpRequest, response);
		}else if(tipo == null){
			redirectToLoginWithError(httpRequest, httpResponse, "Acesso negado: Você não está logado no sistema ou a sua sessão expirou.");
			return;
		}else{
			if(upperUrl.contains("PAGES/BIBLIOTECARIO") && (tipo == TipoUsuario.BIBLIOTECARIO)){
				chain.doFilter(httpRequest, response);
			}else if(upperUrl.contains("PAGES/ALL") && ((tipo == TipoUsuario.BIBLIOTECARIO) || (tipo == TipoUsuario.ALUNO) || (tipo == TipoUsuario.PROFESSOR))){
				chain.doFilter(httpRequest, response);
			}else{
				redirectToLoginWithError(httpRequest, httpResponse, "Acesso negado: Você não tem permissão para acessar esta funcionalidade.");
				return;
			}
		}
	}

	@Override
	public void destroy() {}
	
	private void redirectToLoginWithError(HttpServletRequest httpRequest, HttpServletResponse httpResponse, String errorMessage) throws IOException{
		//Define a mensagem de erro de acesso ilegal que aparecerá na tela de login
		httpRequest.getSession().setAttribute(LoginMB.MENSAGEM_ACESSO_NEGADO, errorMessage);
		//Desloga o usuario.
		httpRequest.getSession().setAttribute(Usuario.AUTENTICADO, null);
		//Redireciona para a pagina de login
		httpResponse.sendRedirect("/bibliorium/pages/login.xhtml");
	}
}
