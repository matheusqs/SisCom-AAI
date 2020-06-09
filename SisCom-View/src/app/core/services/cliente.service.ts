import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Cliente } from '../models/cliente';

@Injectable({
  providedIn: 'root'
})
export class ClienteService {

  constructor(private http: HttpClient) { }

  public cadastrarCliente(cliente: Cliente) {
    return this.http.post('http://localhost:8080/cliente', cliente);
  }

  public getTodosClientes() {
    return this.http.get<Cliente[]>('http://localhost:8080/clientes');
  }

  public deletarCliente(cliente: string) {
    return this.http.delete('http://localhost:8080/cliente/' + cliente);
  }

  public getEstatistica(dataInicio: Date, dataFim: Date) {
    return this.http.get<any[]>(
      'http://localhost:8080/vendas/cliente/data-inicio/' +
        new Date(dataInicio) +
        '/data-fim/' +
        new Date(dataFim)
    );
  }
}
