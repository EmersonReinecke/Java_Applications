package br.com.OrdemDeServico.ordemServico;

import java.io.Serializable;

public class OrdemdeServico implements Serializable {

	
	private int id;
	private int idcliente;
	private String cliente;
	private String dataEntrada;
	private String dataEntrega;
	private String equipamento;
	private String valorServico;
	private String DescricaoOrdemServico;
	
	
	public int getIdcliente() {
		return idcliente;
	}
	public void setIdcliente(int idcliente) {
		this.idcliente = idcliente;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public String getDataEntrada() {
		return dataEntrada;
	}
	public void setDataEntrada(String dataEntrada) {
		this.dataEntrada = dataEntrada;
	}
	public String getDataEntrega() {
		return dataEntrega;
	}
	public void setDataEntrega(String dataEntrega) {
		this.dataEntrega = dataEntrega;
	}
	public String getEquipamento() {
		return equipamento;
	}
	public void setEquipamento(String equipamento) {
		this.equipamento = equipamento;
	}
	public String getValorServico() {
		return valorServico;
	}
	public void setValorServico(String valorServico) {
		this.valorServico = valorServico;
	}
	public String getDescricaoOrdemServico() {
		return DescricaoOrdemServico;
	}
	public void setDescricaoOrdemServico(String DescricaoOrdemServico) {
		this.DescricaoOrdemServico = DescricaoOrdemServico;
	}
	
	
	
}
