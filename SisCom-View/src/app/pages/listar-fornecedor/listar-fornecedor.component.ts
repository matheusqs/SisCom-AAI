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
    'position',
    'nome',
    'nomeContato',
    'email',
    'cnpj',
    'telefone',
  ];
  dataSource = [];

  constructor(
    private fornecedorService: FornecedorService,
    private router: Router
  ) {}

  public ngOnInit() {
    this.fornecedorService.getTodosFornecedores().subscribe((fornecedores) => {
      this.dataSource = fornecedores.map((fornecedor, i) => {
        return { position: i + 1, ...fornecedor };
      });
    });
  }

  public goBack() {
    this.router.navigate(['/']);
  }
}
