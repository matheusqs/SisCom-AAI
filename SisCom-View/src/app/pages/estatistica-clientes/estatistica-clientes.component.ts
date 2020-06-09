import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { VendedorService } from 'src/app/core/services/vendedor.service';
import { Router } from '@angular/router';
import { ClienteService } from 'src/app/core/services/cliente.service';

@Component({
  selector: 'app-estatistica-clientes',
  templateUrl: './estatistica-clientes.component.html',
  styleUrls: ['./estatistica-clientes.component.scss']
})
export class EstatisticaClientesComponent implements OnInit {
  displayedColumns: string[] = [
    'cliente',
    'qtdVendas',
    'vlrTotal',
  ];
  dataSource = [];
  filter: FormGroup;

  constructor(
    private clienteService: ClienteService,
    private router: Router,
    private fb: FormBuilder
  ) {
    this.filter =  this.fb.group({
      dataInicio: [''],
      dataFim: [''],
    });
  }

  public ngOnInit() {
  }

  public filtrar() {
    const dataInicio = this.filter.controls.dataInicio.value;
    const dataFim = this.filter.controls.dataFim.value;

    this.clienteService.getEstatistica(dataInicio, dataFim).subscribe((estatisticas) => {
      this.dataSource = estatisticas;
    });
  }

  public goBack() {
    this.router.navigate(['/']);
  }
}
