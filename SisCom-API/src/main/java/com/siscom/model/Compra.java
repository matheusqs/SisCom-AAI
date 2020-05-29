package com.siscom.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Compra {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int numCompra;
	private Fornecedor fornecedor;
	private ArrayList<ItemCompra> compraItens;
	private Date dataCompra;

	public Compra(Fornecedor fornecedor, ArrayList<ItemCompra> compraItens, Date dataCompra) {
		this.fornecedor = fornecedor;
		this.compraItens = compraItens;
		this.dataCompra = dataCompra;
	}

	public int getNumCompra() {
		return numCompra;
	}

	public void setNumCompra(int numCompra) {
		this.numCompra = numCompra;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public ArrayList<ItemCompra> getCompraItens() {
		return compraItens;
	}

	public void setCompraItens(ArrayList<ItemCompra> compraItens) {
		this.compraItens = compraItens;
	}

	public Date getDataCompra() {
		return dataCompra;
	}

	public void setDataCompra(Date dataCompra) {
		this.dataCompra = dataCompra;
	}
	
	public static Comparator<Compra> dateComparator = new Comparator<Compra>() {
		@Override
		public int compare(Compra compra1, Compra compra2) {
			return (compra1.getDataCompra().before(compra2.getDataCompra()) ? -1
					: (compra1.getDataCompra().equals(compra2.getDataCompra()) ? 0 : 1));
		}
	};

	@Override
	public String toString() {
		return "Compra [numCompra=" + numCompra + ", fornecedor=" + fornecedor + ", dataCompra=" + dataCompra + "]";
	}

}
