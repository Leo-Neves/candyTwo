import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { CandyShopApplicationCategoriaModule } from './categoria/categoria.module';
import { CandyShopApplicationPedidoModule } from './pedido/pedido.module';
import { CandyShopApplicationProdutoModule } from './produto/produto.module';
import { CandyShopApplicationUsuarioModule } from './usuario/usuario.module';
import { CandyShopApplicationPontoModule } from './ponto/ponto.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        CandyShopApplicationCategoriaModule,
        CandyShopApplicationPedidoModule,
        CandyShopApplicationProdutoModule,
        CandyShopApplicationUsuarioModule,
        CandyShopApplicationPontoModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CandyShopApplicationEntityModule {}
