import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormGroup, FormControl } from '@angular/forms';
import { ProdutoService } from 'src/app/core/services/produto.service';
import { Produto } from 'src/app/core/models/produto';

@Component({
  selector: 'app-cadastrar-produto',
  templateUrl: './cadastrar-produto.component.html',
  styleUrls: ['./cadastrar-produto.component.scss'],
})
export class CadastrarProdutoComponent implements OnInit {
  public produto: FormGroup;

  constructor(private produtoService: ProdutoService, private router: Router) {}

  ngOnInit() {
    this.produto = new FormGroup({
      nome: new FormControl(''),
      precoUnitario: new FormControl(''),
      estoque: new FormControl(''),
      estoqueMinimo: new FormControl(''),
    });
  }

  public cadastrarProduto() {
    const produto = this.produto.value as Produto;
    produto.dataCad = new Date();

    this.produtoService.cadastrarProduto(produto).subscribe();
    this.goBack();
  }

  public goBack() {
    this.router.navigate(['/']);
  }
}
