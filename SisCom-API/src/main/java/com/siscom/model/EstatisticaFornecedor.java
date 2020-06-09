package com.siscom.model;

/**
 * @author mathe
 *
 */
public class EstatisticaFornecedor {
	private String nome;
	private int qtdCompras;
	private double vlrTotal;

	public EstatisticaFornecedor(String nome, int qtdCompras, double vlrTotal) {
		this.nome = nome;
		this.qtdCompras = qtdCompras;
		this.vlrTotal = vlrTotal;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getQtdCompras() {
		return qtdCompras;
	}

	public void setQtdCompras(int qtdCompras) {
		this.qtdCompras = qtdCompras;
	}

	public double getVlrTotal() {
		return vlrTotal;
	}

	public void setVlrTotal(double vlrTotal) {
		this.vlrTotal = vlrTotal;
	}

	@Override
	public String toString() {
		return "EstatisticaFornecedor [nome=" + nome + ", qtdCompras=" + qtdCompras + ", vlrTotal=" + vlrTotal + "]";
	}

}
