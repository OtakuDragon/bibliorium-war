package br.com.fortium.bibliorium.security.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.fortium.bibliorium.managedbean.login.LoginBean;

/**
 * Servlet implementation class SecurityServlet
 */
public class SecurityServlet extends HttpServlet {

	private static final long serialVersionUID = 5267584328034875836L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String requestOriginal = request.getRequestURL().toString().split("://")[1];
		String folderNames = requestOriginal.substring(requestOriginal.indexOf("/"), requestOriginal.lastIndexOf("/"));
		
		String lowerFolderNames = folderNames.toLowerCase();
		
		if(! folderNames.equals(lowerFolderNames)){
			request.getSession().setAttribute(LoginBean.MENSAGEM_ACESSO_NEGADO, "Acesso negado: Nice try... nada de tentar burlar minha segurança.");
			response.sendRedirect("/bibliorium/pages/login.xhtml");
		}
	}
}
