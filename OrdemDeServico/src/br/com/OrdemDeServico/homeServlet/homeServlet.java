//Ordem de Serviço
package br.com.OrdemDeServico.homeServlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class homeServlet extends HttpServlet {

	
private static final long serialVersionUID=1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		
		RequestDispatcher view = request.getRequestDispatcher("app/home.html");
		view.forward(request, response);
		
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
		
	}
	
	
	
}
