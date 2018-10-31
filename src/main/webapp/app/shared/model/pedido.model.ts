import { Moment } from 'moment';
import { IProduto } from 'app/shared/model//produto.model';
import { IUsuario } from 'app/shared/model//usuario.model';

export interface IPedido {
    id?: string;
    data?: Moment;
    municipio?: string;
    area?: number;
    geometria?: string;
    produtos?: IProduto[];
    pedido?: IUsuario;
}

export class Pedido implements IPedido {
    constructor(
        public id?: string,
        public data?: Moment,
        public municipio?: string,
        public area?: number,
        public geometria?: string,
        public produtos?: IProduto[],
        public pedido?: IUsuario
    ) {}
}
