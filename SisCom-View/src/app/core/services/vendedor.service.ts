import { Injectable } from '@angular/core';
import { Vendedor } from '../models/vendedor';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class VendedorService {

  constructor(private http: HttpClient) { }

  public cadastrarVendedor(vendedor: Vendedor) {
    return this.http.post('http://localhost:8080/vendedor', vendedor);
  }

  public getTodosVendedores() {
    return this.http.get<Vendedor[]>('http://localhost:8080/vendedores');
  }
}
