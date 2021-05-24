package br.com.OrdemDeServico.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.OrdemDeServico.cliente.Cliente;
import br.com.OrdemDeServico.ordemServico.OrdemdeServico;
import br.com.OrdemDeServico.usuario.Usuario;

public class JDBCOrdemServicoDAO {

	private Connection conexao;

	public JDBCOrdemServicoDAO(Connection conexao) {
		this.conexao = conexao;
	}

	public List<Cliente> buscarCliente(String nome) {

		String comando = "SELECT * FROM clientes ";

		if (!nome.equals("null") && !nome.equals("")) {
			comando += "WHERE cliente LIKE '%" + nome + "%'";
		}
		List<Cliente> listCliente = new ArrayList<Cliente>();

		try {
			java.sql.Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(comando);
			while (rs.next()) {
				Cliente cliente = new Cliente();
				String nomeCliente = rs.getString("cliente");
				int id = rs.getInt("idClientes");

				cliente.setId(id);
				cliente.setCliente(nomeCliente);

				listCliente.add(cliente);

			}

		} catch (Exception e) {
			e.printStackTrace();

		}

		return listCliente;
	}

	public boolean inserirOrdemServico(OrdemdeServico ordemdeServico) {
		
		
           String comando = "INSERT into ordemdeservico "+"(idclienteordemdeservico ,dataEntrada,dataEntrega,equipamento,valorServico,descricaoOrdemServico)"+"VALUES (?,?,?,?,?,?)";
		
           PreparedStatement p;	
		 
		    try {
			
	          p=this.conexao.prepareStatement(comando);
			
	       
	           p.setInt(1,ordemdeServico.getIdcliente());	           
			   p.setString(2,ordemdeServico.getDataEntrada());
			   p.setString(3,ordemdeServico.getDataEntrega() );
			   p.setString(4,ordemdeServico.getEquipamento());
			   p.setString(5,ordemdeServico.getValorServico());
			   p.setString(6,ordemdeServico.getDescricaoOrdemServico());
			   
			   p.execute();
			
		      }catch(SQLException e){
		       e.printStackTrace();
		       return false;
			
		}
		
		
		return true;
		
	}


	public List<OrdemdeServico> buscarOrdemServicoData(String dataEntrada, String dataEntrega){
			
		
		String comando = "SELECT * FROM ordemdeservico";
		 
	
		comando += " WHERE dataEntrada BETWEEN '"+dataEntrada+"' and '"+dataEntrega+"'";

			List<OrdemdeServico> listOrdensServico = new ArrayList<OrdemdeServico>();
			
			OrdemdeServico ordens = null;
			
			try{
				
				java.sql.Statement stmt = conexao.createStatement();
				
		    	ResultSet rs = stmt.executeQuery(comando);
		    	
		    	while(rs.next()) {
		    		
		    	ordens = new OrdemdeServico();
		    	
		    	
		        int idordemdeservico=rs.getInt("idordemdeservico");
	    		String cliente=rs.getString("idclienteordemdeservico");
	    		String DescricaoOrdemServico=rs.getString("DescricaoOrdemServico");
	    		String dataEntrada1=rs.getString("dataEntrada");
	    		String dataEntrega1=rs.getString("dataEntrega");
	    		String valorServico=rs.getString("valorServico");
	    		
	            ordens.setId(idordemdeservico);
	            ordens.setCliente(cliente);
	    		ordens.setDataEntrada(dataEntrada1);
	    		ordens.setDataEntrega(dataEntrega1);
	    		ordens.setDescricaoOrdemServico(DescricaoOrdemServico);
	    		ordens.setValorServico(valorServico);
	    		
	    	
	    		listOrdensServico.add(ordens);
		    	}
		 	
			} catch(Exception e) {
			e.printStackTrace();
			}
			
			return listOrdensServico;
	}

	
	
	
	
	public OrdemdeServico buscarPorId(int id) {
		
		
		String query = "select * from ordemdeservico inner join clientes on clientes.idclientes = ordemdeservico.idclienteordemdeservico where ordemdeservico.idordemdeservico =" + id;
		
		OrdemdeServico ordens = new OrdemdeServico();
	   
		try {
	    	 
			 java.sql.Statement stmt = conexao.createStatement();
		    	
	    	 ResultSet rs = stmt.executeQuery(query);
	    	
	    	 
	    	
	    	 while(rs.next()) {
	    		
	    		    int idordemdeservico=rs.getInt("idordemdeservico");
	    			String cliente=rs.getString("cliente");
		    		String dataEntrada=rs.getString("dataEntrada");
		    		String dataEntrega=rs.getString("dataEntrega");
		    		String equipamento=rs.getString("equipamento");
		    		String valorServico=rs.getString("valorServico");
		    		String DescricaoOrdemServico=rs.getString("DescricaoOrdemServico");
		    		
		            ordens.setId(idordemdeservico);
		            ordens.setCliente(cliente);
		    		ordens.setDataEntrada(dataEntrada);
		    		ordens.setDataEntrega(dataEntrega);
		    		ordens.setEquipamento(equipamento);
		    		ordens.setValorServico(valorServico);
		    		ordens.setDescricaoOrdemServico(DescricaoOrdemServico);
	    	
	    	 } 
	       	 
	     }catch(Exception e){
	      e.printStackTrace();
	     }
	  
	     return ordens;	
	}















}
