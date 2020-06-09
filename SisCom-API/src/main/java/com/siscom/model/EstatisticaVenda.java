package com.siscom.model;

public class EstatisticaVenda {
	private String nome;
	private int qtdVendas;
	private double vlrTotal;

	public EstatisticaVenda(String nome, int qtdVendas, double vlrTotal) {
		super();
		this.nome = nome;
		this.qtdVendas = qtdVendas;
		this.vlrTotal = vlrTotal;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getQtdVendas() {
		return qtdVendas;
	}

	public void setQtdVendas(int qtdVendas) {
		this.qtdVendas = qtdVendas;
	}

	public double getVlrTotal() {
		return vlrTotal;
	}

	public void setVlrTotal(double vlrTotal) {
		this.vlrTotal = vlrTotal;
	}

	@Override
	public String toString() {
		return "EstatisticaVendaCliente [nome=" + nome + ", qtdVendas=" + qtdVendas + ", vlrTotal=" + vlrTotal + "]";
	}

}
