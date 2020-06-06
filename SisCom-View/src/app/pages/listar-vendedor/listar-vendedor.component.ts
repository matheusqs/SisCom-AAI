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
    'position',
    'nome',
    'meta',
    'email',
    'cpf',
    'telefone',
  ];
  dataSource = [];

  constructor(
    private vendedorService: VendedorService,
    private router: Router
  ) {}

  public ngOnInit() {
    this.vendedorService.getTodosVendedores().subscribe((vendedores) => {
      this.dataSource = vendedores.map((vendedor, i) => {
        return { position: i + 1, ...vendedor };
      });
    });
  }

  public goBack() {
    this.router.navigate(['/']);
  }
}
