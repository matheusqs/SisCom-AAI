import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Produto } from '../models/produto';

@Injectable({
  providedIn: 'root'
})
export class ProdutoService {

  constructor(private http: HttpClient) { }

  public cadastrarProduto(produto: Produto) {
    return this.http.post('http://localhost:8080/produto', produto);
  }

  public getTodosProdutos() {
    return this.http.get<Produto[]>('http://localhost:8080/produtos');
  }

  public deletarProduto(nome: string) {
    return this.http.delete('http://localhost:8080/produto/' + nome);
  }
}
