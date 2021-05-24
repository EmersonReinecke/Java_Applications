SENAI.ordemServico = new Object();

// Auto complete campo Cliente!!
$(document)
		.ready(
				function() {
					$('#cliente')
							.autocomplete(
									{
										source : function(request, response) {
											console.log(request.term)
											$
													.ajax({
														url : "../../rest/OrdemServico/consultaClientesNome/"
																+ request.term,
														dataType : "json",
														type : "POST",
														success : function(data) {
															var transformed = $
																	.map(
																			data,
																			function(
																					el) {
																				return {
																					label : el.cliente,
																					id : el.id,

																				};

																			});
															response(transformed);
														},
														error : function(error) {
															console.log(error);
															response([]);
														}

													});
										},
										select : function(event, ui) {
											$("#cliente").val(ui.item.label);
											$("#idcliente").val(ui.item.id);

											return false;
										},
									});
				});

function CriarOrdemServico() {

	var idcliente = document.getElementById("idcliente").value;
	var cliente = document.getElementById("cliente").value;
	var dataEntrada = document.getElementById("dataEntrada").value;
	var dataEntrega = document.getElementById("dataEntrega").value;
	var equipamento = document.getElementById("equipamento").value;
	var valorServico = document.getElementById("valorServico").value;
	var descricaoOrdemServico = document
			.getElementById("descricaoOrdemServico").value;

	if (cliente == "" || dataEntrada == "" || dataEntrega == ""
			|| equipamento == "" || valorServico == ""
			|| descricaoOrdemServico == "") {
		alert("Todos os campos precisam estar preenchidos!")
		return false;

	} else {

		var novaOrdem = new Object();

		novaOrdem.idcliente = $("#idcliente").val();
		novaOrdem.cliente = $("#cliente").val();
		novaOrdem.dataEntrada = $("#dataEntrada").val();
		novaOrdem.dataEntrega = $("#dataEntrega").val();
		novaOrdem.equipamento = $("#equipamento").val();
		novaOrdem.valorServico = $("#valorServico").val();
		novaOrdem.descricaoOrdemServico = $("#descricaoOrdemServico").val();

		if (cliente == "" || dataEntrada == "" || dataEntrega == ""
				|| equipamento == "" || valorServico == ""
				|| descricaoOrdemServico == "") {
			alert("Todos os campos precisam estar preenchidos!")
			return false;

		} else {

			$.ajax({
				url : "../../rest/OrdemServico/addOrdemServico",
				type : "POST",
				data : JSON.stringify(novaOrdem),
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

					alert("OS criada com Sucesso!")
					ordemServico.buscar();

				},

				error : function(err) {
					alert("Erro ao criar a Ordem de Serviço!"
							+ err.responseText);
				}

			});
		}

	}// End AddOS
}

function buscarOrdem() {

	var valoresBusca = {
		dataEntrega : $("#dataEntrega").val(),
		dataEntrada : $("#dataEntrada").val()
	};

	SENAI.ordemServico.exibirOrdensServico(undefined, valoresBusca);

};

SENAI.ordemServico.exibirOrdensServico = function(listadeOrdens, valoresBusca) {

	var html = "<table class='table'>";
	html += "<tr><th>Cliente</th><th>Data de Entrada</th><th>Data de Entrega</th><th>Valor</th><th>Exibir</th></tr>";
	if (listadeOrdens != undefined && listadeOrdens.length > 0) {
		for (var i = 0; i < listadeOrdens.length; i++) {
			html += "<tr><td>"
					+ listadeOrdens[i].cliente
					+ "</td>"
					+ "<td>"
					+ listadeOrdens[i].dataEntrada
					+ "</td>"
					+ "<td>"
					+ listadeOrdens[i].dataEntrega
					+ "</td>"
					+ "<td>"
					+ listadeOrdens[i].valorServico
					+ "</td>"
					+ "<td><a class='link' title='Detalhes da OS' onclick='consultarOrdem("
					+ listadeOrdens[i].id
					+ ")'>"
					+ " <img src='../resources/css/OrdemServico/img/view.png'></a>";

		}

	} else {
		if (listadeOrdens == undefined) {
			if (valoresBusca.dataEntrada == "") {
				valoresBusca.dataEntrada = null;
				
			}
			var cfg = {
				type : "POST",
				url : `../../rest/OrdemServico/buscarOrdemServicoData/${valoresBusca.dataEntrada},${valoresBusca.dataEntrega}`,
				success : function(listadeOrdens) {
					
					SENAI.ordemServico.exibirOrdensServico(listadeOrdens);
				},
				error : function(err) {
					alert("Obrigatorio preencher todos os campos!");
				}

			};

			SENAI.ajax.post(cfg);

		} else {

		}
	}
	html += "</table>";

	$("#ResultadoOrdensServico").html(html);

};

 SENAI.ordemServico.exibirOrdensServico(undefined, "");     

 function consultarOrdem (id) {

	var cfg = {
		type : "GET",
		url : "../../rest/OrdemServico/buscarOrdemServicoId/" + id,
		success : function(data) {
		
				var ordemdeServico = data;
			$("#id").val(ordemdeServico.id)
			$("#cliente").val(ordemdeServico.cliente);
			$("#dataEntrada").val(ordemdeServico.dataEntrada);
			$("#dataEntrega").val(ordemdeServico.dataEntrega);
			$("#equipamento").val(ordemdeServico.equipamento);
			$("#valorServico").val(ordemdeServico.valorServico);
			$("#descricaoOrdemServico").val(ordemdeServico.descricaoOrdemServico); 
			
			SENAI.ordemServico.exibirModalOrdem(ordemdeServico);

		},
		error : function(err) {
			alert("Erro ao consultar!" + err.responseText);
		}

	};

	SENAI.ajax.post(cfg);

};

SENAI.ordemServico.exibirModalOrdem = function(ordemdeServico) {


	
	        $("#id").val(ordemdeServico.id)
	        $("#cliente").val(ordemdeServico.cliente)	        
			$("#dataEntradaOrdem").val(ordemdeServico.dataEntrada);
			$("#dataEntregaOrdem").val(ordemdeServico.dataEntrega);	
			$("#equipamento").val(ordemdeServico.equipamento);
			$("#valorServico").val(ordemdeServico.valorServico);
			$("#descricaoOrdemServico").val(ordemdeServico.descricaoOrdemServico);
			

	var cfg = {

		title : "Ordem de Servico",
		height : 550,
		width : 675,
		modal : true,
		buttons : {
			"OK" : function() {
				var dialog = this;
				
				var ordens = new Object();
				ordens.id= $("#id").val(ordens.id).val();
				ordens.cliente= $("#cliente").val(ordens.cliente).val();
			    ordens.dataEntrada=$("#dataEntradaOrdem").val(ordens.dataEntrada).val();
			    ordens.dataEntrega=$("#dataEntregaOrdem").val(ordens.dataEntrega).val();
				ordens.equipamento= $("#equipamento").val(ordens.equipamento).val();
				ordens.valorServico= $("#valorServico").val(ordens.valorServico).val();
				ordens.descricaoOrdemServico= $("#descricaoOrdemServico").val(ordens.descricaoOrdemServico).val();
					
				
				
				$(this).dialog("close");

			},
			

		},
				}
	$("#exibirOrdem").dialog(cfg);
	};
