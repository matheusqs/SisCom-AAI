import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { VendedorService } from 'src/app/core/services/vendedor.service';
import { Vendedor } from 'src/app/core/models/vendedor';
import { Router } from '@angular/router';

@Component({
  selector: 'app-cadastrar-vendedor',
  templateUrl: './cadastrar-vendedor.component.html',
  styleUrls: ['./cadastrar-vendedor.component.scss'],
})
export class CadastrarVendedorComponent implements OnInit {
  public vendedor: FormGroup;

  constructor(
    private vendedorService: VendedorService,
    private router: Router
  ) {}

  ngOnInit() {
    this.vendedor = new FormGroup({
      nome: new FormControl(''),
      telefone: new FormControl(''),
      email: new FormControl(''),
      cpf: new FormControl(''),
      metaMensal: new FormControl(''),
    });
  }

  public cadastrarVendedor() {
    const vendedor = this.vendedor.value as Vendedor;
    vendedor.dataCad = new Date();
    this.vendedorService
      .cadastrarVendedor(vendedor)
      .subscribe((response) => {
        console.log(response);
      });

    this.goBack();
  }

  public goBack() {
    this.router.navigate(['/']);
  }
}
