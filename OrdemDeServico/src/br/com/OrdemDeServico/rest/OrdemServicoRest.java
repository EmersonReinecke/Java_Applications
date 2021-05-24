package br.com.OrdemDeServico.rest;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;

import br.com.OrdemDeServico.bd.conexao.Conexao;
import br.com.OrdemDeServico.cliente.Cliente;
import br.com.OrdemDeServico.jdbc.JDBCClienteDAO;
import br.com.OrdemDeServico.jdbc.JDBCOrdemServicoDAO;
import br.com.OrdemDeServico.ordemServico.OrdemdeServico;

@Path("OrdemServico")
public class OrdemServicoRest extends UtilRest {

	public OrdemServicoRest() {

	}

@POST
@Path ("/consultaClientesNome/{cliente}")
@Produces ({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
  public Response consultaClientesNome(@PathParam("cliente") String cliente) {
	  try {
		List<Cliente> clientes = new ArrayList<Cliente>();
		
		Conexao conec = new Conexao();
		Connection conexao = conec.abrirConexao();
		JDBCOrdemServicoDAO jdbcOrdemServico = new JDBCOrdemServicoDAO(conexao);
		
		clientes = jdbcOrdemServico.buscarCliente(cliente);
		conec.fecharConexao();
		
		return this.buildResponse(clientes);
		
		
		
	}catch(Exception e) {
	e.printStackTrace();
	return this.buildErrorResponse(e.getMessage());
	}

	   }


    @POST
    @Path("/addOrdemServico")
	@Consumes("application/json")
	@Produces(MediaType.APPLICATION_JSON)
    public Response addOrdemServico (String novaOrdem) {
    
    	try {
    		
    		OrdemdeServico ordemdeServico = new ObjectMapper().readValue(novaOrdem, OrdemdeServico.class);
    		
    		Conexao conec = new Conexao();
    	    Connection conexao = conec.abrirConexao();
    	    JDBCOrdemServicoDAO jdbcOrdemServico = new JDBCOrdemServicoDAO(conexao);
    	    jdbcOrdemServico.inserirOrdemServico(ordemdeServico);
      	    conec.fecharConexao();
    	    
    	    
    	    return this.buildResponse("Ordem de Serviço criada com Sucesso!");
    	    
    		
    		
    		
    	}catch(Exception e){
    	e.printStackTrace();
    	return this.buildErrorResponse(e.getMessage());
    	}
    	
    	
    	
    }
 
   
    
    @POST
	@Path("/buscarOrdemServicoData/{dataEntrada},{dataEntrega}")
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
   public Response buscarOrdemServicoData (@PathParam("dataEntrada")String dataEntrada, @PathParam("dataEntrega")String dataEntrega) {
	   
    	try {
    		
    		
    		
    		List<OrdemdeServico> ordensdeServico = new ArrayList<OrdemdeServico>();
    		
    		Conexao conec = new Conexao();
    		
    		Connection conexao = conec.abrirConexao();
    		JDBCOrdemServicoDAO jdbcOrdemServico = new JDBCOrdemServicoDAO(conexao);
    	
    		ordensdeServico = jdbcOrdemServico.buscarOrdemServicoData(dataEntrada,dataEntrega);
    		conec.fecharConexao();
    		
    
    		
    		return this.buildResponse(ordensdeServico);
    		
    		
    	}catch(Exception e){
    	e.printStackTrace();
    	return this.buildErrorResponse(e.getMessage());
    	}
    	 
   }
    
    
	@GET
	@Path("/buscarOrdemServicoId/{id}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	
	public Response buscarOrdemServicoId (@PathParam("id") int id) {
		
		try {
			
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCOrdemServicoDAO jdbcOrdemServico = new JDBCOrdemServicoDAO(conexao);
			OrdemdeServico ordemServico = jdbcOrdemServico.buscarPorId(id);
		
			
			
			
			return this.buildResponse(ordemServico);
		}catch(Exception e){
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}	
		
	}




    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
   
   
   



}
