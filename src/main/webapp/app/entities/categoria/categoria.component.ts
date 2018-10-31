import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICategoria } from 'app/shared/model/categoria.model';
import { Principal } from 'app/core';
import { CategoriaService } from './categoria.service';

@Component({
    selector: 'jhi-categoria',
    templateUrl: './categoria.component.html'
})
export class CategoriaComponent implements OnInit, OnDestroy {
    categorias: ICategoria[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private categoriaService: CategoriaService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.categoriaService.query().subscribe(
            (res: HttpResponse<ICategoria[]>) => {
                this.categorias = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInCategorias();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ICategoria) {
        return item.id;
    }

    registerChangeInCategorias() {
        this.eventSubscriber = this.eventManager.subscribe('categoriaListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
