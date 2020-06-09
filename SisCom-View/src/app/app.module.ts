import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { MatButtonModule } from '@angular/material/button';
import { CadastrarVendedorComponent } from './pages/cadastrar-vendedor/cadastrar-vendedor.component';
import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { ListarVendedorComponent } from './pages/listar-vendedor/listar-vendedor.component';
import { MatTableModule } from '@angular/material/table';
import { PageTemplateComponent } from './shared/components/page-template/page-template.component';
import { MatDividerModule } from '@angular/material/divider';
import { MatListModule } from '@angular/material/list';
import { CadastrarFornecedorComponent } from './pages/cadastrar-fornecedor/cadastrar-fornecedor.component';
import { CadastrarClienteComponent } from './pages/cadastrar-cliente/cadastrar-cliente.component';
import { ListarClienteComponent } from './pages/listar-cliente/listar-cliente.component';
import { ListarFornecedorComponent } from './pages/listar-fornecedor/listar-fornecedor.component';
import { CadastrarProdutoComponent } from './pages/cadastrar-produto/cadastrar-produto.component';
import { ListarProdutoComponent } from './pages/listar-produto/listar-produto.component';
import { CadastrarCompraComponent } from './pages/cadastrar-compra/cadastrar-compra.component';
import { CadastrarVendaComponent } from './pages/cadastrar-venda/cadastrar-venda.component';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import {MatSelectModule} from '@angular/material/select';
import {MatIconModule} from '@angular/material/icon';
import { ListarVendasComponent } from './pages/listar-vendas/listar-vendas.component';
import { ListarComprasComponent } from './pages/listar-compras/listar-compras.component';
import {MatDatepickerModule} from '@angular/material/datepicker';
import { MatMomentDateModule } from '@angular/material-moment-adapter';
import { EstatisticaClientesComponent } from './pages/estatistica-clientes/estatistica-clientes.component';
import { EstatisticaVendedoresComponent } from './pages/estatistica-vendedores/estatistica-vendedores.component';
import { EstatisticaFornecedoresComponent } from './pages/estatistica-fornecedores/estatistica-fornecedores.component';
import {MatSlideToggleModule} from '@angular/material/slide-toggle';

@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent,
    CadastrarVendedorComponent,
    ListarVendedorComponent,
    PageTemplateComponent,
    CadastrarFornecedorComponent,
    CadastrarClienteComponent,
    ListarClienteComponent,
    ListarFornecedorComponent,
    CadastrarProdutoComponent,
    ListarProdutoComponent,
    CadastrarCompraComponent,
    CadastrarVendaComponent,
    ListarVendasComponent,
    ListarComprasComponent,
    EstatisticaClientesComponent,
    EstatisticaVendedoresComponent,
    EstatisticaFornecedoresComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    HttpClientModule,
    ReactiveFormsModule,
    MatTableModule,
    MatListModule,
    MatDividerModule,
    MatAutocompleteModule,
    MatSelectModule,
    MatIconModule,
    MatDatepickerModule,
    MatMomentDateModule,
    MatSlideToggleModule,
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
