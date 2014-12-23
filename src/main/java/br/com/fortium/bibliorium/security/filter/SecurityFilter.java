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

import br.com.fortium.bibliorium.managedbean.login.LoginBean;
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
		
		TipoUsuario tipo = (TipoUsuario)httpRequest.getSession(true).getAttribute(Usuario.AUTENTICADO);
		
		if(tipo == null){
			redirectToLoginWithError(httpRequest, httpResponse, "Acesso negado: Você não está logado no sistema ou a sua sessão expirou.");
			return;
		}else{
			if(upperUrl.contains("1/LEITOR") && (tipo == TipoUsuario.PROFESSOR || tipo == TipoUsuario.ALUNO)){
				doFilter(httpRequest, response, chain);
			}else if(upperUrl.contains("1/BIBLIOTECARIO") && (tipo == TipoUsuario.BIBLIOTECARIO)){
				doFilter(httpRequest, response, chain);
			}else{
				redirectToLoginWithError(httpRequest, httpResponse, "Acesso negado: Você não tem permissão para acessar esta funcionalidade.");
				return;
			}
		}
	}

	@Override
	public void destroy() {}
	
	private void redirectToLoginWithError(HttpServletRequest httpRequest, HttpServletResponse httpResponse, String errorMessage) throws IOException{
		httpRequest.getSession().setAttribute(LoginBean.MENSAGEM_ACESSO_NEGADO, errorMessage);
		httpResponse.sendRedirect("/bibliorium/login.xhtml");
	}
}
