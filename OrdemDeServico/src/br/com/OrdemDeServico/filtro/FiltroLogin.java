//Ordem de Serviço
package br.com.OrdemDeServico.filtro;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter("/home")
public class FiltroLogin implements Filter {
	
	public void destroy() {

	}
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String context = request.getServletContext().getContextPath();

		try {

			HttpSession sessao = ((HttpServletRequest) request).getSession();
			String usuario = null;
			if (sessao != null) {
				usuario = (String) sessao.getAttribute("login");
			}
		
			if (usuario == null) {
				sessao.setAttribute("msg", "Voce não esta logado!");

				((HttpServletResponse) response).sendRedirect(context + "/Login.html");

			} else {
				// Caso a resposta seja 200 o usuario sera redirecionado para pagina home
				chain.doFilter(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
     

	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	
	
}
