import { Pessoa } from './pessoa';

export interface Vendedor extends Pessoa {
  cpf: string;
  metaMensal: number;
}
