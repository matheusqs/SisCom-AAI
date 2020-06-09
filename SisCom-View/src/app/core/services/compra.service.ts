import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Compra } from '../models/compra';

@Injectable({
  providedIn: 'root',
})
export class CompraService {
  constructor(private http: HttpClient) {}

  public cadastrarCompra(compra: Compra) {
    return this.http.post('http://localhost:8080/compra', compra);
  }

  public getTodosCompras(nome: string, dataInicio: Date, dataFim: Date) {
    return this.http.get<Compra[]>(
      'http://localhost:8080/compras/fornecedor/' +
        nome +
        '/data-inicio/' +
        new Date(dataInicio) +
        '/data-fim/' +
        new Date(dataFim)
    );
  }

  public deleteCompra(nome: string, dataCompra: Date) {
    return this.http.delete(
      'http://localhost:8080/compra/fornecedor/' +
        nome +
        '/data/' +
        new Date(dataCompra)
    );
  }
}
