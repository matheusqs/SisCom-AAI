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
    'nome',
    'limiteCredito',
    'email',
    'cpf',
    'dataCad',
    'telefone',
    'deleteAction'
  ];
  dataSource = [];

  constructor(
    private clienteService: ClienteService,
    private router: Router
  ) {}

  public ngOnInit() {
    this.clienteService.getTodosClientes().subscribe((clientes) => {
      this.dataSource = clientes;
    });
  }

  public goBack() {
    this.router.navigate(['/']);
  }

  public deleteCliente(cliente) {
    this.clienteService.deletarCliente(cliente.nome).subscribe(() => {
      this.dataSource = this.dataSource.filter((pessoa) => pessoa.nome !== cliente.nome);
    });
  }
}
