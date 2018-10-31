import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IProduto } from 'app/shared/model/produto.model';
import { Principal } from 'app/core';
import { ProdutoService } from './produto.service';

@Component({
    selector: 'jhi-produto',
    templateUrl: './produto.component.html'
})
export class ProdutoComponent implements OnInit, OnDestroy {
    produtos: IProduto[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private produtoService: ProdutoService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.produtoService.query().subscribe(
            (res: HttpResponse<IProduto[]>) => {
                this.produtos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInProdutos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IProduto) {
        return item.id;
    }

    registerChangeInProdutos() {
        this.eventSubscriber = this.eventManager.subscribe('produtoListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
