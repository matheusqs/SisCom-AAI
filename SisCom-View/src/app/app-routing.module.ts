import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { CadastrarVendedorComponent } from './pages/cadastrar-vendedor/cadastrar-vendedor.component';
import { ListarVendedorComponent } from './pages/listar-vendedor/listar-vendedor.component';
import { CadastrarFornecedorComponent } from './pages/cadastrar-fornecedor/cadastrar-fornecedor.component';
import { CadastrarClienteComponent } from './pages/cadastrar-cliente/cadastrar-cliente.component';
import { ListarFornecedorComponent } from './pages/listar-fornecedor/listar-fornecedor.component';
import { ListarClienteComponent } from './pages/listar-cliente/listar-cliente.component';

const routes: Routes = [
  {
    path: '',
    component: DashboardComponent,
  },
  {
    path: 'cadastrar/vendedor',
    component: CadastrarVendedorComponent
  },
  {
    path: 'cadastrar/fornecedor',
    component: CadastrarFornecedorComponent
  },
  {
    path: 'cadastrar/cliente',
    component: CadastrarClienteComponent
  },
  {
    path: 'listar/vendedores',
    component: ListarVendedorComponent
  },
  {
    path: 'listar/fornecedores',
    component: ListarFornecedorComponent
  },
  {
    path: 'listar/clientes',
    component: ListarClienteComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
