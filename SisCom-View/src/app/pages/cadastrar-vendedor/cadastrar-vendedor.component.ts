import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { VendedorService } from 'src/app/core/services/vendedor.service';
import { Vendedor } from 'src/app/core/models/vendedor';

@Component({
  selector: 'app-cadastrar-vendedor',
  templateUrl: './cadastrar-vendedor.component.html',
  styleUrls: ['./cadastrar-vendedor.component.scss'],
})
export class CadastrarVendedorComponent implements OnInit {
  public vendedor: FormGroup;

  constructor(private vendedorService: VendedorService) {
    this.vendedor = new FormGroup({
      nome: new FormControl(''),
      telefone: new FormControl(''),
      email: new FormControl(''),
      cpf: new FormControl(''),
      metaMensal: new FormControl(''),
    });
  }

  ngOnInit() {}

  public cadastrarVendedor() {
    this.vendedorService
      .cadastrarVendedor(this.vendedor.value as Vendedor)
      .subscribe((response) => {
        console.log(response);
      });
  }
}
