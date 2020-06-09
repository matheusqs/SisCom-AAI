import { Produto } from './produto';

export interface ItemVenda {
  id?: number;
  produto: Produto;
  quantVenda: number;
  valorVenda: number;
}
