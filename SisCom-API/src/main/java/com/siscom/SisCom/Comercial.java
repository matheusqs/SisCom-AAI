package com.siscom.SisCom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.siscom.model.*;

@RestController
public class Comercial {

	private ArrayList<Pessoa> pessoas = new ArrayList<Pessoa>();
	private ArrayList<Produto> produtos = new ArrayList<Produto>();
	private ArrayList<Compra> compras = new ArrayList<Compra>();
	private ArrayList<Venda> vendas = new ArrayList<Venda>();
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/vendedor")
	public void postVendedor(@RequestBody Vendedor vendedor) throws SisComException {
		this.inserirPessoa(vendedor);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/cliente")
	public void postCliente(@RequestBody Cliente cliente) throws SisComException {
		this.inserirPessoa(cliente);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/fornecedor")
	public void postFornecedor(@RequestBody Fornecedor fornecedor) throws SisComException {
		this.inserirPessoa(fornecedor);
	}

	public void inserirPessoa(@RequestBody Pessoa pessoa) throws SisComException {
		if (pessoa instanceof Cliente) {
			Cliente pessoaEncontrada = this.findClienteByCpf(((Cliente) pessoa).getCpf());

			if (pessoaEncontrada != null) {
				throw new SisComException("Ja existe um cliente cadastrado com esse CPF!");
			}
		} else if (pessoa instanceof Fornecedor) {
			Fornecedor pessoaEncontrada = this.findFornecedorByCnpj(((Fornecedor) pessoa).getCnpj());

			if (pessoaEncontrada != null) {
				throw new SisComException("Ja existe um fornecedor cadastrado com esse CNPJ!");
			}
		} else if (pessoa instanceof Vendedor) {
			Vendedor pessoaEncontrada = this.findVendedorByCpf(((Vendedor) pessoa).getCpf());

			if (pessoaEncontrada != null) {
				throw new SisComException("Ja existe um vendedor cadastrado com esse CPF!");
			} else if (((Vendedor) pessoa).getMetaMensal() <= 0) {
				throw new SisComException("O vendedor tem que ter meta mensal maior que zero!");
			}
		}

		this.pessoas.add(pessoa);
	}

	@DeleteMapping("/pessoa/{id}")
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
					"Nao pode haver produtos duplicados, por favor aumente a quantidade comprada dele somente!");
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
					"Nao pode haver produtos duplicados, por favor diminua a quantidade vendida dele somente!");
		}

		if (formaPagto == 2 && this.calcularTotalVenda(itensVendidos) > cliente.getLimiteCredito()) {
			throw new SisComException("Valor maior que o limete do cliente!");
		}

		this.vendas.add(new Venda(cliente, vendedor, itensVendidos, formaPagto, new Date()));
		this.decrementarEstoqueVenda(itensVendidos);
	}

	public void removerVenda(int numVenda) {
		Venda venda = this.getVenda(numVenda);
		this.vendas.remove(venda);
		this.incrementarEstoqueVenda(venda.getVendaItens());
	}

	public void inserirProduto(Produto produto) {
		this.produtos.add(produto);
	}

	public void removerProduto(int numProduto) throws SisComException {
		if (this.hasCompraProduto(numProduto)) {
			throw new SisComException("Ainda ha compra para esse produto!");
		} else if (this.hasVendaProduto(numProduto)) {
			throw new SisComException("Ainda ha venda para esse produto!");
		}

		Produto produto = this.getProduto(numProduto);
		this.produtos.remove(produto);
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/clientes")
	public ArrayList<Cliente> getClientesOrdemAlfabetica() {
		ArrayList<Cliente> clientes = new ArrayList<Cliente>();

		for (Pessoa pessoa : this.pessoas) {
			if (pessoa instanceof Cliente) {
				clientes.add((Cliente) pessoa);
			}
		}

		Collections.sort(clientes);
		return clientes;
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/fornecedores")
	public ArrayList<Fornecedor> getFornecedoresOremAlfabetica() {
		ArrayList<Fornecedor> fornecedores = new ArrayList<Fornecedor>();

		for (Pessoa pessoa : this.pessoas) {
			if (pessoa instanceof Fornecedor) {
				fornecedores.add((Fornecedor) pessoa);
			}
		}

		Collections.sort(fornecedores);
		return fornecedores;
	}

	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/vendedores")
	public ArrayList<Vendedor> getVendedoresOremAlfabetica() {
		ArrayList<Vendedor> vendedores = new ArrayList<Vendedor>();

		for (Pessoa pessoa : this.pessoas) {
			if (pessoa instanceof Vendedor) {
				vendedores.add((Vendedor) pessoa);
			}
		}

		Collections.sort(vendedores);
		return vendedores;
	}

	public ArrayList<Produto> getProdutosOrderbyName() {
		Collections.sort(this.produtos);
		return this.produtos;
	}

	public ArrayList<Produto> getProdutosOrderbyNameWithLowEstoque() {
		ArrayList<Produto> produtos = this.getProdutosOrderbyName();
		for (Produto produto : produtos) {
			if (produto.getEstoqueMinimo() >= produto.getEstoque()) {
				produtos.remove(produto);
			}
		}
		return produtos;
	}

	public ArrayList<Venda> getVendasByPeriodByCliente(String nome, Date dateInicio, Date dataFim) {
		ArrayList<Venda> vendas = new ArrayList<Venda>();

		for (Venda venda : this.vendas) {
			if (venda.getCliente().getNome().contains(nome) && venda.getDataVenda().after(dateInicio)
					&& venda.getDataVenda().before(dataFim)) {
				vendas.add(venda);
			}
		}

		Collections.sort(vendas, Venda.dateComparator);
		return vendas;
	}

	public ArrayList<Venda> getVendasByPeriodByVendedor(String nome, Date dateInicio, Date dataFim) {
		ArrayList<Venda> vendas = new ArrayList<Venda>();

		for (Venda venda : this.vendas) {
			if (venda.getVendedor().getNome().contains(nome) && venda.getDataVenda().after(dateInicio)
					&& venda.getDataVenda().before(dataFim)) {
				vendas.add(venda);
			}
		}

		Collections.sort(vendas, Venda.dateComparator);
		return vendas;
	}
	
	public ArrayList<Compra> getComprasByPeriodByFornecedor(String nome, Date dateInicio, Date dataFim) {
		ArrayList<Compra> compras = new ArrayList<Compra>();

		for (Compra compra : this.compras) {
			if (compra.getFornecedor().getNome().contains(nome) && compra.getDataCompra().after(dateInicio)
					&& compra.getDataCompra().before(dataFim)) {
				compras.add(compra);
			}
		}

		Collections.sort(compras, Compra.dateComparator);
		return compras;
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

	private Venda getVenda(int numVenda) {
		for (Venda venda : this.vendas) {
			if (venda.getNumVenda() == numVenda) {
				return venda;
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

	private boolean hasCompraProduto(int numProduto) {
		for (Compra compra : this.compras) {
			for (ItemCompra item : compra.getCompraItens()) {
				if (item.getProduto().getCodigo() == numProduto) {
					return true;
				}
			}
		}

		return false;
	}

	private boolean hasVendaProduto(int numProduto) {
		for (Venda venda : this.vendas) {
			for (ItemVenda item : venda.getVendaItens()) {
				if (item.getProduto().getCodigo() == numProduto) {
					return true;
				}
			}
		}

		return false;
	}

	private Produto getProduto(int numProduto) {
		for (Produto produto : this.produtos) {
			if (produto.getCodigo() == numProduto) {
				return produto;
			}
		}

		return null;
	}

	private Cliente findClienteByCpf(String cpf) {
		for (Pessoa pessoa : this.pessoas) {
			if (pessoa instanceof Cliente && ((Cliente) pessoa).getCpf().equals(cpf)) {
				return (Cliente) pessoa;
			}
		}

		return null;
	}

	private Fornecedor findFornecedorByCnpj(String cnpj) {
		for (Pessoa pessoa : this.pessoas) {
			if (pessoa instanceof Fornecedor && ((Fornecedor) pessoa).getCnpj().equals(cnpj)) {
				return (Fornecedor) pessoa;
			}
		}

		return null;
	}

	private Vendedor findVendedorByCpf(String cpf) {
		for (Pessoa pessoa : this.pessoas) {
			if (pessoa instanceof Vendedor && ((Vendedor) pessoa).getCpf().equals(cpf)) {
				return (Vendedor) pessoa;
			}
		}

		return null;
	}
}
