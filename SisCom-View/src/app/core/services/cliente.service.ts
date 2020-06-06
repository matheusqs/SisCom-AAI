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
}
