import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FornecedorService } from 'src/app/core/services/fornecedor.service';

@Component({
  selector: 'app-listar-fornecedor',
  templateUrl: './listar-fornecedor.component.html',
  styleUrls: ['./listar-fornecedor.component.scss']
})
export class ListarFornecedorComponent implements OnInit {

  displayedColumns: string[] = [
    'nome',
    'nomeContato',
    'email',
    'cnpj',
    'telefone',
    'dataCad',
    'deleteAction',
  ];
  dataSource = [];

  constructor(
    private fornecedorService: FornecedorService,
    private router: Router
  ) {}

  public ngOnInit() {
    this.fornecedorService.getTodosFornecedores().subscribe((fornecedores) => {
      this.dataSource = fornecedores;
    });
  }

  public goBack() {
    this.router.navigate(['/']);
  }

  public deleteFornecedor(fornecedor) {
    this.fornecedorService.deletarFornecedor(fornecedor.nome).subscribe(() => {
      this.dataSource = this.dataSource.filter((pessoa) => pessoa.nome !== fornecedor.nome);
    });
  }
}
