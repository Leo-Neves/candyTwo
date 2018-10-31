import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { NgbDateAdapter } from '@ng-bootstrap/ng-bootstrap';

import { NgbDateMomentAdapter } from './util/datepicker-adapter';
import { CandyShopApplicationSharedLibsModule, CandyShopApplicationSharedCommonModule, HasAnyAuthorityDirective } from './';

@NgModule({
    imports: [CandyShopApplicationSharedLibsModule, CandyShopApplicationSharedCommonModule],
    declarations: [HasAnyAuthorityDirective],
    providers: [{ provide: NgbDateAdapter, useClass: NgbDateMomentAdapter }],
    exports: [CandyShopApplicationSharedCommonModule, HasAnyAuthorityDirective],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CandyShopApplicationSharedModule {}
