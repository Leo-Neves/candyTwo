import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPonto } from 'app/shared/model/ponto.model';

@Component({
    selector: 'jhi-ponto-detail',
    templateUrl: './ponto-detail.component.html'
})
export class PontoDetailComponent implements OnInit {
    ponto: IPonto;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ ponto }) => {
            this.ponto = ponto;
        });
    }

    previousState() {
        window.history.back();
    }
}
