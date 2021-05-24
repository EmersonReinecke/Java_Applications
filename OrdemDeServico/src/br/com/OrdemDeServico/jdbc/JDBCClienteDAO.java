package br.com.OrdemDeServico.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.OrdemDeServico.cliente.Cliente;
import br.com.OrdemDeServico.usuario.Usuario;



public class JDBCClienteDAO {
    
	private Connection conexao;
	
	public JDBCClienteDAO(Connection conexao) {
		this.conexao = conexao;

	}
	
	
	public boolean inserir(Cliente cliente) {
		
		String comando = "INSERT into clientes " + "(cliente,telefone,email,cpfcnpj,endereco)"+"VALUES (?,?,?,?,?)";
	
		PreparedStatement p;
		
		try {
			
			p=this.conexao.prepareStatement(comando);
			
			p.setString(1,cliente.getCliente());
			p.setString(2,cliente.getTelefone());
			p.setString(3,cliente.getEmail());
			p.setString(4,cliente.getCpfcnpj());
			p.setString(5,cliente.getEndereco());
			
	        p.execute();
	        
		}catch(SQLException e){
		e.printStackTrace();
		return false;
	   }
	    
		return true;
	}
	

	
	
	public List<Cliente> buscarPorCliente(String cliente){
		
		String comando = "SELECT * FROM clientes ";
		
		if(!cliente.equals("null")&& !cliente.equals("")&&!cliente.equals("undefined")) {
			comando += "WHERE cliente LIKE '%"+cliente+"%'";
		}
		List<Cliente> listCliente = new ArrayList<Cliente>();
		Cliente clientes = null;
	    try {
	    	java.sql.Statement stmt = conexao.createStatement();
	    	ResultSet rs = stmt.executeQuery(comando);
	    	while(rs.next()) {
	    		clientes = new Cliente();
	    		String nomeCliente = rs.getString("cliente");
	    		String email = rs.getString("email");
	    		int id=rs.getInt("idClientes");
	    		String telefone=rs.getString("telefone");
	    		String cpfcnpj=rs.getString("cpfcnpj");
	    		String endereco=rs.getString("endereco");
	    		
	    	
	    		clientes.setId(id);
	    	    clientes.setCliente(nomeCliente);
	    	    clientes.setEmail(email);
	    	    clientes.setTelefone(telefone);
	    	   clientes.setCpfcnpj(cpfcnpj);
	    	    clientes.setEndereco(endereco);
	    	
	    	
	    		listCliente.add(clientes);		
	    	}
	    	
	    	
	    	
	    }catch(Exception e) {
	    	e.printStackTrace();
	    	
	    }
	
	    return listCliente;
	}
	
	
	
	
	public boolean deletarCliente (int id) {
		
		String comando = "DELETE from clientes WHERE idClientes="+id;
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
	
	
	
	public Cliente buscarPorId(int id) {
		String comando = "SELECT * FROM clientes WHERE idClientes ="+id;
		Cliente cliente = new Cliente();
	     try {
	    	 
	    	 java.sql.Statement stmt = conexao.createStatement();
	    	
	    	 ResultSet rs = stmt.executeQuery(comando);
	    	
	    	 while(rs.next()) {
	    		String nomeCliente = rs.getString("cliente");
		    	String email = rs.getString("email");
		    	int idCliente=rs.getInt("idClientes");
		    	String telefone=rs.getString("telefone");
		    	String endereco=rs.getString("endereco");
		    	String cpfcnpj=rs.getString("cpfcnpj");
	    		 
	    		 cliente.setId(idCliente);
	    		 cliente.setCliente(nomeCliente);
	    		 cliente.setEmail(email);
	    		 cliente.setTelefone(telefone);	 
	    		 cliente.setEndereco(endereco);
	    		 cliente.setCpfcnpj(cpfcnpj);
	    	
	    		 
	    	 }
	     }catch(Exception e){
	      e.printStackTrace();
	     }
	  
	     return cliente;	
	}
	
public boolean atualizar(Cliente cliente) {
		
		String comando = "UPDATE clientes SET cliente=?, email=?, telefone=?, endereco=?, cpfcnpj=? ";
		comando +="WHERE idClientes= "+cliente.getId();
		PreparedStatement p;
		try {
			
			p=this.conexao.prepareStatement(comando);
			
			p.setString(1, cliente.getCliente());
			p.setString(2, cliente.getEmail());
			p.setString(3,cliente.getTelefone());
			p.setString(4,cliente.getEndereco());
			p.setString(5,cliente.getCpfcnpj());
			
		
			
		    p.executeUpdate();	
		
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}return true;	
		
	}

	
	
	
	
	
	
	
	
	
	
}


