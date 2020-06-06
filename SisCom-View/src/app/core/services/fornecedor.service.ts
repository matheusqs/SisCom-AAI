import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Fornecedor } from '../models/fornecedor';

@Injectable({
  providedIn: 'root'
})
export class FornecedorService {

  constructor(private http: HttpClient) { }

  public cadastrarFornecedor(fornecedor: Fornecedor) {
    return this.http.post('http://localhost:8080/fornecedor', fornecedor);
  }

  public getTodosFornecedores() {
    return this.http.get<Fornecedor[]>('http://localhost:8080/fornecedores');
  }
}
