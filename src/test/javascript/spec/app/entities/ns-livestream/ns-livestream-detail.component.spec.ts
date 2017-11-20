/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { SvFootballTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { Ns_LivestreamDetailComponent } from '../../../../../../main/webapp/app/entities/ns-livestream/ns-livestream-detail.component';
import { Ns_LivestreamService } from '../../../../../../main/webapp/app/entities/ns-livestream/ns-livestream.service';
import { Ns_Livestream } from '../../../../../../main/webapp/app/entities/ns-livestream/ns-livestream.model';

describe('Component Tests', () => {

    describe('Ns_Livestream Management Detail Component', () => {
        let comp: Ns_LivestreamDetailComponent;
        let fixture: ComponentFixture<Ns_LivestreamDetailComponent>;
        let service: Ns_LivestreamService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SvFootballTestModule],
                declarations: [Ns_LivestreamDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    Ns_LivestreamService,
                    JhiEventManager
                ]
            }).overrideTemplate(Ns_LivestreamDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(Ns_LivestreamDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(Ns_LivestreamService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Ns_Livestream(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.ns_Livestream).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
