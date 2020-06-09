import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { Fornecedor } from 'src/app/core/models/fornecedor';
import { FornecedorService } from 'src/app/core/services/fornecedor.service';
import { CompraService } from 'src/app/core/services/compra.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-estatistica-fornecedores',
  templateUrl: './estatistica-fornecedores.component.html',
  styleUrls: ['./estatistica-fornecedores.component.scss']
})
export class EstatisticaFornecedoresComponent implements OnInit {
  displayedColumns: string[] = [
    'fornecedor',
    'qtdCompras',
    'vlrTotal',
  ];
  dataSource = [];
  filter: FormGroup;

  constructor(
    private fornecedorService: FornecedorService,
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

    this.fornecedorService.getEstatistica(dataInicio, dataFim).subscribe((estatisticas) => {
      this.dataSource = estatisticas;
    });
  }

  public goBack() {
    this.router.navigate(['/']);
  }
}
