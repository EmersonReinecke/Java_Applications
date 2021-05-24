package br.com.OrdemDeServico.rest;

import java.security.MessageDigest;
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
import javax.xml.bind.DatatypeConverter;

import org.codehaus.jackson.map.ObjectMapper;

import com.google.gson.Gson;

import br.com.OrdemDeServico.bd.conexao.Conexao;
import br.com.OrdemDeServico.jdbc.JDBCUsuarioDAO;
import br.com.OrdemDeServico.jdbcinterface.UsuarioDAO;
import br.com.OrdemDeServico.usuario.Usuario;

@Path("CadastroUsuario")
public class CadastroUsuarioRest extends UtilRest {

	public CadastroUsuarioRest() {
	}

	@POST
	@Path("/addUsuario")
	@Consumes("application/json")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addUsuario(String novoUsuario) {
		try {

		
			Usuario usuario = new ObjectMapper().readValue(novoUsuario,Usuario.class);
		
			
			MessageDigest senhamd5 = MessageDigest.getInstance("MD5");
			senhamd5.update(usuario.getSenha().getBytes());
			byte[] digest = senhamd5.digest();

			String myHash = DatatypeConverter.printHexBinary(digest).toUpperCase();
			
			Gson gson = new Gson();

			
			usuario.setSenha(myHash);
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCUsuarioDAO jdbcUsuario = new JDBCUsuarioDAO(conexao);
			
			jdbcUsuario.inserir(usuario);
			conec.fecharConexao();

			return this.buildResponse("Usuario Cadastrado com Sucesso!");
               
		} catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}

	}
	
	
	@POST
	@Path("/buscarUsuarioPorNome/{nome}")
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Response buscarUsuarioPorNome(@PathParam("nome") String nome) {
		try {
			List<Usuario> usuarios = new ArrayList<Usuario>();
		
		Conexao conec = new Conexao();
		Connection conexao = conec.abrirConexao();
		JDBCUsuarioDAO jdbcUsuario = new JDBCUsuarioDAO(conexao);
		
		usuarios = jdbcUsuario.buscarPorNome(nome);
		conec.fecharConexao();
		
		return this.buildResponse(usuarios);
		 
		} catch(Exception e) {
		e.printStackTrace();
		return this.buildErrorResponse(e.getMessage());
	         }
	    }
	
	
	@POST
	@Path("/deletarUsuario/{id}")
	@Consumes("application/*")
	public Response deletarUsuario(@PathParam("id") int id) {
		try {
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCUsuarioDAO jdbcUsuario = new JDBCUsuarioDAO(conexao);
			jdbcUsuario.deletarUsuario(id);
			conec.fecharConexao();
			
			return this.buildResponse("Usuario deletado com sucesso");	
			
			}catch(Exception e){
				e.printStackTrace();
				return this.buildErrorResponse(e.getMessage());
			}
		
	}
	
	
	
	@POST
	@Path("/buscarUsuarioPeloId/{id}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	
	public Response buscarUsuarioPeloId (@PathParam("id") int id) {
		
		try {
			
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCUsuarioDAO jdbcUsuario = new JDBCUsuarioDAO(conexao);
			Usuario usuario = jdbcUsuario.buscarPorId(id);
		
			
			
			return this.buildResponse(usuario);
		}catch(Exception e){
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}	
		
	}
	
	@POST
	@Path("/editarUsuario")
	@Consumes("application/*")
	public Response editarUsuario (String usuario) {
		try {
			
			Usuario usuarioEdit = new ObjectMapper().readValue(usuario, Usuario.class);
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCUsuarioDAO jdbcUsuario = new JDBCUsuarioDAO(conexao);
			jdbcUsuario.atualizar(usuarioEdit);
			
			conec.fecharConexao();
			
			return this.buildResponse("Usuario Editado com Sucesso!");
		}catch(Exception e){
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
		
		
		
	}
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	}
