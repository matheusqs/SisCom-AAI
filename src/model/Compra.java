package model;

import java.util.ArrayList;
import java.util.Date;

public class Compra {
	private int numCompra; // TODO sequencial
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

	@Override
	public String toString() {
		return "Compra [numCompra=" + numCompra + ", fornecedor=" + fornecedor + ", dataCompra=" + dataCompra + "]";
	}

}
