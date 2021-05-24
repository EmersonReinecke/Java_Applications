package br.com.agendaContatoRest.jdbcinterface;

import java.util.List;

import br.com.agendaContatoRest.objetos.Contato;
	
	
public interface ContatoDAO {
	
		public boolean inserir (Contato contato);
	    public List<Contato> buscarPorNome(String nome);	
	    public boolean deletarContato(int id);
	    public Contato buscarPorId(int cod);
	    public boolean atualizar(Contato contato);
		
		
	}


  