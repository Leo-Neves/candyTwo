import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CandyShopApplicationSharedModule } from 'app/shared';
import {
    PontoComponent,
    PontoDetailComponent,
    PontoUpdateComponent,
    PontoDeletePopupComponent,
    PontoDeleteDialogComponent,
    pontoRoute,
    pontoPopupRoute
} from './';

const ENTITY_STATES = [...pontoRoute, ...pontoPopupRoute];

@NgModule({
    imports: [CandyShopApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [PontoComponent, PontoDetailComponent, PontoUpdateComponent, PontoDeleteDialogComponent, PontoDeletePopupComponent],
    entryComponents: [PontoComponent, PontoUpdateComponent, PontoDeleteDialogComponent, PontoDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CandyShopApplicationPontoModule {}
