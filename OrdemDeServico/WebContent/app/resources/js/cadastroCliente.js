/**
 * Ordem de Serviço
 * 
 */

SENAI.cadastroCliente = new Object();

function CadastroCliente() {

	var cliente = document.getElementById("cliente").value;
	if (cliente == "") {
		alert("O campo do Cliente precisa estar preenchido!")
		return false;
	}
	var telefone = document.getElementById("telefone").value;
	if (telefone == "") {
		alert("O campo telefone precisa estar preenchido!")
		return false;
	}
	var email = document.getElementById("email").value;
	if (email.indexOf("@") == -1 || email.indexOf(".") == -1
			|| email.indexOf("@") == 0
			|| email.lastIndexOf(".") + 1 == email.length
			|| (email.indexOf("@") + 1 == email.indexOf("."))) {
		alert("E-mail esta incorreto!");
		document.getElementById("email").focus();
		return false;
	}
	var cpfcnpj = document.getElementById("cpfcnpj").value;
	if (cpfcnpj == "") {
		alert("O campo Cpf/CNPJ precisa estar preenchido!")
		return false;
	}
	var endereco = document.getElementById("endereco").value;
	if (endereco == "") {
		alert("O campo endereco precisa estar preenchido!")
		return false;
	
	

	} else {

		var novoCliente = new Object();

		novoCliente.cliente = $("#cliente").val();
		novoCliente.telefone = $("#telefone").val();
		novoCliente.email = $("#email").val();
		novoCliente.cpfcnpj = $("#cpfcnpj").val();
		novoCliente.endereco = $("#endereco").val();

		$.ajax({
			url : "../../rest/CadastroCliente/addCliente",
			type : "POST",
			data : JSON.stringify(novoCliente),
			contentType : 'application/json',
			dataType : 'json',

			success : function(msg) {

				var cfg = {
					title : "Mensagem",
					height : 250,
					width : 400,
					modal : true,
					buttons : {
						"OK" : function() {
							$(this).dialog("close");
						}
					}
				};
				$("#msg").html(msg);
				$("#msg").dialog(cfg);

				alert("Cliente cadastrado com sucesso!");
				cadastroCliente.buscar();
				
				
			},
			error : function(err) {
				alert("Erro ao cadastrar cliente!" + err.responseText);
			}
		});
	}
}

SENAI.cadastroCliente.buscar = function() {

	var valorBusca = $("#consultarCliente").val();

	SENAI.cadastroCliente.exibirClientes(undefined, valorBusca);
};

SENAI.cadastroCliente.exibirClientes = function(listaDeClientes, valorBusca) {

	var html = "<table class='table'>";
	html += "<tr><th>Cliente</th><th>Telefone</th><th>E-mail</th><th>CPF/CNPJ</th><th>Endereço</th><th>Acoes</th></tr>";

	if (listaDeClientes != undefined && listaDeClientes.length > 0) {

		for (var i = 0; i < listaDeClientes.length; i++) {
			html += "<tr><td>"
					+ listaDeClientes[i].cliente
					+ "</td>"
					+ "<td>"
					+ listaDeClientes[i].telefone
					+ "</td>"
					+ "<td>"
					+ listaDeClientes[i].email
					+ "</td>"
					+ "<td>"
					+ listaDeClientes[i].cpfcnpj
					+ "</td>"
					+ "<td>"
					+ listaDeClientes[i].endereco
					+ "</td>"
					+ "<td><a class='link' onclick='SENAI.cadastroCliente.editarCliente("+listaDeClientes[i].id+")'>"
					+ " <img src='../resources/css/CadastroCliente/img/edit.png'></a>"					
					+ `<a class="link" onclick="SENAI.cadastroCliente.deletarCliente(${listaDeClientes[i].id}, '${listaDeClientes[i].cliente}')">` 
			/*+ "<a class='link' onclick='SENAI.cadastroCliente.deletarCliente("+listaDeClientes[i].id+", '"+listaDeClientes[i].cliente+"')'>"*/
					+ " <img src='../resources/css/CadastroCliente/img/remove.png'></a></td></tr>";
		
		}
	} else {
		if (listaDeClientes == undefined) {
			if (valorBusca == "") {
				valorBusca = null;
			}
			var cfg = {
				type : "POST",
				url : "../../rest/CadastroCliente/buscarClientePorNome/"+valorBusca,
				success : function(listaDeClientes) {
					SENAI.cadastroCliente.exibirClientes(listaDeClientes);
				},
                    error : function(err) {
					alert("Erro ao consultar cliente:" + err.responseText);
				}

			};
			
			SENAI.ajax.post(cfg);

		} else {
			html += "<tr><td colspan='3'>Nenhum cliente encontrado</td></tr>";
		}
	}
	html += "</table>";
	$("#resultadoClientes").html(html);

};

     SENAI.cadastroCliente.exibirClientes(undefined, "");


  SENAI.cadastroCliente.deletarCliente = function(id,cliente) {
	  
	  if (confirm("Deseja realmente excluir o cliente "+cliente+"?")==false) {
		  alert("Exclusao cancelada!"); 
	  }else{
	    var cfg = {
		type : "POST",
		url : "../../rest/CadastroCliente/deletarCliente/"+id,
		success : function(data) {

			SENAI.cadastroCliente.buscar();
			
			

		},
		error : function(err) {
			alert("Erro ao deletar o Cliente: " + err.responseText);

		}
	};
	SENAI.ajax.post(cfg);

	
	}

};


SENAI.cadastroCliente.editarCliente = function(id) {

	var cfg = {
		type : "POST",
		url: "../../rest/CadastroCliente/buscarClientePeloId/" + id,
		success : function(cliente) {
			//console.log(cliente)
			$("#idClienteEdit").val(cliente.id)
			$("#clienteEdit").val(cliente.cliente);
			$("#emailEdit").val(cliente.email);
			$("#telefoneEdit").val(cliente.telefone);
			$("#enderecoEdit").val(cliente.endereco);
			$("#cpfcnpjEdit").val(cliente.cpfcnpj);

			SENAI.cadastroCliente.exibirEdicao(cliente);

		},
		error : function(err) {
			alert("Erro ao editar Cliente!" + err.responseText);
		}

	};

	SENAI.ajax.post(cfg);

};

SENAI.cadastroCliente.exibirEdicao = function(cliente) {

	var cfg = {
		title : "Editar Cliente",
		height : 400,
		width : 500,
		modal : true,

		buttons : {
			"Salvar" : function() {
				var dialog = this;
				var cliente = new Object();
				cliente.id = $("#idClienteEdit").val();
				cliente.cliente = $("#clienteEdit").val();
				cliente.email = $("#emailEdit").val();
				cliente.telefone = $("#telefoneEdit").val();
				cliente.endereco = $("#enderecoEdit").val();
				cliente.cpfcnpj = $("#cpfcnpjEdit").val();
			

				var cfg = {
					type : "POST",
					url : "../../rest/CadastroCliente/editarCliente",
					data : JSON.stringify(cliente),
					contentType : 'application/json',
					dataType : 'json',
					success : function() {
						$(dialog).dialog("close");

						SENAI.cadastroCliente.buscar();
						
						alert("Cliente Editado com Sucesso!")

					},

					error : function(err) {
						alert("Erro ao editar Cliente!" + err.responseText);
					}

				};
				SENAI.ajax.post(cfg);
			},
			"Cancelar" : function() {
				$(this).dialog("close");
			}
		},
		close : function() {

		}

	};

	$("#editarCliente").dialog(cfg);

};











