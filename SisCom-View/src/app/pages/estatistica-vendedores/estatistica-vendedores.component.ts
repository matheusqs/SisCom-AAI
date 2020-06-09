import { Component, OnInit } from '@angular/core';
import { FornecedorService } from 'src/app/core/services/fornecedor.service';
import { FormGroup, FormBuilder } from '@angular/forms';
import { Fornecedor } from 'src/app/core/models/fornecedor';
import { Router } from '@angular/router';
import { VendedorService } from 'src/app/core/services/vendedor.service';

@Component({
  selector: 'app-estatistica-vendedores',
  templateUrl: './estatistica-vendedores.component.html',
  styleUrls: ['./estatistica-vendedores.component.scss']
})
export class EstatisticaVendedoresComponent implements OnInit {
  displayedColumns: string[] = [
    'vendedor',
    'qtdVendas',
    'vlrTotal',
  ];
  dataSource = [];
  filter: FormGroup;

  constructor(
    private vendedorService: VendedorService,
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

    this.vendedorService.getEstatistica(dataInicio, dataFim).subscribe((estatisticas) => {
      this.dataSource = estatisticas;
    });
  }

  public goBack() {
    this.router.navigate(['/']);
  }
}
