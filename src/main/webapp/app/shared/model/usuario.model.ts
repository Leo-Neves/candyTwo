import { IPedido } from 'app/shared/model//pedido.model';
import { IPonto } from 'app/shared/model//ponto.model';

export interface IUsuario {
    id?: string;
    nome?: string;
    email?: string;
    token?: string;
    pedidos?: IPedido[];
    ponto?: IPonto;
}

export class Usuario implements IUsuario {
    constructor(
        public id?: string,
        public nome?: string,
        public email?: string,
        public token?: string,
        public pedidos?: IPedido[],
        public ponto?: IPonto
    ) {}
}
