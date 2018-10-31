import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPonto } from 'app/shared/model/ponto.model';
import { Principal } from 'app/core';
import { PontoService } from './ponto.service';

@Component({
    selector: 'jhi-ponto',
    templateUrl: './ponto.component.html'
})
export class PontoComponent implements OnInit, OnDestroy {
    pontos: IPonto[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private pontoService: PontoService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.pontoService.query().subscribe(
            (res: HttpResponse<IPonto[]>) => {
                this.pontos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInPontos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IPonto) {
        return item.id;
    }

    registerChangeInPontos() {
        this.eventSubscriber = this.eventManager.subscribe('pontoListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
