//Ordem de Serviço
package br.com.OrdemDeServico.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.OrdemDeServico.usuario.Usuario;

public class JDBCUsuarioDAO {
	

	private Connection conexao;
	
	public JDBCUsuarioDAO(Connection conexao) {
		this.conexao = conexao;

	}

	public boolean validarUsuario(String usuario, String senhamd5) {
		try {
			String comando = "SELECT nome FROM usuario WHERE nome = '" + usuario + "' AND senha = '"+ senhamd5 + "';";
			Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(comando);

			if (rs.next()) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean inserir(Usuario usuario) {
		
		String comando = "INSERT into usuario " + "(nome,senha,email,telefone)"+"VALUES (?,?,?,?)";
		
		PreparedStatement p;
		
		try {
			
			p=this.conexao.prepareStatement(comando);
			
			p.setString(1,usuario.getNome());
			p.setString(2,usuario.getSenha());
			p.setString(3,usuario.getEmail());
			p.setString(4,usuario.getTelefone());
	        p.execute();
	        }catch(SQLException e){
		e.printStackTrace();
		return false;
	   }
	    
		return true;
	}

	public List<Usuario> buscarPorNome(String nome){
	
		String comando = "SELECT * FROM usuario ";
		
		if(!nome.equals("null")&& !nome.equals("")) {
			comando += "WHERE nome LIKE '%"+nome+"%'";
		}
		List<Usuario> listUsuario = new ArrayList<Usuario>();
		Usuario usuario = null;
	    try {
	    	java.sql.Statement stmt = conexao.createStatement();
	    	ResultSet rs = stmt.executeQuery(comando);
	    	while(rs.next()) {
	    		usuario = new Usuario();
	    		String nomeUsuario = rs.getString("nome");
	    		String email = rs.getString("email");
	    		int id=rs.getInt("idUsuario");
	    		String telefone=rs.getString("telefone");
	    		
	    	
	    		usuario.setId(id);
	    		usuario.setNome(nomeUsuario);
	    		usuario.setEmail(email);
	    		usuario.setTelefone(telefone);
	    		
	    	
	    		listUsuario.add(usuario);
	    		
					
				
	    		
	    	}
	    	
	    	
	    	
	    }catch(Exception e) {
	    	e.printStackTrace();
	    	
	    }
	
	    return listUsuario;
	}
	
	
	public boolean deletarUsuario (int id) {
		
		String comando = "DELETE from usuario WHERE idUsuario="+id;
		Statement p;
		
		try {
			p=this.conexao.createStatement();
			p.execute(comando);
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
		
	}
	
	
	
	
	public Usuario buscarPorId(int id) {
		String comando = "SELECT * FROM usuario WHERE idUsuario="+id;
		Usuario usuario = new Usuario();
	     try {
	    	 
	    	 java.sql.Statement stmt = conexao.createStatement();
	    	
	    	 ResultSet rs = stmt.executeQuery(comando);
	    	
	    	 while(rs.next()) {
	    		String nomeUsuario = rs.getString("nome");
		    	String email = rs.getString("email");
		    	int idUsuario=rs.getInt("idUsuario");
		    	String telefone=rs.getString("telefone");
	    		 
	    		 usuario.setId(idUsuario);
	    		 usuario.setNome(nomeUsuario);
	    		 usuario.setEmail(email);
	    		 usuario.setTelefone(telefone);	 
	    		 
	    	
	    		 
	    	 }
	     }catch(Exception e){
	      e.printStackTrace();
	     }
	  
	     return usuario;	
	}
	
	public boolean atualizar(Usuario usuario) {
		
		String comando = "UPDATE usuario SET nome=?, email=?, telefone=? ";
		comando +="WHERE idUsuario= "+usuario.getId();
		PreparedStatement p;
		try {
			
			p=this.conexao.prepareStatement(comando);
			
			p.setString(1, usuario.getNome());
			p.setString(2, usuario.getEmail());
			p.setString(3,usuario.getTelefone());
			
		
			
		    p.executeUpdate();	
		
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}return true;	
		
	}

	
	
}
