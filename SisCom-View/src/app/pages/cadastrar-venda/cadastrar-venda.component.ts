import { Component, OnInit } from '@angular/core';
import { FormGroup, FormArray, FormBuilder } from '@angular/forms';
import { Produto } from 'src/app/core/models/produto';
import { ProdutoService } from 'src/app/core/services/produto.service';
import { VendaService } from 'src/app/core/services/Venda.service';
import { Router } from '@angular/router';
import { Venda } from 'src/app/core/models/Venda';
import { ItemVenda } from 'src/app/core/models/item-Venda';
import { VendedorService } from 'src/app/core/services/vendedor.service';
import { ClienteService } from 'src/app/core/services/cliente.service';
import { Cliente } from 'src/app/core/models/cliente';
import { Vendedor } from 'src/app/core/models/vendedor';

@Component({
  selector: 'app-cadastrar-venda',
  templateUrl: './cadastrar-venda.component.html',
  styleUrls: ['./cadastrar-venda.component.scss'],
})
export class CadastrarVendaComponent implements OnInit {
  public venda: FormGroup;
  public vendedores: Vendedor[] = [];
  public clientes: Cliente[] = [];
  public produtos: Produto[] = [];

  get vendaItens() {
    return this.venda.get('vendaItens') as FormArray;
  }

  constructor(
    vendedorService: VendedorService,
    clienteService: ClienteService,
    produtoService: ProdutoService,
    private vendaService: VendaService,
    private router: Router,
    private fb: FormBuilder
  ) {
    vendedorService.getTodosVendedores().subscribe((vendedores) => {
      this.vendedores = vendedores;
    });

    clienteService.getTodosClientes().subscribe((clientes) => {
      this.clientes = clientes;
    });

    produtoService.getTodosProdutos().subscribe((produtos) => {
      this.produtos = produtos;
    });
  }

  ngOnInit() {
    this.venda = this.fb.group({
      cliente: [''],
      vendedor: [''],
      vendaItens: this.fb.array([
        this.fb.group({
          produto: [''],
          quantVenda: [''],
        }),
      ]),
      formaPagto: [''],
    });
  }

  public cadastrarVenda() {
    const venda = this.getVendaValue();

    this.vendaService.cadastrarVenda(venda).subscribe();

    this.goBack();
  }

  private getVendaValue(): Venda {
    const venda = this.venda.value;

    venda.cliente = this.clientes.find(
      (cliente) => cliente.nome === venda.cliente
    );

    venda.vendedor = this.vendedores.find(
      (vendedor) => vendedor.nome === venda.vendedor
    );

    venda.vendaItens = this.setItemVenda(this.vendaItens);

    venda.dataVenda = new Date();

    return venda;
  }

  private setItemVenda(vendaItens): ItemVenda[] {
    const itemVendas: ItemVenda[] = [];

    vendaItens.controls.forEach((itemVenda, i) => {
      const nomeProduto = itemVenda.get('produto').value;

      itemVendas[i] = itemVenda.value;
      itemVendas[i].produto = this.produtos.find(
        (produto) => nomeProduto === produto.nome
      );
    });

    return itemVendas.map((itemVenda) => {
      return {
        ...itemVenda,
        valorVenda: parseFloat(
          (itemVenda.produto.precoUnitario * itemVenda.quantVenda).toFixed(2)
        ),
      };
    });
  }

  public goBack() {
    this.router.navigate(['/']);
  }

  public addItemVenda() {
    this.vendaItens.push(
      this.fb.group({
        produto: [''],
        quantVenda: [''],
      })
    );
  }
}
