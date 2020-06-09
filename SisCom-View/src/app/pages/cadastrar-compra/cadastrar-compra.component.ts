import { Component, OnInit } from '@angular/core';
import { CompraService } from 'src/app/core/services/compra.service';
import { Router } from '@angular/router';
import { FormGroup, FormBuilder, FormArray } from '@angular/forms';
import { Compra } from 'src/app/core/models/compra';
import { FornecedorService } from 'src/app/core/services/fornecedor.service';
import { ProdutoService } from 'src/app/core/services/produto.service';
import { Fornecedor } from 'src/app/core/models/fornecedor';
import { Produto } from 'src/app/core/models/produto';
import { ItemCompra } from 'src/app/core/models/item-compra';

@Component({
  selector: 'app-cadastrar-compra',
  templateUrl: './cadastrar-compra.component.html',
  styleUrls: ['./cadastrar-compra.component.scss'],
})
export class CadastrarCompraComponent implements OnInit {
  public compra: FormGroup;
  public fornecedores: Fornecedor[] = [];
  public produtos: Produto[] = [];

  get compraItens() {
    return this.compra.get('compraItens') as FormArray;
  }

  constructor(
    fornecedorService: FornecedorService,
    produtoService: ProdutoService,
    private compraService: CompraService,
    private router: Router,
    private fb: FormBuilder
  ) {
    fornecedorService.getTodosFornecedores().subscribe((fornecedores) => {
      this.fornecedores = fornecedores;
    });
    produtoService.getTodosProdutos().subscribe((produtos) => {
      this.produtos = produtos;
    });
  }

  ngOnInit() {
    this.compra = this.fb.group({
      fornecedor: [''],
      compraItens: this.fb.array([
        this.fb.group({
          produto: [''],
          quantCompra: [''],
        }),
      ]),
    });
  }

  public cadastrarCompra() {
    const compra = this.getCompraValue();

    this.compraService.cadastrarCompra(compra).subscribe();

    this.goBack();
  }

  private getCompraValue(): Compra {
    const compra = this.compra.value;
    compra.fornecedor = this.fornecedores.find(
      (fornecedor) => fornecedor.nome === compra.fornecedor
    );

    compra.compraItens = this.setItemCompra(this.compraItens);

    compra.dataCompra = new Date();

    return compra;
  }

  private setItemCompra(compraItens): ItemCompra[] {
    const itemCompras: ItemCompra[] = [];

    compraItens.controls.forEach((itemCompra, i) => {
      const nomeProduto = itemCompra.get('produto').value;

      itemCompras[i] = itemCompra.value;
      itemCompras[i].produto = this.produtos.find(
        (produto) => nomeProduto === produto.nome
      );
    });

    return itemCompras.map((itemCompra) => {
      return {...itemCompra, valorCompra: parseFloat((itemCompra.produto.precoUnitario * itemCompra.quantCompra).toFixed(2))};
    });
  }

  public goBack() {
    this.router.navigate(['/']);
  }

  public addItemCompra() {
    this.compraItens.push(
      this.fb.group({
        produto: [''],
        quantCompra: [''],
      })
    );
  }
}
