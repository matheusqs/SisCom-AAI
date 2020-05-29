package com.siscom.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ItemCompra {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private Produto produto;
	private int quantCompra;
	private double valorCompra;

	public ItemCompra(Produto produto, int quantCompra, double valorCompra) {
		this.produto = produto;
		this.quantCompra = quantCompra;
		this.valorCompra = valorCompra;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public int getQuantCompra() {
		return quantCompra;
	}

	public void setQuantCompra(int quantCompra) {
		this.quantCompra = quantCompra;
	}

	public double getValorCompra() {
		return valorCompra;
	}

	public void setValorCompra(double valorCompra) {
		this.valorCompra = valorCompra;
	}

	@Override
	public String toString() {
		return "ItemCompra [quantCompra=" + quantCompra + ", valorCompra=" + valorCompra + "]";
	}

}
