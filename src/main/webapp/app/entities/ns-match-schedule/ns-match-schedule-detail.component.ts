import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Ns_Match_Schedule } from './ns-match-schedule.model';
import { Ns_Match_ScheduleService } from './ns-match-schedule.service';

@Component({
    selector: 'jhi-ns-match-schedule-detail',
    templateUrl: './ns-match-schedule-detail.component.html'
})
export class Ns_Match_ScheduleDetailComponent implements OnInit, OnDestroy {

    ns_Match_Schedule: Ns_Match_Schedule;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private ns_Match_ScheduleService: Ns_Match_ScheduleService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInNs_Match_Schedules();
    }

    load(id) {
        this.ns_Match_ScheduleService.find(id).subscribe((ns_Match_Schedule) => {
            this.ns_Match_Schedule = ns_Match_Schedule;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInNs_Match_Schedules() {
        this.eventSubscriber = this.eventManager.subscribe(
            'ns_Match_ScheduleListModification',
            (response) => this.load(this.ns_Match_Schedule.id)
        );
    }
}
