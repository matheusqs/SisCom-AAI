import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { Router } from '@angular/router';
import { FornecedorService } from 'src/app/core/services/fornecedor.service';
import { Fornecedor } from 'src/app/core/models/fornecedor';

@Component({
  selector: 'app-cadastrar-fornecedor',
  templateUrl: './cadastrar-fornecedor.component.html',
  styleUrls: ['./cadastrar-fornecedor.component.scss']
})
export class CadastrarFornecedorComponent implements OnInit {

  public fornecedor: FormGroup;

  constructor(
    private fornecedorService: FornecedorService,
    private router: Router
  ) {}

  ngOnInit() {
    this.fornecedor = new FormGroup({
      nome: new FormControl(''),
      telefone: new FormControl(''),
      email: new FormControl(''),
      cnpj: new FormControl(''),
      nomeContato: new FormControl(''),
    });
  }

  public cadastrarFornecedor() {
    const fornecedor = this.fornecedor.value as Fornecedor;
    fornecedor.dataCad = new Date();

    this.fornecedorService
      .cadastrarFornecedor(fornecedor)
      .subscribe((response) => {
        console.log(response);
      });

    this.goBack();
  }

  public goBack() {
    this.router.navigate(['/']);
  }

}
