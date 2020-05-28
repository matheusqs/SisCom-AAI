package com.siscom.model;

import java.util.Date;

public class Produto implements Comparable<Produto> {
	private int codigo; // TODO sequencial
	private String nome;
	private double precoUnitario;
	private int estoque;
	private int estoqueMinimo;
	private Date dataCad;

	public Produto(int codigo, String nome, double precoUnitario, int estoque, int estoqueMinimo, Date dataCad) {
		this.codigo = codigo;
		this.nome = nome;
		this.precoUnitario = precoUnitario;
		this.estoque = estoque;
		this.estoqueMinimo = estoqueMinimo;
		this.dataCad = dataCad;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getPrecoUnitario() {
		return precoUnitario;
	}

	public void setPrecoUnitario(double precoUnitario) {
		this.precoUnitario = precoUnitario;
	}

	public int getEstoque() {
		return estoque;
	}

	public void setEstoque(int estoque) {
		this.estoque = estoque;
	}

	public int getEstoqueMinimo() {
		return estoqueMinimo;
	}

	public void setEstoqueMinimo(int estoqueMinimo) {
		this.estoqueMinimo = estoqueMinimo;
	}

	public Date getDataCad() {
		return dataCad;
	}

	public void setDataCad(Date dataCad) {
		this.dataCad = dataCad;
	}

	public void incrementQuantidade(int quantidade) {
		this.estoque += quantidade;
	}

	public void decrementQuantidade(int quantidade) throws SisComException {
		if (this.estoque < quantidade) {
			throw new SisComException(this.nome, this.estoque, "Estoque Insuficiente.");
		} else {
			this.estoque -= quantidade;
		}
	}

	public int compareTo(Produto produto) {
		return produto.getNome().compareTo(this.nome);
	}

	@Override
	public String toString() {
		return "Produto [codigo=" + codigo + ", nome=" + nome + ", precoUnitario=" + precoUnitario + ", estoque="
				+ estoque + ", estoqueMinimo=" + estoqueMinimo + ", dataCad=" + dataCad + "]";
	}

}
