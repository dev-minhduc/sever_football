/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { SvFootballTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { Ns_ResultDetailComponent } from '../../../../../../main/webapp/app/entities/ns-result/ns-result-detail.component';
import { Ns_ResultService } from '../../../../../../main/webapp/app/entities/ns-result/ns-result.service';
import { Ns_Result } from '../../../../../../main/webapp/app/entities/ns-result/ns-result.model';

describe('Component Tests', () => {

    describe('Ns_Result Management Detail Component', () => {
        let comp: Ns_ResultDetailComponent;
        let fixture: ComponentFixture<Ns_ResultDetailComponent>;
        let service: Ns_ResultService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SvFootballTestModule],
                declarations: [Ns_ResultDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    Ns_ResultService,
                    JhiEventManager
                ]
            }).overrideTemplate(Ns_ResultDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(Ns_ResultDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(Ns_ResultService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Ns_Result(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.ns_Result).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
