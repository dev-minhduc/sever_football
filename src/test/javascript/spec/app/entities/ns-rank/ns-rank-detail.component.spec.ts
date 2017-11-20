/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { SvFootballTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { Ns_RankDetailComponent } from '../../../../../../main/webapp/app/entities/ns-rank/ns-rank-detail.component';
import { Ns_RankService } from '../../../../../../main/webapp/app/entities/ns-rank/ns-rank.service';
import { Ns_Rank } from '../../../../../../main/webapp/app/entities/ns-rank/ns-rank.model';

describe('Component Tests', () => {

    describe('Ns_Rank Management Detail Component', () => {
        let comp: Ns_RankDetailComponent;
        let fixture: ComponentFixture<Ns_RankDetailComponent>;
        let service: Ns_RankService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SvFootballTestModule],
                declarations: [Ns_RankDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    Ns_RankService,
                    JhiEventManager
                ]
            }).overrideTemplate(Ns_RankDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(Ns_RankDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(Ns_RankService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Ns_Rank(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.ns_Rank).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
