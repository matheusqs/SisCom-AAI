import { Cliente } from './cliente';
import { Vendedor } from './vendedor';
import { ItemVenda } from './item-venda';

export interface Venda {
  numVenda: number;
  cliente: Cliente;
  vendedor: Vendedor;
  vendaItens: ItemVenda[];
  formaPagto: number;
  dataVenda: Date;
}
