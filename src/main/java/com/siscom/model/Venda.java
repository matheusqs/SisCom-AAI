package com.siscom.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

public class Venda {
	private int numVenda; // TODO sequencial
	private Cliente cliente;
	private Vendedor vendedor;
	private ArrayList<ItemVenda> vendaItens;
	private int formaPagto;
	private Date dataVenda;

	public Venda(Cliente cliente, Vendedor vendedor, ArrayList<ItemVenda> vendaItens, int formaPagto, Date dataVenda) {
		this.cliente = cliente;
		this.vendedor = vendedor;
		this.vendaItens = vendaItens;
		this.formaPagto = formaPagto;
		this.dataVenda = dataVenda;
	}

	public int getNumVenda() {
		return numVenda;
	}

	public void setNumVenda(int numVenda) {
		this.numVenda = numVenda;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Vendedor getVendedor() {
		return vendedor;
	}

	public void setVendedor(Vendedor vendedor) {
		this.vendedor = vendedor;
	}

	public ArrayList<ItemVenda> getVendaItens() {
		return vendaItens;
	}

	public void setVendaItens(ArrayList<ItemVenda> vendaItens) {
		this.vendaItens = vendaItens;
	}

	public int getFormaPagto() {
		return formaPagto;
	}

	public void setFormaPagto(int formaPagto) {
		this.formaPagto = formaPagto;
	}

	public Date getDataVenda() {
		return dataVenda;
	}

	public void setDataVenda(Date dataVenda) {
		this.dataVenda = dataVenda;
	}

	public static Comparator<Venda> dateComparator = new Comparator<Venda>() {
		@Override
		public int compare(Venda venda1, Venda venda2) {
			return (venda1.getDataVenda().before(venda2.getDataVenda()) ? -1
					: (venda1.getDataVenda().equals(venda2.getDataVenda()) ? 0 : 1));
		}
	};

	@Override
	public String toString() {
		return "Venda [numVenda=" + numVenda + ", cliente=" + cliente + ", vendedor=" + vendedor + ", formaPagto="
				+ formaPagto + ", dataVenda=" + dataVenda + "]";
	}

}
