package br.com.OrdemDeServico.jdbcinterface;

import java.util.List;


import br.com.OrdemDeServico.ordemServico.OrdemdeServico;

public interface OrdemServico {

	
	public List<OrdemdeServico>buscarOrdemServicoData(String dataEntrada, String dataEntrega);
}
