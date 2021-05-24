// Ordem de Serviço
package br.com.OrdemDeServico.LoginServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;

import br.com.OrdemDeServico.bd.conexao.Conexao;
import br.com.OrdemDeServico.jdbc.JDBCUsuarioDAO;



public class LoginServlet extends HttpServlet {
	
		
		private static final long serialVersionUID=1l;
		
		@Override
		protected void doGet(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
				
		}
		
		@Override
		protected void doPost(HttpServletRequest request, HttpServletResponse response )
		throws ServletException, IOException {
			
			PrintWriter omt = response.getWriter();
		    String usuario = request.getParameter("usuario");
		    String senha = request.getParameter("senha");
		    
		
		    byte[] base64decodedBytes = Base64.getDecoder().decode(senha.getBytes());
		    String odt = new String(base64decodedBytes, "utf-8");
		       
		    String senhamd5;
			   
		     try {
		         MessageDigest md = MessageDigest.getInstance("MD5");
		        md.update(odt.getBytes());
		        byte[] digest = md.digest();
		        senhamd5 = DatatypeConverter.printHexBinary(digest).toUpperCase();
		        } catch (NoSuchAlgorithmException e) {
				throw new RuntimeException(e);
			}
		        
		Conexao conec = new Conexao();
		
		Connection conexao = conec.abrirConexao();

		JDBCUsuarioDAO objetoDAO = new JDBCUsuarioDAO(conexao);
		
		
	     String context = request.getServletContext().getContextPath();
		
		
		if (objetoDAO.validarUsuario(request.getParameter("usuario"),senhamd5)) {		
			HttpSession sessao = request.getSession();
			sessao.setAttribute("login", request.getParameter("usuario"));
			
			response.setStatus(200);
			
		}else {
			response.setStatus(403);
		}
		
		
	    }

	}

	
	
	

