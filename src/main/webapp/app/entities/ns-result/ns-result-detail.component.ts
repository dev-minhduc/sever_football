import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Ns_Result } from './ns-result.model';
import { Ns_ResultService } from './ns-result.service';

@Component({
    selector: 'jhi-ns-result-detail',
    templateUrl: './ns-result-detail.component.html'
})
export class Ns_ResultDetailComponent implements OnInit, OnDestroy {

    ns_Result: Ns_Result;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private ns_ResultService: Ns_ResultService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInNs_Results();
    }

    load(id) {
        this.ns_ResultService.find(id).subscribe((ns_Result) => {
            this.ns_Result = ns_Result;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInNs_Results() {
        this.eventSubscriber = this.eventManager.subscribe(
            'ns_ResultListModification',
            (response) => this.load(this.ns_Result.id)
        );
    }
}
