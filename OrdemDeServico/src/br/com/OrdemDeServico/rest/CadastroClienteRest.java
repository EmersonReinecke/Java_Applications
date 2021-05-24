package br.com.OrdemDeServico.rest;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
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
import br.com.OrdemDeServico.jdbc.JDBCUsuarioDAO;
import br.com.OrdemDeServico.usuario.Usuario;

@Path("CadastroCliente")
public class CadastroClienteRest extends UtilRest {
  
	
	 public CadastroClienteRest() {
	}
	
	 @POST
	 @Path("/addCliente")
	@Consumes("application/json")
	@Produces(MediaType.APPLICATION_JSON)
	 public Response addCliente (String novoCliente) {
		 try {
			 
			 Cliente cliente = new ObjectMapper().readValue(novoCliente, Cliente.class);
			 
			 Conexao conec = new Conexao();
			 Connection conexao = conec.abrirConexao();
			 JDBCClienteDAO jdbcCliente = new JDBCClienteDAO(conexao);
			 jdbcCliente.inserir(cliente);
			 conec.fecharConexao();
			 
			 return this.buildResponse("Cliente Cadastrado com Sucesso!");
			 
			 
		 }catch(Exception e){
	       e.printStackTrace();
	       return this.buildErrorResponse(e.getMessage());
		 }
		
	 }
	
		@POST
		@Path("/buscarClientePorNome/{cliente}")
		@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
		public Response buscarClientePorNome(@PathParam("cliente") String cliente) {
			try {
				List<Cliente> clientes = new ArrayList<Cliente>();
			
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCClienteDAO jdbcCliente = new JDBCClienteDAO(conexao);
			
			clientes = jdbcCliente.buscarPorCliente(cliente);
			conec.fecharConexao();
			
			return this.buildResponse(clientes);
			
			}catch(Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		         }
		    }
	 
	 
		@POST
		@Path("/deletarCliente/{id}")
		@Consumes("application/*")
		public Response deletarCliente(@PathParam("id") int id) {
			try {
				Conexao conec = new Conexao();
				Connection conexao = conec.abrirConexao();
				JDBCClienteDAO jdbcCliente = new JDBCClienteDAO(conexao);
				jdbcCliente.deletarCliente(id);
				conec.fecharConexao();
				
				return this.buildResponse("Cliente deletado com Sucesso!");	
				
				}catch(Exception e){
					e.printStackTrace();
					return this.buildErrorResponse(e.getMessage());
				}
			
		}
	 
		@POST
		@Path("/buscarClientePeloId/{id}")
		@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
		
		public Response buscarClientePeloId (@PathParam("id") int id) {
			
			try {
				
				Conexao conec = new Conexao();
				Connection conexao = conec.abrirConexao();
				JDBCClienteDAO jdbcCliente = new JDBCClienteDAO(conexao);
				Cliente cliente = jdbcCliente.buscarPorId(id);
			
				
				
				return this.buildResponse(cliente);
			}catch(Exception e){
				e.printStackTrace();
				return this.buildErrorResponse(e.getMessage());
			}	
			
		}
		
		@POST
		@Path("/editarCliente")
		@Consumes("application/*")
		public Response editarCliente (String cliente) {
			try {
				
				Cliente clienteEdit = new ObjectMapper().readValue(cliente, Cliente.class);
				Conexao conec = new Conexao();
				Connection conexao = conec.abrirConexao();
				JDBCClienteDAO jdbcCliente = new JDBCClienteDAO(conexao);
				jdbcCliente.atualizar(clienteEdit);
				
				conec.fecharConexao();
				
				return this.buildResponse("Cliente Editado com Sucesso!");
			}catch(Exception e){
				e.printStackTrace();
				return this.buildErrorResponse(e.getMessage());
			}
			
			
			
		}
	 
	 
	 
	 
	
}
