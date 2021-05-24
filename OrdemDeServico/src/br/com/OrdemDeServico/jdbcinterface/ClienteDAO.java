package br.com.OrdemDeServico.jdbcinterface;

import java.util.List;

import br.com.OrdemDeServico.cliente.Cliente;
import br.com.OrdemDeServico.usuario.Usuario;


public interface ClienteDAO {
    
	public boolean inserir(Cliente cliente);
	public List<Cliente>buscarPorCliente(String cliente);
	public boolean deletarCliente(int id);
	public Cliente buscarPorId(int cod);
	public boolean atualizar(Cliente cliente);
}
