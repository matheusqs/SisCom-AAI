import { Pessoa } from './pessoa';

export interface Fornecedor extends Pessoa {
  cnpj: string;
  nomeContato: string;
}
