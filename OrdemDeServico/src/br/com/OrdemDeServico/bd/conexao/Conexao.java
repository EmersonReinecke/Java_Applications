package br.com.OrdemDeServico.bd.conexao;

import java.sql.Connection;
//Ordem de Serviço
public class Conexao {

	private Connection conexao;

	public Connection abrirConexao() {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conexao = java.sql.DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/ordemdeservico?useTimezone=true&serverTimezone=UTC", "root", "root");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return conexao;

	}

	public void fecharConexao() {
		try {
			conexao.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
}
