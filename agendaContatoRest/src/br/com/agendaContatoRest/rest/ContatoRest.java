package br.com.agendaContatoRest.rest;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Response;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.codehaus.jackson.map.ObjectMapper;
import br.com.agendaContatoRest.bd.conexao.Conexao;
import br.com.agendaContatoRest.jdbc.JDBCContatoDAO;
import br.com.agendaContatoRest.objetos.Contato;

@Path("contatoRest")//Caminho URI da classe Rest utilizada.

public class ContatoRest extends UtilRest {

	public ContatoRest(){
	}
	   	
	   @POST//Processar� as requisi��es enviadas pelo m�todo post.
	   @Path("/addContato")/*Caminho URI do m�todo da classe que
	   * receber� os dados do formul�rio que
	   * os armazenar� em sua respectiva classe
	   * e os incluir� no banco de dados.
	   */
	   @Consumes("application/*")/*Caminho URI que identifica o tipo de
	   * m�dia enviado pelo lado cliente, no
	   * caso, informa��es do formul�rio no
	  * formato de aplica��o.
	   */
    	public Response addContato(String contatoParam) {
	      try {
      	/*
	     * Instancia a classe ObjectMapper e chama o m�todo readValue()
	     * leitura dos valores repassados pelo cliente no formato JSON,
	     * no caso os campos do formul�rio e atribui os valores destes
	     * campos aos atributos da classe Contato.
	     * Os valores obtidos do formulario s�o armazenos em atributos
	     * javascript,esses atributos por sua vez devem obritoriamente

	     * ter o mesmo nome dos atributos da classe Java correspondente
	     * Com isso � possivel a realiza��o de um "de-para" dos valores
	     * contidos no objeto JSON (contatoParam) para um objeto da
	     * classe Contato.
	     * Aqui, importante observa��o de que o nome dos atributos
	     * declarados na classe, que ir�o receber os valores dos campos
	     * do formul�rio, sejam declarados de maneira identica ao nome
	     * dos campos do formul�rio que enviar� seus valores.
	     */
	       Contato contato = new ObjectMapper().readValue(contatoParam,Contato.class);
	//Chamar o m�todo que grava o objeto contato no banco de dados

      	Conexao conec = new Conexao();
	    Connection conexao = conec.abrirConexao();
	    JDBCContatoDAO jdbcContato = new JDBCContatoDAO(conexao);
	    jdbcContato.inserir(contato);
	    conec.fecharConexao();

	   /*
	    * Envia como retorno para o m�todo buildResponse() a mensagem
	    * �Contato cadastrado com sucesso�, no caso de sucesso da
	    * inclus�o.
	    * Tamb�m retorna para o m�todo buildErrorResponse() uma mensagem
	    * interna de erro, no caso da erro ocorrido durante a inclus�o.
	    */
	   return this.buildResponse("Contato cadastrado com sucesso.");
	    } catch (Exception e) {
	    e.printStackTrace();
	    return this.buildErrorResponse(e.getMessage());
	    }
	   }
	 
	  //Processa as requisi��es do metodo Post.
	  @POST
	 // Caminho URL que fara a busca do contato pelo nome.
	  @Path("/buscarContatosPorNome/{nome}")
	 // Tipo de media que sera recebido que sera em formato JSON.
	  @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	  public Response buscarContatosPorNome(@PathParam("nome") String nome){
		  try{
			  //Esta sendo criado o arraylist que recebera os contatos.
			  List<Contato> contatos = new ArrayList<Contato>();
			  
			  Conexao conec = new Conexao();
			  Connection conexao = conec.abrirConexao();
			  JDBCContatoDAO jdbcContato = new JDBCContatoDAO(conexao);
			  contatos = jdbcContato.buscarPorNome(nome);
			  conec.fecharConexao();
			  // Enviara para o buildResponse os contatos cadastrados no Banco de dados
			  return this.buildResponse(contatos);
		  }catch (Exception e){
			  e.printStackTrace();
			  //Retornara uma mensagem com erro caso de algo de errado.
			  return this.buildErrorResponse(e.getMessage());
		  }
	  }
	  
	   @POST
	   @Path("/deletarContato/{id}")
	   @Consumes("application/*")
	   
	   public Response deletarContato(@PathParam("id") int id){
		   try{
			   Conexao conec = new Conexao();
			   Connection conexao = conec.abrirConexao();
			   JDBCContatoDAO jdbcContato = new JDBCContatoDAO(conexao);
			   jdbcContato.deletarContato(id);
			   conec.fecharConexao();
			   
			   return this.buildResponse("Contato deletado com sucesso.");
		   } catch (Exception e){
			   e.printStackTrace();
			   return this.buildErrorResponse(e.getMessage());
			   
		   }
	   }
	   
	   @POST
	   @Path ("buscarContatoPeloId/{id}")
	   @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	   
	   public Response buscarContatoPeloId(@PathParam("id") int id){
		   try{
			   Conexao conec = new Conexao();
			   Connection conexao = conec.abrirConexao();
			   
			   JDBCContatoDAO jdbcContato = new JDBCContatoDAO(conexao);
			   Contato contato = jdbcContato.buscarPorId(id);
			    
			   return this.buildResponse(contato);
		   }catch (Exception e){
			   e.printStackTrace();
			   return this.buildErrorResponse(e.getMessage());
			   
		   }
	   }
	   
	   @POST 
	   @Path("/editarContato")
	   @Consumes("application/*")
	   public Response editarContato (String contatoParam){
		   try{
			   Contato contato = new ObjectMapper().readValue(contatoParam, Contato.class);
			   Conexao conec = new Conexao();
			   Connection conexao = conec.abrirConexao();
			   JDBCContatoDAO jdbcContato=new JDBCContatoDAO(conexao);
			   jdbcContato.atualizar(contato);
			   conec.fecharConexao();
			   
			   return this.buildResponse("Contato editado com sucesso");
		   }catch (Exception e){
			   e.printStackTrace();
			   return this.buildErrorResponse(e.getMessage());
		   }
	   }
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	}

