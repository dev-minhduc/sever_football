import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Ns_Livestream } from './ns-livestream.model';
import { Ns_LivestreamService } from './ns-livestream.service';

@Component({
    selector: 'jhi-ns-livestream-detail',
    templateUrl: './ns-livestream-detail.component.html'
})
export class Ns_LivestreamDetailComponent implements OnInit, OnDestroy {

    ns_Livestream: Ns_Livestream;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private ns_LivestreamService: Ns_LivestreamService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInNs_Livestreams();
    }

    load(id) {
        this.ns_LivestreamService.find(id).subscribe((ns_Livestream) => {
            this.ns_Livestream = ns_Livestream;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInNs_Livestreams() {
        this.eventSubscriber = this.eventManager.subscribe(
            'ns_LivestreamListModification',
            (response) => this.load(this.ns_Livestream.id)
        );
    }
}
