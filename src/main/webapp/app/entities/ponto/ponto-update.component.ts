import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';

import { IPonto } from 'app/shared/model/ponto.model';
import { PontoService } from './ponto.service';

@Component({
    selector: 'jhi-ponto-update',
    templateUrl: './ponto-update.component.html'
})
export class PontoUpdateComponent implements OnInit {
    ponto: IPonto;
    isSaving: boolean;
    validadeDp: any;

    constructor(private pontoService: PontoService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ ponto }) => {
            this.ponto = ponto;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.ponto.id !== undefined) {
            this.subscribeToSaveResponse(this.pontoService.update(this.ponto));
        } else {
            this.subscribeToSaveResponse(this.pontoService.create(this.ponto));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPonto>>) {
        result.subscribe((res: HttpResponse<IPonto>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
