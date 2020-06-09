import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Fornecedor } from '../models/fornecedor';

@Injectable({
  providedIn: 'root',
})
export class FornecedorService {
  constructor(private http: HttpClient) {}

  public cadastrarFornecedor(fornecedor: Fornecedor) {
    return this.http.post('http://localhost:8080/fornecedor', fornecedor);
  }

  public getTodosFornecedores() {
    return this.http.get<Fornecedor[]>('http://localhost:8080/fornecedores');
  }

  public deletarFornecedor(nome: string) {
    return this.http.delete('http://localhost:8080/fornecedor/' + nome);
  }

  public getEstatistica(dataInicio: Date, dataFim: Date) {
    return this.http.get<any[]>(
      'http://localhost:8080/compras/data-inicio/' +
        new Date(dataInicio) +
        '/data-fim/' +
        new Date(dataFim)
    );
  }
}
