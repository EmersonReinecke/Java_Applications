package br.com.agendaContatoRest.jdbc;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.agendaContatoRest.jdbcinterface.ContatoDAO;
import br.com.agendaContatoRest.objetos.Contato;

public class JDBCContatoDAO implements ContatoDAO {

	private Connection conexao;
	
	public JDBCContatoDAO (Connection conexao){
		this.conexao=conexao;
	}
	
	public boolean inserir(Contato contato){
		String comando="INSERT INTO contato"+
	"(nome, endereco, telefone, senha, email)"+
				"VALUES (?,?,?,?,?)";
		
		PreparedStatement p;
	
		try{
			p=this.conexao.prepareStatement(comando);
			p.setString(1,contato.getNome());
			p.setString(2,contato.getEndereco());
			p.setString(3,contato.getTelefone());
			p.setString(4,contato.getSenha());
			p.setString(5,contato.getEmail());
			p.execute();
		}catch(SQLException e){
			e.printStackTrace();
			return false;
		}
	return true;
		
	}
	
	
	public List<Contato> buscarPorNome(String nome){
		String comando = "SELECT * FROM contato ";
		if(!nome.equals("null") && !nome.equals("")){
			comando += "WHERE nome LIKE ' "+nome+"%'";
		}
	List<Contato> listContato = new ArrayList<Contato>();
	Contato contato = null;
	try{
		java.sql.Statement stmt = conexao.createStatement();
		ResultSet rs = stmt.executeQuery(comando);
		while(rs.next()){
			contato = new Contato();
			String nomeContato = rs.getString("nome");
			String ende = rs.getString("endereco");
			int id= rs.getInt("idContato");
			String telefone = rs.getString("telefone");
			String email= rs.getString("email");
			String senha= rs.getString("senha");
			
			contato.setId(id);
			contato.setNome(nomeContato);
			contato.setEndereco(ende);
			contato.setTelefone(telefone);
			contato.setEmail(email);
			contato.setSenha(senha);
		
			listContato.add(contato);
		}
	}catch (Exception e){
		e.printStackTrace();
	}
	return listContato;
	}
	
	
	public boolean deletarContato (int id){
		String comando = "DELETE from contato WHERE idContato="+id;
		Statement p;
		try{
			p = this.conexao.createStatement();
			p.execute(comando);
		}catch (SQLException e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	public Contato buscarPorId(int id){
		String comando = "SELECT * FROM contato WHERE idContato="+id;
		Contato contato = new Contato();
		try{
			java.sql.Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(comando);
			while(rs.next()){
				String nomeContato = rs.getString("nome");
				String endereco = rs.getString("endereco");
				int idContato = rs.getInt("idContato");
				String telefone= rs.getString("telefone");
				String email= rs.getString("email");
				
				contato.setId(idContato);
				contato.setNome(nomeContato);
				contato.setEndereco(endereco);
				contato.setTelefone(telefone);
				contato.setEmail(email);  
			    }
		}catch (Exception e){
			e.printStackTrace();				
				}
	     return contato;	
	}
	

   public boolean atualizar(Contato contato){
	   boolean senhaEditada = false;
	   String comando = "UPDATE contato SET nome=?, endereco=?, telefone=?, email=? ";
	   if(contato.getSenha()==null || contato.getSenha()==""){
		   comando+="WHERE idContato="+contato.getId();
	   }else{
		   senhaEditada=true;
		   comando+=", senha=? WHERE idContato="+contato.getId();
		   
		  }
	   PreparedStatement p;
	   try{
		   p = this.conexao.prepareStatement(comando);
		   p.setString(1, contato.getNome());
		   p.setString(2,contato.getEndereco());
		   p.setString(3,contato.getTelefone());
		   p.setString(4,contato.getEmail());
		   
		   if(senhaEditada){
			   p.setString(5,contato.getSenha());
			      }
		   p.executeUpdate();
	   }catch (SQLException e){
		   e.printStackTrace();
		   return false;
	   }return true;



}

	}
