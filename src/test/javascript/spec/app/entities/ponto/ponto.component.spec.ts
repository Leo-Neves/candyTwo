/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CandyShopApplicationTestModule } from '../../../test.module';
import { PontoComponent } from 'app/entities/ponto/ponto.component';
import { PontoService } from 'app/entities/ponto/ponto.service';
import { Ponto } from 'app/shared/model/ponto.model';

describe('Component Tests', () => {
    describe('Ponto Management Component', () => {
        let comp: PontoComponent;
        let fixture: ComponentFixture<PontoComponent>;
        let service: PontoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CandyShopApplicationTestModule],
                declarations: [PontoComponent],
                providers: []
            })
                .overrideTemplate(PontoComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PontoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PontoService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Ponto('123')],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.pontos[0]).toEqual(jasmine.objectContaining({ id: '123' }));
        });
    });
});
