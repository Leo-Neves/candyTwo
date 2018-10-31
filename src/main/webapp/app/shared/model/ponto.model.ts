import { Moment } from 'moment';
import { IUsuario } from 'app/shared/model//usuario.model';

export interface IPonto {
    id?: string;
    quantidade?: number;
    validade?: Moment;
    usuarios?: IUsuario[];
}

export class Ponto implements IPonto {
    constructor(public id?: string, public quantidade?: number, public validade?: Moment, public usuarios?: IUsuario[]) {}
}
