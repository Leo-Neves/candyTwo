import { IProduto } from 'app/shared/model//produto.model';

export interface ICategoria {
    id?: string;
    nome?: string;
    produtos?: IProduto[];
}

export class Categoria implements ICategoria {
    constructor(public id?: string, public nome?: string, public produtos?: IProduto[]) {}
}
