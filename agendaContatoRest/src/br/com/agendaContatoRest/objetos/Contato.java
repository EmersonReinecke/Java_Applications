package br.com.agendaContatoRest.objetos;

import java.io.Serializable;


public class Contato implements Serializable {

	private static final long serialVersionUID=1L;
	/*
	 * Declarar o nome dos atributos de maneira identica ao nome dos campos
	 * do formulario que enviarao os dados para serem armazenados nestes.
	 */
	private int id;
	private String nome;
	private String endereco;
	private String telefone;
	private String email;
	private String senha;
	
	public int getId(){
		return id;
	}
	
	public void setId(int id){
		this.id=id;
	}
	public String getNome(){
		return nome;
	}
	
	
	public void setNome(String nome){
		this.nome=nome;
	}
	public String getEndereco(){
		return endereco;
	}
	public void setEndereco(String endereco){
		this.endereco=endereco;
	}
	public String getTelefone(){
		return telefone;
	}
	public void setTelefone (String telefone){
		this.telefone=telefone;
	}
	public String getEmail(){
		return email;
	}
	public void setEmail(String email){
		this.email=email;
	}
	public String getSenha(){
		return senha;
	}
	public void setSenha(String senha){
		this.senha=senha;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
