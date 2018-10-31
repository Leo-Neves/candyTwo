import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IProduto } from 'app/shared/model/produto.model';
import { ProdutoService } from './produto.service';
import { ICategoria } from 'app/shared/model/categoria.model';
import { CategoriaService } from 'app/entities/categoria';
import { IPedido } from 'app/shared/model/pedido.model';
import { PedidoService } from 'app/entities/pedido';

@Component({
    selector: 'jhi-produto-update',
    templateUrl: './produto-update.component.html'
})
export class ProdutoUpdateComponent implements OnInit {
    produto: IProduto;
    isSaving: boolean;

    categorias: ICategoria[];

    pedidos: IPedido[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private produtoService: ProdutoService,
        private categoriaService: CategoriaService,
        private pedidoService: PedidoService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ produto }) => {
            this.produto = produto;
        });
        this.categoriaService.query().subscribe(
            (res: HttpResponse<ICategoria[]>) => {
                this.categorias = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.pedidoService.query().subscribe(
            (res: HttpResponse<IPedido[]>) => {
                this.pedidos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.produto.id !== undefined) {
            this.subscribeToSaveResponse(this.produtoService.update(this.produto));
        } else {
            this.subscribeToSaveResponse(this.produtoService.create(this.produto));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IProduto>>) {
        result.subscribe((res: HttpResponse<IProduto>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackCategoriaById(index: number, item: ICategoria) {
        return item.id;
    }

    trackPedidoById(index: number, item: IPedido) {
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
