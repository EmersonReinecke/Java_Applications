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
	   	
	   @POST//Processará as requisições enviadas pelo método post.
	   @Path("/addContato")/*Caminho URI do método da classe que
	   * receberá os dados do formulário que
	   * os armazenará em sua respectiva classe
	   * e os incluirá no banco de dados.
	   */
	   @Consumes("application/*")/*Caminho URI que identifica o tipo de
	   * mídia enviado pelo lado cliente, no
	   * caso, informações do formulário no
	  * formato de aplicação.
	   */
    	public Response addContato(String contatoParam) {
	      try {
      	/*
	     * Instancia a classe ObjectMapper e chama o método readValue()
	     * leitura dos valores repassados pelo cliente no formato JSON,
	     * no caso os campos do formulário e atribui os valores destes
	     * campos aos atributos da classe Contato.
	     * Os valores obtidos do formulario são armazenos em atributos
	     * javascript,esses atributos por sua vez devem obritoriamente

	     * ter o mesmo nome dos atributos da classe Java correspondente
	     * Com isso é possivel a realização de um "de-para" dos valores
	     * contidos no objeto JSON (contatoParam) para um objeto da
	     * classe Contato.
	     * Aqui, importante observação de que o nome dos atributos
	     * declarados na classe, que irão receber os valores dos campos
	     * do formulário, sejam declarados de maneira identica ao nome
	     * dos campos do formulário que enviará seus valores.
	     */
	       Contato contato = new ObjectMapper().readValue(contatoParam,Contato.class);
	//Chamar o método que grava o objeto contato no banco de dados

      	Conexao conec = new Conexao();
	    Connection conexao = conec.abrirConexao();
	    JDBCContatoDAO jdbcContato = new JDBCContatoDAO(conexao);
	    jdbcContato.inserir(contato);
	    conec.fecharConexao();

	   /*
	    * Envia como retorno para o método buildResponse() a mensagem
	    * “Contato cadastrado com sucesso”, no caso de sucesso da
	    * inclusão.
	    * Também retorna para o método buildErrorResponse() uma mensagem
	    * interna de erro, no caso da erro ocorrido durante a inclusão.
	    */
	   return this.buildResponse("Contato cadastrado com sucesso.");
	    } catch (Exception e) {
	    e.printStackTrace();
	    return this.buildErrorResponse(e.getMessage());
	    }
	   }
	 
	  //Processa as requisições do metodo Post.
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

