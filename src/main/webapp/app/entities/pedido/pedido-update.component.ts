import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';

import { IPedido } from 'app/shared/model/pedido.model';
import { PedidoService } from './pedido.service';
import { IProduto } from 'app/shared/model/produto.model';
import { ProdutoService } from 'app/entities/produto';
import { IUsuario } from 'app/shared/model/usuario.model';
import { UsuarioService } from 'app/entities/usuario';

@Component({
    selector: 'jhi-pedido-update',
    templateUrl: './pedido-update.component.html'
})
export class PedidoUpdateComponent implements OnInit {
    pedido: IPedido;
    isSaving: boolean;

    produtos: IProduto[];

    usuarios: IUsuario[];
    dataDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private pedidoService: PedidoService,
        private produtoService: ProdutoService,
        private usuarioService: UsuarioService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ pedido }) => {
            this.pedido = pedido;
        });
        this.produtoService.query().subscribe(
            (res: HttpResponse<IProduto[]>) => {
                this.produtos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.usuarioService.query().subscribe(
            (res: HttpResponse<IUsuario[]>) => {
                this.usuarios = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.pedido.id !== undefined) {
            this.subscribeToSaveResponse(this.pedidoService.update(this.pedido));
        } else {
            this.subscribeToSaveResponse(this.pedidoService.create(this.pedido));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPedido>>) {
        result.subscribe((res: HttpResponse<IPedido>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackProdutoById(index: number, item: IProduto) {
        return item.id;
    }

    trackUsuarioById(index: number, item: IUsuario) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}
