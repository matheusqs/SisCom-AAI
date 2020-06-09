import { Component, OnInit } from '@angular/core';
import { ProdutoService } from 'src/app/core/services/produto.service';
import { Router } from '@angular/router';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-listar-produto',
  templateUrl: './listar-produto.component.html',
  styleUrls: ['./listar-produto.component.scss']
})
export class ListarProdutoComponent implements OnInit {
  form: FormGroup;
  displayedColumns: string[] = [
    'nome',
    'estoque',
    'estoqueMinimo',
    'precoUnitario',
    'dataCad',
    'deleteAction'
  ];
  dataSource = [];
  dataSourceAll = [];
  dataSourceFilter = [];

  constructor(
    private produtoService: ProdutoService,
    private router: Router,
    fb: FormBuilder
  ) {
    this.form = fb.group({
      toggle: ['']
    });

    this.form.get('toggle').valueChanges.subscribe((value) => {
      this.dataSource = value ? this.dataSourceFilter : this.dataSourceAll;
    });
  }

  public ngOnInit() {
    this.produtoService.getTodosProdutos().subscribe((produtos) => {
      this.dataSource = produtos;
      this.dataSourceAll = this.dataSource;
      this.dataSourceFilter = produtos.filter((produto) => produto.estoque < produto.estoqueMinimo);
    });

  }

  public goBack() {
    this.router.navigate(['/']);
  }

  public deleteProduto(produto) {
    this.produtoService.deletarProduto(produto.nome).subscribe(() => {
      this.dataSource = this.dataSource.filter((produtoValue) => produtoValue.nome !== produto.nome);
    });
  }
}
