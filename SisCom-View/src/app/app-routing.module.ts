import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { CadastrarVendedorComponent } from './pages/cadastrar-vendedor/cadastrar-vendedor.component';
import { ListarVendedorComponent } from './pages/listar-vendedor/listar-vendedor.component';
import { CadastrarFornecedorComponent } from './pages/cadastrar-fornecedor/cadastrar-fornecedor.component';
import { CadastrarClienteComponent } from './pages/cadastrar-cliente/cadastrar-cliente.component';
import { ListarFornecedorComponent } from './pages/listar-fornecedor/listar-fornecedor.component';
import { ListarClienteComponent } from './pages/listar-cliente/listar-cliente.component';
import { CadastrarProdutoComponent } from './pages/cadastrar-produto/cadastrar-produto.component';
import { ListarProdutoComponent } from './pages/listar-produto/listar-produto.component';
import { CadastrarCompraComponent } from './pages/cadastrar-compra/cadastrar-compra.component';
import { CadastrarVendaComponent } from './pages/cadastrar-venda/cadastrar-venda.component';
import { ListarVendasComponent } from './pages/listar-vendas/listar-vendas.component';
import { ListarComprasComponent } from './pages/listar-compras/listar-compras.component';
import { EstatisticaClientesComponent } from './pages/estatistica-clientes/estatistica-clientes.component';
import { EstatisticaVendedoresComponent } from './pages/estatistica-vendedores/estatistica-vendedores.component';
import { EstatisticaFornecedoresComponent } from './pages/estatistica-fornecedores/estatistica-fornecedores.component';

const routes: Routes = [
  {
    path: '',
    component: DashboardComponent,
  },
  {
    path: 'estatistica/clientes',
    component: EstatisticaClientesComponent,
  },
  {
    path: 'estatistica/vendedores',
    component: EstatisticaVendedoresComponent,
  },
  {
    path: 'estatistica/fornecedores',
    component: EstatisticaFornecedoresComponent,
  },
  {
    path: 'listar/vendas',
    component: ListarVendasComponent,
  },
  {
    path: 'listar/compras',
    component: ListarComprasComponent,
  },
  {
    path: 'cadastrar/venda',
    component: CadastrarVendaComponent,
  },
  {
    path: 'cadastrar/compra',
    component: CadastrarCompraComponent,
  },
  {
    path: 'cadastrar/produto',
    component: CadastrarProdutoComponent,
  },
  {
    path: 'listar/produtos',
    component: ListarProdutoComponent,
  },
  {
    path: 'cadastrar/vendedor',
    component: CadastrarVendedorComponent,
  },
  {
    path: 'listar/vendedores',
    component: ListarVendedorComponent,
  },
  {
    path: 'cadastrar/fornecedor',
    component: CadastrarFornecedorComponent,
  },
  {
    path: 'listar/fornecedores',
    component: ListarFornecedorComponent,
  },
  {
    path: 'cadastrar/cliente',
    component: CadastrarClienteComponent,
  },
  {
    path: 'listar/clientes',
    component: ListarClienteComponent,
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
