import { Component, OnInit } from '@angular/core';
import { VendedorService } from 'src/app/core/services/vendedor.service';
import { Vendedor } from 'src/app/core/models/vendedor';
import { Router } from '@angular/router';

@Component({
  selector: 'app-listar-vendedor',
  templateUrl: './listar-vendedor.component.html',
  styleUrls: ['./listar-vendedor.component.scss'],
})
export class ListarVendedorComponent implements OnInit {
  displayedColumns: string[] = [
    'nome',
    'meta',
    'email',
    'cpf',
    'telefone',
    'dataCad',
    'deleteAction',
  ];
  dataSource: Vendedor[] = [];

  constructor(
    private vendedorService: VendedorService,
    private router: Router
  ) {}

  public ngOnInit() {
    this.vendedorService.getTodosVendedores().subscribe((vendedores) => {
      this.dataSource = vendedores;
    });
  }

  public goBack() {
    this.router.navigate(['/']);
  }

  public deleteVendedor(vendedor) {
    this.vendedorService.deletarVendedor(vendedor.nome).subscribe(() => {
      this.dataSource = this.dataSource.filter((pessoa) => pessoa.nome !== vendedor.nome);
    });
  }
}
