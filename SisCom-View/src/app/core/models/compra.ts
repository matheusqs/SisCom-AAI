import { Fornecedor } from './fornecedor';
import { ItemCompra } from './item-compra';

export interface Compra {
    numCompra?: number;
    fornecedor: Fornecedor;
    compraItens: ItemCompra[];
    dataCompra: Date;
}
