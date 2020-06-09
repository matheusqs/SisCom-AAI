import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Venda } from "../models/venda";

@Injectable({
  providedIn: "root",
})
export class VendaService {
  constructor(private http: HttpClient) {}

  public cadastrarVenda(venda: Venda) {
    return this.http.post("http://localhost:8080/venda", venda);
  }

  public getTodosVendas(
    nomeCliente: string,
    nomeVendedor: string,
    dataInicio: Date,
    dataFim: Date
  ) {
    return this.http.get<Venda[]>(
      "http://localhost:8080/vendas/cliente/" +
        nomeCliente +
        "/vendedor/" +
        nomeVendedor +
        "/data-inicio/" +
        new Date(dataInicio) +
        "/data-fim/" +
        new Date(dataFim)
    );
  }

  public deleteVenda(
    nomeCliente: string,
    nomeVendedor: string,
    dataVenda: Date
  ) {
    return this.http.delete(
      "http://localhost:8080/venda/cliente/" +
        nomeCliente +
        "/vendedor/" +
        nomeVendedor +
        "/data/" +
        new Date(dataVenda)
    );
  }
}
