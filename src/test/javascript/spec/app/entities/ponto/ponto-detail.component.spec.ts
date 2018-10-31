/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CandyShopApplicationTestModule } from '../../../test.module';
import { PontoDetailComponent } from 'app/entities/ponto/ponto-detail.component';
import { Ponto } from 'app/shared/model/ponto.model';

describe('Component Tests', () => {
    describe('Ponto Management Detail Component', () => {
        let comp: PontoDetailComponent;
        let fixture: ComponentFixture<PontoDetailComponent>;
        const route = ({ data: of({ ponto: new Ponto('123') }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CandyShopApplicationTestModule],
                declarations: [PontoDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PontoDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PontoDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.ponto).toEqual(jasmine.objectContaining({ id: '123' }));
            });
        });
    });
});
