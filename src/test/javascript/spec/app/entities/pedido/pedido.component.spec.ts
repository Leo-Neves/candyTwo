/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CandyShopApplicationTestModule } from '../../../test.module';
import { PedidoComponent } from 'app/entities/pedido/pedido.component';
import { PedidoService } from 'app/entities/pedido/pedido.service';
import { Pedido } from 'app/shared/model/pedido.model';

describe('Component Tests', () => {
    describe('Pedido Management Component', () => {
        let comp: PedidoComponent;
        let fixture: ComponentFixture<PedidoComponent>;
        let service: PedidoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CandyShopApplicationTestModule],
                declarations: [PedidoComponent],
                providers: []
            })
                .overrideTemplate(PedidoComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PedidoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PedidoService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Pedido('123')],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.pedidos[0]).toEqual(jasmine.objectContaining({ id: '123' }));
        });
    });
});
