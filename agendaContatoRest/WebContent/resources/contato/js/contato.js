/*Criação do objeto*/
SENAI.contato = new Object();

$(document)
		.ready(
				function() {

					SENAI.contato.cadastrar = function() {

						var nome = document.getElementById("nome").value;
						var endereco = document.getElementById("endereco").value;
						var telefone = document.getElementById("telefone").value;
						if (nome == "" || endereco == "" || telefone == "") {
							alert("Todos os campos são de preenchimento obrigatório!");
							return false;
						} else {
							var senha = document.getElementById("senha").value;
							var confirmaSenha = document
									.getElementById("confirmarSenha").value;
							if (senha != confirmaSenha) {
								alert("As senhas não conferem!");
								return false;
							}
							if (senha.length < 4 || senha.length > 8) {
								alert("A senha informada possui menos de 04 caracteres!");
								return false;
							}
							var email = document.getElementById("email").value;
							if (email.indexOf("@") == -1 || // valida se existe
							// o @
							email.indexOf(".") == -1 || // valida se existe o .
							email.indexOf("@") == 0 || // valida se tem texto
							// antes do @
							email.lastIndexOf(".") + 1 == email.length || // valida
							// se
							// tem
							// texto
							// depois
							// do
							// ponto
							(email.indexOf("@") + 1 == email.indexOf("."))) { // valida
								// se
								// tem
								// texto
								// entre
								// o @
								// e o
								// .{
								alert("email incorreto");
								document.getElementById("email").focus();
								return false;

							} else {
								/*
								 * Criação do objeto Javascript "novoContato"
								 * que levará os dados informados pelo usuário
								 * para o lado servidor por intermédio de uma
								 * requisiççao Ajax para cadastro no banco de
								 * dados. Esse objeto é Javascript, porém,
								 * depois ele será convertido em um objeto Java,
								 * portanto, é necessário que os atributos dele
								 * contenham os nomes exatamente iguais aos
								 * nomes dos atributos da classe Contato (Java)
								 */
								var novoContato = new Object();

								novoContato.nome = $("#nome").val();
								novoContato.endereco = $("#endereco").val();
								novoContato.telefone = $("#telefone").val();
								novoContato.senha = $("#senha").val();
								novoContato.email = $("#email").val();
								var cfg = {
									/*
									 * Abaixo a url repassada como referência
									 * para o acesso a Rest que contém, dentre
									 * outros, o recurso que tratará de todo o
									 * processo para cadastramento dos dados do
									 * formulário
									 */
									url : "rest/contatoRest/addContato",
									data : novoContato,
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
										SENAI.contato.buscar();
									},
									error : function(err) {
										alert("Erro ao cadastrar um novo contato!"
												+ err.responseText);
									}
								};
								/*
								 * Abaixo a chamada ao método post(), encontrado
								 * no arquivo ajax.js, que ainda não
								 * implementou, mas que, por sua vez, conterá os
								 * scripts responsáveis por enviar as
								 * requisições dos dados de cadastramento,
								 * consulta, exclusão e edição dos dados e
								 * receber a resposta do servidor a essas
								 * requisições. Perceba que ao chamar o método,
								 * enviamos como parâmetro a variável cfg
								 * contendo os dados do formulário, url,
								 * estrutura da caixa de diálogo, mensagem de
								 * erro, dentre outros
								 */
								SENAI.ajax.post(cfg);
							}

						}
						;

						SENAI.contato.buscar = function() {
							var valorBusca = $("#consultarContato").val();
							SENAI.contato.exibirContatos(undefined, valorBusca);
						};

						SENAI.contato.exibirContatos = function(
								listaDeContatos, valorBusca) {
							var html = "<table class='table'>";
							html += "<tr><th>Nome</th><th>Endereço</th><th>Email</th><th>Ações</th></tr>";
							if (listaDeContatos != undefined
									&& listaDeContatos.length > 0) {
								for (var i = 0; i < listaDeContatos.length; i++) {
									html += "<tr><td>"
											+ listaDeContatos[i].nome
											+ "</td>"
											+ "<td>"
											+ listaDeContatos[i].endereco
											+ "</td>"
											+ "<td>"
											+ listaDeContatos[i].email
											+ "</td>"
											+ "<td><a class='link'onclick='SENAI.contato.editarContato("
											+ listaDeContatos[i].id
											+ ")'>"
											+ "<img src='resources/contato/img/edit.png'> </a>"
											+ "<a class='link' onclick='SENAI.contato.deletarContato("
											+ listaDeContatos[i].id
											+ ")'>"
											+ "<img src='resources/contato/img/remove.png'></a></td></tr>";
								}
							} else {
								if (listaDeContatos == undefined) {
									if (valorBusca == "") {
										valorBusca = null;
									}
									/*
									 * Destaque para a url que referencia o
									 * recurso Rest que fará a busca dos
									 * contatos, baseados no valor contido na
									 * variável valorBusca que, por sua vez,
									 * também encontra-se referenciada nesta.
									 */
									var cfg = {
										type : "POST",
										url : "rest/contatoRest/buscarContatosPorNome/"
												+ valorBusca,
										success : function(listaDeContatos) {
											SENAI.contato
													.exibirContatos(listaDeContatos);
										},

										error : function(err) {
											alert("Erro ao consultar os contatos: "
													+ err.responseText);

										}
									};
									SENAI.ajax.post(cfg);
								} else {
									html += "<tr><td colspan='3'>Nenhum registro encontrado</td></tr>";

								}
							}

							html += "</table>";
							$("#resultadoContatos").html(html);
						};// Fecha a declaração do método exibirContatos()

						SENAI.contato.exibirContatos(undefined, "");
						SENAI.contato.deletarContato = function(id) {
							var cfg = {
								type : "POST",
								url : "rest/contatoRest/deletarContato/" + id,
								success : function(data) {
									SENAI.contato.buscar();
								},
								error : function(err) {
									alert("Erro ao deletar o contato: "
											+ err.responseText);
								}

							};
							SENAI.ajax.post(cfg);
						};

						SENAI.contato.editarContato = function(id) {
							var cfg = {
								type : "POST",
								url : "rest/contatoRest/buscarContatoPeloId/"
										+ id,
								success : function(contato) {
									$("#nomeEdit").val(contato.nome);
									$("#enderecoEdit").val(contato.endereco);
									$("#telefoneEdit").val(contato.telefone);
									$("#emailEdit").val(contato.email);
									$("#idContatoEdit").val(contato.id);
									$("#senhaEdit").val(contato.senha);

									SENAI.contato.exibirEdicao(contato);
								},
								error : function(err) {
									alert("Erro ao edita o contato!"
											+ err.responseText);
								}

							};
							SENAI.ajax.post(cfg);
						};

						SENAI.contato.exibirEdicao = function(contato) {
							var cfg = {
								title : "Editar Contato",
								height : 400,
								width : 550,
								modal : true,
								buttons : {
									"Salvar" : function() {
										var dialog = this;
										var contato = new Object();
										contato.id = $("#idContatoEdit").val();
										contato.nome = $("#nomeEdit").val();
										contato.endereco = $("#enderecoEdit")
												.val();
										contato.telefone = $("#telefoneEdit")
												.val();
										contato.email = $("#emailEdit").val();
										contato.senha = $("#senhaEdit").val();
										var cfg = {
											type : "POST",

											url : "rest/contatoRest/editarContato",
											data : contato,
											success : function() {
												$(dialog).dialog("close");
												SENAI.contato.buscar();
											},
											error : function(err) {
												alert("Erro ao editar o contato "
														+ err.responseText);
											}
										};
										SENAI.ajax.post(cfg);
									},
									"Cancelar" : function() {
										$(this).dialog("close");
									}
								},
								close : function() {
									// caso o usuário simplesmente feche a caixa
									// de edição
									// não deve acontecer nada
								}
							};
							$("#editarContato").dialog(cfg);
						};

					};

				});
