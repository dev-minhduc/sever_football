import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Ns_Highlight } from './ns-highlight.model';
import { Ns_HighlightService } from './ns-highlight.service';

@Component({
    selector: 'jhi-ns-highlight-detail',
    templateUrl: './ns-highlight-detail.component.html'
})
export class Ns_HighlightDetailComponent implements OnInit, OnDestroy {

    ns_Highlight: Ns_Highlight;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private ns_HighlightService: Ns_HighlightService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInNs_Highlights();
    }

    load(id) {
        this.ns_HighlightService.find(id).subscribe((ns_Highlight) => {
            this.ns_Highlight = ns_Highlight;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInNs_Highlights() {
        this.eventSubscriber = this.eventManager.subscribe(
            'ns_HighlightListModification',
            (response) => this.load(this.ns_Highlight.id)
        );
    }
}
