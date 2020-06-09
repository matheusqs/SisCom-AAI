package com.siscom.siscom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.siscom.model.*;
import com.siscom.repository.ClienteRepository;
import com.siscom.repository.CompraRepository;
import com.siscom.repository.FornecedorRepository;
import com.siscom.repository.ProdutoRepository;
import com.siscom.repository.VendaRepository;
import com.siscom.repository.VendedorRepository;

@RestController
public class Comercial {
	private ArrayList<Pessoa> pessoas = new ArrayList<Pessoa>();
	private ArrayList<Produto> produtos = new ArrayList<Produto>();
	private ArrayList<Compra> compras = new ArrayList<Compra>();
	private ArrayList<Venda> vendas = new ArrayList<Venda>();

	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/vendedor")
	public void postVendedor(@RequestBody Vendedor vendedor) throws SisComException {
		Vendedor pessoaEncontrada = this.findVendedorByCpf((vendedor).getCpf());

		if (pessoaEncontrada != null) {
			throw new SisComException("Ja existe um vendedor cadastrado com esse CPF!");
		} else if ((vendedor).getMetaMensal() <= 0) {
			throw new SisComException("O vendedor tem que ter meta mensal maior que zero!");
		}

		this.pessoas.add(vendedor);
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/cliente")
	public void postCliente(@RequestBody Cliente cliente) throws SisComException {
		Cliente pessoaEncontrada = this.findClienteByCpf((cliente).getCpf());

		if (pessoaEncontrada != null) {
			throw new SisComException("Ja existe um cliente cadastrado com esse CPF!");
		}

		this.pessoas.add(cliente);
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/fornecedor")
	public void postFornecedor(@RequestBody Fornecedor fornecedor) throws SisComException {
		Fornecedor pessoaEncontrada = this.findFornecedorByCnpj((fornecedor).getCnpj());

		if (pessoaEncontrada != null) {
			throw new SisComException("Ja existe um fornecedor cadastrado com esse CNPJ!");
		}

		this.pessoas.add(fornecedor);
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@DeleteMapping("/vendedor/{nome}")
	public void deleteVendedor(@PathVariable String nome) throws SisComException {
		this.removerPessoa(nome);
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@DeleteMapping("/cliente/{nome}")
	public void deleteCliente(@PathVariable String nome) throws SisComException {
		this.removerPessoa(nome);
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@DeleteMapping("/fornecedor/{nome}")
	public void deleteFornecedor(@PathVariable String nome) throws SisComException {
		this.removerPessoa(nome);
	}

	private void removerPessoa(String nome) throws SisComException {
		Pessoa pessoaAchada = null;

		for (Pessoa pessoa : this.pessoas) {
			if (pessoa.getNome().equalsIgnoreCase(nome)) {
				pessoaAchada = pessoa;
			}
		}

		if (pessoaAchada != null) {
			if (pessoaAchada instanceof Fornecedor && this.isFornecedorHasCompra((Fornecedor) pessoaAchada)) {
				throw new SisComException("O fornecedor tem compra cadastrada!");
			} else if (pessoaAchada instanceof Cliente && this.isClienteHasVenda((Cliente) pessoaAchada)) {
				throw new SisComException("O cliente tem venda cadastrada!");
			} else if (pessoaAchada instanceof Vendedor && this.isVendedorHasVenda((Vendedor) pessoaAchada)) {
				throw new SisComException("O vendedor tem venda cadastrada!");
			}

			this.pessoas.remove(pessoaAchada);
		}
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/compra")
	public void comprarProdutos(@RequestBody Compra compra) throws SisComException {
		if (this.hasDuplicateProdutosCompra(compra.getCompraItens())) {
			throw new SisComException(
					"Nao pode haver produtos duplicados, por favor aumente a quantidade comprada dele somente!");
		}

		this.compras.add(compra);
		this.incrementarEstoqueCompra(compra.getCompraItens());
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@DeleteMapping("/compra/fornecedor/{nomeFornecedor}/data/{dataCompra}")
	public void removerCompra(@PathVariable String nomeFornecedor, @PathVariable Date dataCompra)
			throws SisComException {
		Compra compra = this.getCompra(nomeFornecedor, dataCompra);
		this.compras.remove(compra);
		this.decrementarEstoqueCompra(compra.getCompraItens());
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/venda")
	public void realizarVenda(@RequestBody Venda venda) throws SisComException {
		if (this.hasDuplicateProdutosVenda(venda.getVendaItens())) {
			throw new SisComException(
					"Nao pode haver produtos duplicados, por favor diminua a quantidade vendida dele somente!");
		}

		if (venda.getFormaPagto() == 2
				&& this.calcularTotalVenda(venda.getVendaItens()) > venda.getCliente().getLimiteCredito()) {
			throw new SisComException("Valor maior que o limete do cliente!");
		}

		this.vendas.add(venda);
		this.decrementarEstoqueVenda(venda.getVendaItens());
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@DeleteMapping("/venda/cliente/{nomeCliente}/vendedor/{nomeVendedor}/data/{dataCompra}")
	public void removerVenda(@PathVariable String nomeCliente, @PathVariable String nomeVendedor,
			@PathVariable Date dataCompra) {
		Venda venda = this.getVenda(nomeCliente, nomeVendedor, dataCompra);
		this.vendas.remove(venda);
		this.incrementarEstoqueVenda(venda.getVendaItens());
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/produto")
	public void inserirProduto(@RequestBody Produto produto) {
		this.produtos.add(produto);
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@DeleteMapping("/produto/{nome}")
	public void removerProduto(@PathVariable String nome) throws SisComException {
		if (this.hasCompraProduto(nome)) {
			throw new SisComException("Ainda ha compra para esse produto!");
		} else if (this.hasVendaProduto(nome)) {
			throw new SisComException("Ainda ha venda para esse produto!");
		}

		Produto produto = this.getProduto(nome);
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

	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/produtos")
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

	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/vendas/cliente/{nomeCliente}/vendedor/{nomeVendedor}/data-inicio/{dataInicio}/data-fim/{dataFim}")
	public ArrayList<Venda> getVendasByPeriodByVendedor(@PathVariable String nomeCliente,
			@PathVariable String nomeVendedor, @PathVariable Date dataInicio, @PathVariable Date dataFim) {
		ArrayList<Venda> vendas = new ArrayList<Venda>();

		for (Venda venda : this.vendas) {
			if (venda.getVendedor().getNome().contains(nomeVendedor)
					&& venda.getCliente().getNome().contains(nomeCliente) && venda.getDataVenda().after(dataInicio)
					&& venda.getDataVenda().before(dataFim)) {
				vendas.add(venda);
			}
		}

		Collections.sort(vendas, Venda.dateComparator);
		return vendas;
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/compras/fornecedor/{nome}/data-inicio/{dataInicio}/data-fim/{dataFim}")
	public ArrayList<Compra> getComprasByPeriodByFornecedor(@PathVariable String nome, @PathVariable Date dataInicio,
			@PathVariable Date dataFim) {
		ArrayList<Compra> compras = new ArrayList<Compra>();

		for (Compra compra : this.compras) {
			compra.getDataCompra().toString();
			if (compra.getFornecedor().getNome().contains(nome) && compra.getDataCompra().after(dataInicio)
					&& compra.getDataCompra().before(dataFim)) {
				compras.add(compra);
			}
		}

		Collections.sort(compras, Compra.dateComparator);
		return compras;
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/compras/data-inicio/{dataInicio}/data-fim/{dataFim}")
	public ArrayList<EstatisticaFornecedor> getEstatisticaCompra(@PathVariable Date dataInicio, @PathVariable Date dataFim) {
		ArrayList<EstatisticaFornecedor> estatistica = new ArrayList<EstatisticaFornecedor>();

		for (Pessoa pessoa : this.pessoas) {
			if (pessoa instanceof Fornecedor) {
				estatistica.add(new EstatisticaFornecedor(pessoa.getNome(),
						this.getQuantidadeCompras((Fornecedor) pessoa, dataInicio, dataFim),
						this.calcularTotalComprado((Fornecedor) pessoa, dataInicio, dataFim)));
			}
		}
		
		return estatistica;
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/vendas/cliente/data-inicio/{dataInicio}/data-fim/{dataFim}")
	public ArrayList<EstatisticaVenda> getEstatisticaVendaCliente(@PathVariable Date dataInicio, @PathVariable Date dataFim) {
		ArrayList<EstatisticaVenda> estatistica = new ArrayList<EstatisticaVenda>();

		for (Pessoa pessoa : this.pessoas) {
			if (pessoa instanceof Cliente) {
				estatistica.add(new EstatisticaVenda(pessoa.getNome(),
						this.getQuantidadeVendasCliente((Cliente) pessoa, dataInicio, dataFim),
						this.calcularTotalVendidoCliente((Cliente) pessoa, dataInicio, dataFim)));
			}
		}
		
		return estatistica;
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/vendas/vendedor/data-inicio/{dataInicio}/data-fim/{dataFim}")
	public ArrayList<EstatisticaVenda> getEstatisticaVendaVendedor(@PathVariable Date dataInicio, @PathVariable Date dataFim) {
		ArrayList<EstatisticaVenda> estatistica = new ArrayList<EstatisticaVenda>();

		for (Pessoa pessoa : this.pessoas) {
			if (pessoa instanceof Vendedor) {
				estatistica.add(new EstatisticaVenda(pessoa.getNome(),
						this.getQuantidadeVendasVendedor((Vendedor) pessoa, dataInicio, dataFim),
						this.calcularTotalVendidoVendedor((Vendedor) pessoa, dataInicio, dataFim)));
			}
		}
		
		return estatistica;
	}

	private int getQuantidadeCompras(Fornecedor fornecedor, Date dataInicio, Date dataFim) {
		int quantidade = 0;

		for (Compra compra : this.compras) {
			if (compra.getFornecedor().compareTo(fornecedor) == 0 && compra.getDataCompra().after(dataInicio)
					&& compra.getDataCompra().before(dataFim)) {
				quantidade++;
			}
		}

		return quantidade;
	}

	private double calcularTotalComprado(Fornecedor fornecedor, Date dataInicio, Date dataFim) {
		double valor = 0;

		for (Compra compra : this.compras) {
			if (compra.getFornecedor().compareTo(fornecedor) == 0 && compra.getDataCompra().after(dataInicio)
					&& compra.getDataCompra().before(dataFim)) {
				for (ItemCompra item : compra.getCompraItens()) {
					valor += item.getValorCompra();
				}
			}
		}

		return valor;
	}
	
	private int getQuantidadeVendasCliente(Cliente cliente, Date dataInicio, Date dataFim) {
		int quantidade = 0;

		for (Venda venda : this.vendas) {
			if (venda.getCliente().compareTo(cliente) == 0 && venda.getDataVenda().after(dataInicio)
					&& venda.getDataVenda().before(dataFim)) {
				quantidade++;
			}
		}

		return quantidade;
	}

	private double calcularTotalVendidoCliente(Cliente cliente, Date dataInicio, Date dataFim) {
		double valor = 0;

		for (Venda venda : this.vendas) {
			if (venda.getCliente().compareTo(cliente) == 0 && venda.getDataVenda().after(dataInicio)
					&& venda.getDataVenda().before(dataFim)) {
				for (ItemVenda item : venda.getVendaItens()) {
					valor += item.getValorVenda();
				}
			}
		}

		return valor;
	}
	
	private int getQuantidadeVendasVendedor(Vendedor vendedor, Date dataInicio, Date dataFim) {
		int quantidade = 0;

		for (Venda venda : this.vendas) {
			if (venda.getVendedor().compareTo(vendedor) == 0 && venda.getDataVenda().after(dataInicio)
					&& venda.getDataVenda().before(dataFim)) {
				quantidade++;
			}
		}

		return quantidade;
	}

	private double calcularTotalVendidoVendedor(Vendedor vendedor, Date dataInicio, Date dataFim) {
		double valor = 0;

		for (Venda venda : this.vendas) {
			if (venda.getVendedor().compareTo(vendedor) == 0 && venda.getDataVenda().after(dataInicio)
					&& venda.getDataVenda().before(dataFim)) {
				for (ItemVenda item : venda.getVendaItens()) {
					valor += item.getValorVenda();
				}
			}
		}

		return valor;
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
			for (Produto produto : this.produtos) {
				if (produto.getNome().equalsIgnoreCase(item.getProduto().getNome())) {
					produto.incrementQuantidade(item.getQuantCompra());
					this.produtos.set(this.produtos.indexOf(produto), produto);
				}
			}
		}
	}

	private void decrementarEstoqueCompra(ArrayList<ItemCompra> itens) throws SisComException {
		for (ItemCompra item : itens) {
			for (Produto produto : this.produtos) {
				if (produto.getNome().equalsIgnoreCase(item.getProduto().getNome())) {
					produto.decrementQuantidade(item.getQuantCompra());
					this.produtos.set(this.produtos.indexOf(produto), produto);
				}
			}
		}
	}

	private void incrementarEstoqueVenda(ArrayList<ItemVenda> itens) {
		for (ItemVenda item : itens) {
			for (Produto produto : this.produtos) {
				if (produto.getNome().equalsIgnoreCase(item.getProduto().getNome())) {
					produto.incrementQuantidade(item.getQuantVenda());
					this.produtos.set(this.produtos.indexOf(produto), produto);
				}
			}
		}
	}

	private void decrementarEstoqueVenda(ArrayList<ItemVenda> itens) throws SisComException {
		for (ItemVenda item : itens) {
			for (Produto produto : this.produtos) {
				if (produto.getNome().equalsIgnoreCase(item.getProduto().getNome())) {
					produto.decrementQuantidade(item.getQuantVenda());
					this.produtos.set(this.produtos.indexOf(produto), produto);
				}
			}
		}
	}

	private boolean hasDuplicateProdutosCompra(ArrayList<ItemCompra> itens) {
		int itemCont = itens.size() - 1;

		for (int i = 0; i < itemCont; i++) {
			for (int j = i + 1; j < itens.size(); j++) {
				if (itens.get(i).equals(itens.get(j))) {
					return true;
				}
			}
		}

		return false;
	}

	private boolean hasDuplicateProdutosVenda(ArrayList<ItemVenda> itens) {
		int itemCont = itens.size() - 1;

		for (int i = 0; i < itemCont; i++) {
			for (int j = i + 1; j < itens.size(); j++) {
				if (itens.get(i).equals(itens.get(j))) {
					return true;
				}
			}
		}

		return false;
	}

	@SuppressWarnings("deprecation")
	private Compra getCompra(String fornecedor, Date data) {
		for (Compra compra : this.compras) {

			if (compra.getFornecedor().getNome().equalsIgnoreCase(fornecedor)
					&& compra.getDataCompra().getDate() == data.getDate()
					&& compra.getDataCompra().getMonth() == data.getMonth()
					&& compra.getDataCompra().getYear() == data.getYear()) {
				return compra;
			}
		}

		return null;
	}

	@SuppressWarnings("deprecation")
	private Venda getVenda(String nomeCliente, String nomeVendedor, Date data) {
		for (Venda venda : this.vendas) {
			if (venda.getCliente().getNome().equalsIgnoreCase(nomeCliente)
					&& venda.getVendedor().getNome().equalsIgnoreCase(nomeVendedor)
					&& venda.getDataVenda().getDate() == data.getDate()
					&& venda.getDataVenda().getMonth() == data.getMonth()
					&& venda.getDataVenda().getYear() == data.getYear()) {
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

	private boolean hasCompraProduto(String nome) {
		for (Compra compra : this.compras) {
			for (ItemCompra item : compra.getCompraItens()) {
				if (item.getProduto().getNome() == nome) {
					return true;
				}
			}
		}

		return false;
	}

	private boolean hasVendaProduto(String nome) {
		for (Venda venda : this.vendas) {
			for (ItemVenda item : venda.getVendaItens()) {
				if (item.getProduto().getNome() == nome) {
					return true;
				}
			}
		}

		return false;
	}

	private Produto getProduto(String nome) {
		for (Produto produto : this.produtos) {
			if (produto.getNome().equalsIgnoreCase(nome)) {
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
