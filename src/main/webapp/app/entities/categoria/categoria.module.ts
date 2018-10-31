import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CandyShopApplicationSharedModule } from 'app/shared';
import {
    CategoriaComponent,
    CategoriaDetailComponent,
    CategoriaUpdateComponent,
    CategoriaDeletePopupComponent,
    CategoriaDeleteDialogComponent,
    categoriaRoute,
    categoriaPopupRoute
} from './';

const ENTITY_STATES = [...categoriaRoute, ...categoriaPopupRoute];

@NgModule({
    imports: [CandyShopApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CategoriaComponent,
        CategoriaDetailComponent,
        CategoriaUpdateComponent,
        CategoriaDeleteDialogComponent,
        CategoriaDeletePopupComponent
    ],
    entryComponents: [CategoriaComponent, CategoriaUpdateComponent, CategoriaDeleteDialogComponent, CategoriaDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CandyShopApplicationCategoriaModule {}
