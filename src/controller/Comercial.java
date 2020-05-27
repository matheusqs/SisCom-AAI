package controller;

import java.util.ArrayList;
import java.util.Date;

import model.*;

public class Comercial {

	private ArrayList<Pessoa> pessoas;
	private ArrayList<Produto> produtos;
	private ArrayList<Compra> compras;
	private ArrayList<Venda> vendas;

	public void inserirPessoa(Pessoa pessoa) throws SisComException {
		for (Pessoa pessoaCadastrada : this.pessoas) {
			if (pessoaCadastrada.getTipoPessoa().equals(pessoa.getTipoPessoa())) {
				if (pessoa instanceof Fornecedor
						&& ((Fornecedor) pessoa).getCnpj().equals(((Fornecedor) pessoaCadastrada).getCnpj())) {
					throw new SisComException("Já existe um fornecedor cadastrado com esse CNPJ!");
				} else if (pessoa instanceof Cliente
						&& ((Cliente) pessoa).getCpf().equals(((Cliente) pessoaCadastrada).getCpf())) {
					throw new SisComException("Já existe um cliente cadastrado com esse CPF!");
				} else {
					if (((Vendedor) pessoa).getCpf().equals(((Vendedor) pessoaCadastrada).getCpf())) {
						throw new SisComException("Já existe um vendedor cadastrado com esse CPF!");
					} else if (((Vendedor) pessoa).getMetaMensal() == 0) {
						throw new SisComException("O vendedor tem que ter meta mensal maior que zero!");
					}
				}
			}
		}

		this.pessoas.add(pessoa);
	}

	public void removerPessoa(Pessoa pessoa) throws SisComException {
		if (pessoa instanceof Fornecedor && this.isFornecedorHasCompra((Fornecedor) pessoa)) {
			throw new SisComException("O fornecedor tem compra cadastrada!");
		} else if (pessoa instanceof Cliente && this.isClienteHasVenda((Cliente) pessoa)) {
			throw new SisComException("O cliente tem venda cadastrada!");
		} else if (this.isVendedorHasVenda((Vendedor) pessoa)) {
			throw new SisComException("O vendedor tem venda cadastrada!");
		}

		this.pessoas.remove(pessoa);
	}

	public void comprarProdutos(Fornecedor fornecedor, ArrayList<ItemCompra> itensComprados) throws SisComException {
		if (this.hasDuplicateProdutosCompra(itensComprados)) {
			throw new SisComException(
					"Não pode haver produtos duplicados, por favor aumente a quantidade comprada dele somente!");
		}

		this.compras.add(new Compra(fornecedor, itensComprados, new Date()));
		this.incrementarEstoqueCompra(itensComprados);
	}

	public void removerCompra(int numCompra) throws SisComException {
		Compra compra = this.getCompra(numCompra);
		this.compras.remove(compra);
		this.decrementarEstoqueCompra(compra.getCompraItens());
	}

	public void realizarVenda(Cliente cliente, Vendedor vendedor, int formaPagto, ArrayList<ItemVenda> itensVendidos)
			throws SisComException {
		if (this.hasDuplicateProdutosVenda(itensVendidos)) {
			throw new SisComException(
					"Não pode haver produtos duplicados, por favor diminua a quantidade vendida dele somente!");
		}

		if (formaPagto == 2 && this.calcularTotalVenda(itensVendidos) > cliente.getLimiteCredito()) {
			throw new SisComException("Valor maior que o limete do cliente!");
		}

		this.vendas.add(new Venda(cliente, vendedor, itensVendidos, formaPagto, new Date()));
		this.decrementarEstoqueVenda(itensVendidos);
	}

	private boolean isFornecedorHasCompra(Fornecedor fornecedor) {
		for (Compra compra : this.compras) {
			if (compra.getFornecedor().compareTo(fornecedor) == 0) {
				return true;
			}
		}

		return false;
	}

	private boolean isClienteHasVenda(Cliente cliente) {
		for (Venda venda : this.vendas) {
			if (venda.getCliente().compareTo(cliente) == 0) {
				return true;
			}
		}

		return false;
	}

	private boolean isVendedorHasVenda(Vendedor vendedor) {
		for (Venda venda : this.vendas) {
			if (venda.getVendedor().compareTo(vendedor) == 0) {
				return true;
			}
		}

		return false;
	}

	private void incrementarEstoqueCompra(ArrayList<ItemCompra> itens) {
		for (ItemCompra item : itens) {
			int produtoIndex = this.produtos.indexOf(item.getProduto());
			Produto produto = this.produtos.get(produtoIndex);
			produto.incrementQuantidade(item.getQuantCompra());
			this.produtos.set(produtoIndex, produto);
		}
	}

	private void decrementarEstoqueCompra(ArrayList<ItemCompra> itens) throws SisComException {
		for (ItemCompra item : itens) {
			int produtoIndex = this.produtos.indexOf(item.getProduto());
			Produto produto = this.produtos.get(produtoIndex);
			produto.decrementQuantidade(item.getQuantCompra());
			this.produtos.set(produtoIndex, produto);
		}
	}

	private void incrementarEstoqueVenda(ArrayList<ItemVenda> itens) {
		for (ItemVenda item : itens) {
			int produtoIndex = this.produtos.indexOf(item.getProduto());
			Produto produto = this.produtos.get(produtoIndex);
			produto.incrementQuantidade(item.getQuantVenda());
			this.produtos.set(produtoIndex, produto);
		}
	}

	private void decrementarEstoqueVenda(ArrayList<ItemVenda> itens) throws SisComException {
		for (ItemVenda item : itens) {
			int produtoIndex = this.produtos.indexOf(item.getProduto());
			Produto produto = this.produtos.get(produtoIndex);
			produto.decrementQuantidade(item.getQuantVenda());
			this.produtos.set(produtoIndex, produto);
		}
	}

	private boolean hasDuplicateProdutosCompra(ArrayList<ItemCompra> itens) {
		for (int i = 0; i < itens.size(); i++) {
			for (int j = i; j < itens.size(); j++) {
				if (itens.get(i).equals(itens.get(j))) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean hasDuplicateProdutosVenda(ArrayList<ItemVenda> itens) {
		for (int i = 0; i < itens.size(); i++) {
			for (int j = i; j < itens.size(); j++) {
				if (itens.get(i).equals(itens.get(j))) {
					return true;
				}
			}
		}
		return false;
	}

	private Compra getCompra(int numCompra) {
		for (Compra compra : this.compras) {
			if (compra.getNumCompra() == numCompra) {
				return compra;
			}
		}

		return null;
	}

	private double calcularTotalVenda(ArrayList<ItemVenda> itens) {
		double total = 0;
		for (ItemVenda item : itens) {
			total += item.getValorVenda();
		}

		return total;
	}
}
