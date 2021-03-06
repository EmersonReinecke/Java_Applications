package br.com.agendaContatoRest.rest;

import java.io.StringWriter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import org.codehaus.jackson.map.ObjectMapper;

public class UtilRest {

	/*
	 * Abaixo o m?todo respons?vel por enviar a resposta ao cliente sobre a
	 * transa??o realizada inclus?o, consulta, edi??o ou exclus?o,realizadas com
	 * sucesso. Repare que o metodo em quest?o aguarda que seja repassado um
	 * conteudo que ser? referenciado por um objeto chamado result.
	 */
	public Response buildResponse(Object result) {
		/*
		 * Cria a instancia da Classe StringWriter para o objeto fw. Isto que
		 * este objeto ? quem estar? referenciando o conte?do repassado como
		 * resposta para o lado cliente.
		 */

		StringWriter fw = new StringWriter();
		try {
			/*
			 * Cria a instacia da classe ObjectMapper para o objeto mapper.
			 */
			ObjectMapper mapper = new ObjectMapper();
			/*
			 * Acessa o metodo writeValue, por meio do objeto mapper passando
			 * como parametro o objeto fw e o conteudo do objeto result, na
			 * realidade est? criando um mapeamento de dados onde o objeto fw ?
			 * a chave do valor de um conte?do referenciado pelo objeto result.
			 * result pode conter a mensagem,"Cadastro efetuado com sucesso",ou
			 * "Exclus?o efetuada com sucesso" ou outra qualquer dependendo da
			 * transa??o realizada.
			 */

			mapper.writeValue(fw, result);
			/*
			 * Monta o objeto de resposta com status 200 (ok), junto com o
			 * objeto result convertido para JSON pelo objeto fw para o cliente
			 * no formato String.
			 */
			return Response.ok(fw.toString()).build();

		} catch (Exception ex) {
			return this.buildErrorResponse(ex.getMessage());

		}

	}

	/*
	 * Abaixo o m?todo respons?vel por enviar a resposta ao cliente sobre A
	 * transa??o realizada, inclus?o, consulta, edi??o ou exclus?o.ao cliente,
	 * n?o realizadas com sucesso, ou seja, que contenha algum erro. Repare que
	 * o m?todo em quest?o aguarda que seja repassado um conte?do que ser?
	 * referenciado pelo por um objeto chamado rb.
	 */
	public Response buildErrorResponse(String str) {
		/*
		 * Abaixo o objeto rb recebe o status do erro.
		 */

		ResponseBuilder rb =Response.status(Response.Status.INTERNAL_SERVER_ERROR);

		/*
		 * Define a entidade (objeto), que nesse caso ? uma
		 * 
		 * mensagem que ser? retornado para o cliente.
		 */
		rb = rb.entity(str);
		/*
		 * Define o tipo de retorno desta entidade(objeto), no
		 * 
		 * caso ? definido como texto simples.
		 */
		rb = rb.type("text/plain");
		/*
		 * Retorna o objeto de resposta com status 500 (erro), junto com a
		 * String contendo a mensagem de erro.
		 */
		return rb.build();
	}

}
