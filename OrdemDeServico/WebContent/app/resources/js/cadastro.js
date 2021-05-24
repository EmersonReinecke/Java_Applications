/** Ordem de Serviço */

SENAI.cadastro = new Object();

// function buscar(){
// };

function CadastroUsuario() {

	var nome = document.getElementById("nome").value;
	if (nome == "") {
		alert("O campo nome precisa estar preenchido!")
		return false;
	}
	var senha = document.getElementById("senha").value;
	var confirmaSenha = document.getElementById("confirmaSenha").value;
	if (senha != confirmaSenha) {
		console.log(senha, confirmaSenha)
		alert("As senhas nao conferem!");
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
	var telefone = document.getElementById("telefone").value;
	if (telefone == "") {
		alert("O campo telefone precisa estar preenchido!")
		return false;
	
	

	} else {

		var senhaCodificada = btoa(confirmaSenha);

		var novoUsuario = new Object();

		novoUsuario.nome = $("#nome").val();
		novoUsuario.senha = senhaCodificada;
		novoUsuario.email = $("#email").val();
		novoUsuario.telefone = $("#telefone").val();

		$.ajax({
			url : "../../rest/CadastroUsuario/addUsuario",
			type : "POST",
			data : JSON.stringify(novoUsuario),
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

				alert("Usuario cadastrado com Sucesso!")

				cadastro.buscar();

			},
			error : function(err) {
				alert("Erro ao cadastrar um novo Usuario!" + err.responseText);
			}
		});
	}
}

SENAI.cadastro.buscar = function() {

	var valorBusca = $("#consultarUsuario").val();

	SENAI.cadastro.exibirUsuarios(undefined, valorBusca);

};

SENAI.cadastro.exibirUsuarios = function(listaDeUsuarios, valorBusca) {

	var html = "<table class='table'>";
	html += "<tr><th>Nome</th><th>E-mail</th><th>Telefone</th><th>Acoes</th></tr>";

	if (listaDeUsuarios != undefined && listaDeUsuarios.length > 0) {

		for (var i = 0; i < listaDeUsuarios.length; i++) {
			html += "<tr><td>"
					+ listaDeUsuarios[i].nome
					+ "</td>"
					+ "<td>"
					+ listaDeUsuarios[i].email
					+ "</td>"
					+ "<td>"
					+ listaDeUsuarios[i].telefone
					+ "</td>"
					+ "<td><a class='link' onclick='SENAI.cadastro.editarUsuario("
					+ listaDeUsuarios[i].id
					+ ")'>"
					+ " <img src='../resources/css/CadastroUsuario/img/edit.png'></a>"
				//	+ "<a class='link' onclick='SENAI.cadastro.deletarUsuario("+ listaDeUsuarios[i].id+ ")'>"
					
					+ `<a class="link" onclick="SENAI.cadastro.deletarUsuario(${listaDeUsuarios[i].id}, '${listaDeUsuarios[i].nome}')">`
					+ " <img src='../resources/css/CadastroUsuario/img/remove.png'></a></td></tr>";
		}
	} else {
		if (listaDeUsuarios == undefined) {
			if (valorBusca == "") {
				valorBusca = null;
			}
			var cfg = {
				type : "POST",
				url : "../../rest/CadastroUsuario/buscarUsuarioPorNome/"
						+ valorBusca,
				success : function(listaDeUsuarios) {

					SENAI.cadastro.exibirUsuarios(listaDeUsuarios);

				},

				error : function(err) {
					alert("Erro ao consultar usuarios:" + err.responseText);
				}

			};
			SENAI.ajax.post(cfg);

		} else {
			html += "<tr><td colspan='3'>Nenhum usuario encontrado</td></tr>";
		}
	}
	html += "</table>";
	$("#resultadoUsuarios").html(html);

};

SENAI.cadastro.exibirUsuarios(undefined, "");

    SENAI.cadastro.deletarUsuario = function(id,nome) {
    	
    	if (confirm("Deseja realmente excluir o usuario "+nome+"?")==false) {
  		  alert("Exclusao cancelada!"); 

    	}else{
	var cfg = {
		type : "POST",
		url : "../../rest/CadastroUsuario/deletarUsuario/"+id,
		success : function(data) {
		SENAI.cadastro.buscar();
        alert("Usuario deletado com Sucesso!")
          },

		error : function(err) {
         	alert("Erro ao deletar o usuario: "+err.responseText);

		}
	};
	SENAI.ajax.post(cfg);
    	}
	// Fazer mensagem ao deletar usuario.

};

SENAI.cadastro.editarUsuario = function(id) {

	var cfg = {
		type : "POST",
		url : "../../rest/CadastroUsuario/buscarUsuarioPeloId/" + id,
		success : function(usuario) {
			// console.log(usuario)
			$("#idUsuarioEdit").val(usuario.id)
			$("#nomeEdit").val(usuario.nome);
			$("#emailEdit").val(usuario.email);
			$("#telefoneEdit").val(usuario.telefone);

			SENAI.cadastro.exibirEdicao(usuario);

		},
		error : function(err) {
			alert("Erro ao editar Usuario!" + err.responseText);
		}

	};

	SENAI.ajax.post(cfg);

};

SENAI.cadastro.exibirEdicao = function(usuario) {

	var cfg = {
		title : "Editar Usuario",
		height : 400,
		width : 500,
		modal : true,

		buttons : {
			"Salvar" : function() {
				var dialog = this;
				var usuario = new Object();
				usuario.id = $("#idUsuarioEdit").val();
				usuario.nome = $("#nomeEdit").val();
				usuario.email = $("#emailEdit").val();
				usuario.telefone = $("#telefoneEdit").val();
				console.log(usuario)

				var cfg = {
					type : "POST",
					url : "../../rest/CadastroUsuario/editarUsuario",
					data : JSON.stringify(usuario),
					contentType : 'application/json',
					dataType : 'json',
					success : function() {
						$(dialog).dialog("close");

						SENAI.cadastro.buscar();
						alert("Usuario Editado com Sucesso!")
					},

					error : function(err) {
						alert("Erro ao editar Usuario!" + err.responseText);
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

	$("#editarUsuario").dialog(cfg);

};
