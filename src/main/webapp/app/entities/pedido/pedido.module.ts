import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CandyShopApplicationSharedModule } from 'app/shared';
import {
    PedidoComponent,
    PedidoDetailComponent,
    PedidoUpdateComponent,
    PedidoDeletePopupComponent,
    PedidoDeleteDialogComponent,
    pedidoRoute,
    pedidoPopupRoute
} from './';

const ENTITY_STATES = [...pedidoRoute, ...pedidoPopupRoute];

@NgModule({
    imports: [CandyShopApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [PedidoComponent, PedidoDetailComponent, PedidoUpdateComponent, PedidoDeleteDialogComponent, PedidoDeletePopupComponent],
    entryComponents: [PedidoComponent, PedidoUpdateComponent, PedidoDeleteDialogComponent, PedidoDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CandyShopApplicationPedidoModule {}
