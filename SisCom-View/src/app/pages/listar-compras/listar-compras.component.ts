import { Component, OnInit } from '@angular/core';
import { ClienteService } from 'src/app/core/services/cliente.service';
import { Router } from '@angular/router';
import { CompraService } from 'src/app/core/services/compra.service';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Fornecedor } from 'src/app/core/models/fornecedor';
import { FornecedorService } from 'src/app/core/services/fornecedor.service';

@Component({
  selector: 'app-listar-compras',
  templateUrl: './listar-compras.component.html',
  styleUrls: ['./listar-compras.component.scss']
})
export class ListarComprasComponent implements OnInit {
  displayedColumns: string[] = [
    'fornecedor',
    'dataCompra',
    'deleteAction'
  ];
  dataSource = [];
  filter: FormGroup;
  public fornecedores: Fornecedor[] = [];

  constructor(
    fornecedorService: FornecedorService,
    private compraService: CompraService,
    private router: Router,
    private fb: FormBuilder
  ) {
    this.filter =  this.fb.group({
      fornecedor: [''],
      dataInicio: [''],
      dataFim: [''],
    });

    fornecedorService.getTodosFornecedores().subscribe((fornecedores) => {
      this.fornecedores = fornecedores;
    });
  }

  public ngOnInit() {
  }

  public filtrar() {
    const nome = this.filter.controls.fornecedor.value;
    const dataInicio = this.filter.controls.dataInicio.value;
    const dataFim = this.filter.controls.dataFim.value;

    this.compraService.getTodosCompras(nome, dataInicio, dataFim).subscribe((compras) => {
      this.dataSource = compras;
    });
  }

  public goBack() {
    this.router.navigate(['/']);
  }

  public deleteCompra(compra) {
    this.compraService.deleteCompra(compra.fornecedor.nome, compra.dataCompra).subscribe(() => {
      this.dataSource = this.dataSource.filter((compraFilter) => compraFilter !== compra);
    });
  }
}
