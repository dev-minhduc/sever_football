/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { SvFootballTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { Ns_Match_ScheduleDetailComponent } from '../../../../../../main/webapp/app/entities/ns-match-schedule/ns-match-schedule-detail.component';
import { Ns_Match_ScheduleService } from '../../../../../../main/webapp/app/entities/ns-match-schedule/ns-match-schedule.service';
import { Ns_Match_Schedule } from '../../../../../../main/webapp/app/entities/ns-match-schedule/ns-match-schedule.model';

describe('Component Tests', () => {

    describe('Ns_Match_Schedule Management Detail Component', () => {
        let comp: Ns_Match_ScheduleDetailComponent;
        let fixture: ComponentFixture<Ns_Match_ScheduleDetailComponent>;
        let service: Ns_Match_ScheduleService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SvFootballTestModule],
                declarations: [Ns_Match_ScheduleDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    Ns_Match_ScheduleService,
                    JhiEventManager
                ]
            }).overrideTemplate(Ns_Match_ScheduleDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(Ns_Match_ScheduleDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(Ns_Match_ScheduleService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Ns_Match_Schedule(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.ns_Match_Schedule).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
