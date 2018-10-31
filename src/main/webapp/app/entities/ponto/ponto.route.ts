import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Ponto } from 'app/shared/model/ponto.model';
import { PontoService } from './ponto.service';
import { PontoComponent } from './ponto.component';
import { PontoDetailComponent } from './ponto-detail.component';
import { PontoUpdateComponent } from './ponto-update.component';
import { PontoDeletePopupComponent } from './ponto-delete-dialog.component';
import { IPonto } from 'app/shared/model/ponto.model';

@Injectable({ providedIn: 'root' })
export class PontoResolve implements Resolve<IPonto> {
    constructor(private service: PontoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((ponto: HttpResponse<Ponto>) => ponto.body));
        }
        return of(new Ponto());
    }
}

export const pontoRoute: Routes = [
    {
        path: 'ponto',
        component: PontoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'candyShopApplicationApp.ponto.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'ponto/:id/view',
        component: PontoDetailComponent,
        resolve: {
            ponto: PontoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'candyShopApplicationApp.ponto.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'ponto/new',
        component: PontoUpdateComponent,
        resolve: {
            ponto: PontoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'candyShopApplicationApp.ponto.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'ponto/:id/edit',
        component: PontoUpdateComponent,
        resolve: {
            ponto: PontoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'candyShopApplicationApp.ponto.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const pontoPopupRoute: Routes = [
    {
        path: 'ponto/:id/delete',
        component: PontoDeletePopupComponent,
        resolve: {
            ponto: PontoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'candyShopApplicationApp.ponto.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
