import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Pedido } from 'app/shared/model/pedido.model';
import { PedidoService } from './pedido.service';
import { PedidoComponent } from './pedido.component';
import { PedidoDetailComponent } from './pedido-detail.component';
import { PedidoUpdateComponent } from './pedido-update.component';
import { PedidoDeletePopupComponent } from './pedido-delete-dialog.component';
import { IPedido } from 'app/shared/model/pedido.model';

@Injectable({ providedIn: 'root' })
export class PedidoResolve implements Resolve<IPedido> {
    constructor(private service: PedidoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((pedido: HttpResponse<Pedido>) => pedido.body));
        }
        return of(new Pedido());
    }
}

export const pedidoRoute: Routes = [
    {
        path: 'pedido',
        component: PedidoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'candyShopApplicationApp.pedido.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'pedido/:id/view',
        component: PedidoDetailComponent,
        resolve: {
            pedido: PedidoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'candyShopApplicationApp.pedido.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'pedido/new',
        component: PedidoUpdateComponent,
        resolve: {
            pedido: PedidoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'candyShopApplicationApp.pedido.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'pedido/:id/edit',
        component: PedidoUpdateComponent,
        resolve: {
            pedido: PedidoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'candyShopApplicationApp.pedido.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const pedidoPopupRoute: Routes = [
    {
        path: 'pedido/:id/delete',
        component: PedidoDeletePopupComponent,
        resolve: {
            pedido: PedidoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'candyShopApplicationApp.pedido.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
