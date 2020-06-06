import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { Router } from '@angular/router';
import { Vendedor } from 'src/app/core/models/vendedor';
import { Cliente } from 'src/app/core/models/cliente';
import { ClienteService } from 'src/app/core/services/cliente.service';

@Component({
  selector: 'app-cadastrar-cliente',
  templateUrl: './cadastrar-cliente.component.html',
  styleUrls: ['./cadastrar-cliente.component.scss']
})
export class CadastrarClienteComponent implements OnInit {
  public cliente: FormGroup;

  constructor(
    private clienteService: ClienteService,
    private router: Router
  ) {}

  ngOnInit() {
    this.cliente = new FormGroup({
      nome: new FormControl(''),
      telefone: new FormControl(''),
      email: new FormControl(''),
      cpf: new FormControl(''),
      limiteCredito: new FormControl(''),
    });
  }

  public cadastrarCliente() {
    this.clienteService
      .cadastrarCliente(this.cliente.value as Cliente)
      .subscribe((response) => {
        console.log(response);
      });

    this.goBack();
  }

  public goBack() {
    this.router.navigate(['/']);
  }
}
