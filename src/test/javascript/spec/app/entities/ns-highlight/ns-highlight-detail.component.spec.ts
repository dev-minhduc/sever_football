/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { SvFootballTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { Ns_HighlightDetailComponent } from '../../../../../../main/webapp/app/entities/ns-highlight/ns-highlight-detail.component';
import { Ns_HighlightService } from '../../../../../../main/webapp/app/entities/ns-highlight/ns-highlight.service';
import { Ns_Highlight } from '../../../../../../main/webapp/app/entities/ns-highlight/ns-highlight.model';

describe('Component Tests', () => {

    describe('Ns_Highlight Management Detail Component', () => {
        let comp: Ns_HighlightDetailComponent;
        let fixture: ComponentFixture<Ns_HighlightDetailComponent>;
        let service: Ns_HighlightService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SvFootballTestModule],
                declarations: [Ns_HighlightDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    Ns_HighlightService,
                    JhiEventManager
                ]
            }).overrideTemplate(Ns_HighlightDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(Ns_HighlightDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(Ns_HighlightService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Ns_Highlight(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.ns_Highlight).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
