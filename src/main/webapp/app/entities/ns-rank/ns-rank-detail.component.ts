import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Ns_Rank } from './ns-rank.model';
import { Ns_RankService } from './ns-rank.service';

@Component({
    selector: 'jhi-ns-rank-detail',
    templateUrl: './ns-rank-detail.component.html'
})
export class Ns_RankDetailComponent implements OnInit, OnDestroy {

    ns_Rank: Ns_Rank;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private ns_RankService: Ns_RankService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInNs_Ranks();
    }

    load(id) {
        this.ns_RankService.find(id).subscribe((ns_Rank) => {
            this.ns_Rank = ns_Rank;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInNs_Ranks() {
        this.eventSubscriber = this.eventManager.subscribe(
            'ns_RankListModification',
            (response) => this.load(this.ns_Rank.id)
        );
    }
}
