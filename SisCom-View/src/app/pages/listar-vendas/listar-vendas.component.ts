import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { Fornecedor } from 'src/app/core/models/fornecedor';
import { FornecedorService } from 'src/app/core/services/fornecedor.service';
import { CompraService } from 'src/app/core/services/compra.service';
import { Router } from '@angular/router';
import { ClienteService } from 'src/app/core/services/cliente.service';
import { VendedorService } from 'src/app/core/services/vendedor.service';
import { Cliente } from 'src/app/core/models/cliente';
import { Vendedor } from 'src/app/core/models/vendedor';
import { VendaService } from 'src/app/core/services/Venda.service';

@Component({
  selector: 'app-listar-vendas',
  templateUrl: './listar-vendas.component.html',
  styleUrls: ['./listar-vendas.component.scss'],
})
export class ListarVendasComponent implements OnInit {
  displayedColumns: string[] = [
    'vendedor',
    'cliente',
    'dataVenda',
    'deleteAction',
  ];
  dataSource = [];
  filter: FormGroup;
  public vendedores: Vendedor[] = [];
  public clientes: Cliente[] = [];

  constructor(
    clienteService: ClienteService,
    vendedorService: VendedorService,
    private vendaService: VendaService,
    private router: Router,
    private fb: FormBuilder
  ) {
    this.filter = this.fb.group({
      cliente: [''],
      vendedor: [''],
      dataInicio: [''],
      dataFim: [''],
    });

    clienteService.getTodosClientes().subscribe((clientes) => {
      this.clientes = clientes;
    });

    vendedorService.getTodosVendedores().subscribe((vendedores) => {
      this.vendedores = vendedores;
    });
  }

  public ngOnInit() {}

  public filtrar() {
    const nomeVendedor = this.filter.controls.vendedor.value;
    const nomeCliente = this.filter.controls.cliente.value;
    const dataInicio = this.filter.controls.dataInicio.value;
    const dataFim = this.filter.controls.dataFim.value;

    this.vendaService
      .getTodosVendas(nomeCliente, nomeVendedor, dataInicio, dataFim)
      .subscribe((vendas) => {
        this.dataSource = vendas;
      });
  }

  public goBack() {
    this.router.navigate(['/']);
  }

  public deleteCompra(venda) {
    this.vendaService
      .deleteVenda(venda.cliente.nome, venda.vendedor.nome, venda.dataVenda)
      .subscribe(() => {
        this.dataSource = this.dataSource.filter(
          (vendaFilter) => vendaFilter !== venda
        );
      });
  }
}
