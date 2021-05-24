package br.com.OrdemDeServico.jdbcinterface;

import java.util.List;

import br.com.OrdemDeServico.usuario.Usuario;

public interface UsuarioDAO {

	
	public boolean inserir(Usuario usuario);
	public List<Usuario>buscarPorNome(String nome);
	public boolean deletarUsuario(int id);
	public Usuario buscarPorId(int cod);
	public boolean atualizar(Usuario usuario);
	
}
