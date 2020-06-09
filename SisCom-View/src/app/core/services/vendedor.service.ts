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

  public deletarVendedor(nome: string) {
    return this.http.delete('http://localhost:8080/vendedor/' + nome);
  }

  public getEstatistica(dataInicio: Date, dataFim: Date) {
    return this.http.get<any[]>(
      'http://localhost:8080/vendas/vendedor/data-inicio/' +
        new Date(dataInicio) +
        '/data-fim/' +
        new Date(dataFim)
    );
  }
}
