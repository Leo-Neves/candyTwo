/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CandyShopApplicationTestModule } from '../../../test.module';
import { PontoDeleteDialogComponent } from 'app/entities/ponto/ponto-delete-dialog.component';
import { PontoService } from 'app/entities/ponto/ponto.service';

describe('Component Tests', () => {
    describe('Ponto Management Delete Component', () => {
        let comp: PontoDeleteDialogComponent;
        let fixture: ComponentFixture<PontoDeleteDialogComponent>;
        let service: PontoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CandyShopApplicationTestModule],
                declarations: [PontoDeleteDialogComponent]
            })
                .overrideTemplate(PontoDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PontoDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PontoService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete('123');
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith('123');
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
