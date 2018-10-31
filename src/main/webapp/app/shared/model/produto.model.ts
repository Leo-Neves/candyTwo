import { ICategoria } from 'app/shared/model//categoria.model';
import { IPedido } from 'app/shared/model//pedido.model';

export interface IProduto {
    id?: string;
    nome?: string;
    preco?: string;
    estoque?: number;
    categoria?: ICategoria;
    pedidos?: IPedido[];
}

export class Produto implements IProduto {
    constructor(
        public id?: string,
        public nome?: string,
        public preco?: string,
        public estoque?: number,
        public categoria?: ICategoria,
        public pedidos?: IPedido[]
    ) {}
}
