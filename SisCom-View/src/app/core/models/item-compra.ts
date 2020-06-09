import { Produto } from './produto';

export interface ItemCompra {
  id?: number;
  produto: Produto;
  quantCompra: number;
  valorCompra: number;
}
