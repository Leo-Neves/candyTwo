/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CandyShopApplicationTestModule } from '../../../test.module';
import { PontoUpdateComponent } from 'app/entities/ponto/ponto-update.component';
import { PontoService } from 'app/entities/ponto/ponto.service';
import { Ponto } from 'app/shared/model/ponto.model';

describe('Component Tests', () => {
    describe('Ponto Management Update Component', () => {
        let comp: PontoUpdateComponent;
        let fixture: ComponentFixture<PontoUpdateComponent>;
        let service: PontoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CandyShopApplicationTestModule],
                declarations: [PontoUpdateComponent]
            })
                .overrideTemplate(PontoUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PontoUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PontoService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Ponto('123');
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.ponto = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Ponto();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.ponto = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
