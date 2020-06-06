import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ClienteService } from 'src/app/core/services/cliente.service';

@Component({
  selector: 'app-listar-cliente',
  templateUrl: './listar-cliente.component.html',
  styleUrls: ['./listar-cliente.component.scss']
})
export class ListarClienteComponent implements OnInit {

  displayedColumns: string[] = [
    'position',
    'nome',
    'limiteCredito',
    'email',
    'cpf',
    'telefone',
  ];
  dataSource = [];

  constructor(
    private clienteService: ClienteService,
    private router: Router
  ) {}

  public ngOnInit() {
    this.clienteService.getTodosClientes().subscribe((clientes) => {
      this.dataSource = clientes.map((cliente, i) => {
        return { position: i + 1, ...cliente };
      });
    });
  }

  public goBack() {
    this.router.navigate(['/']);
  }

}
