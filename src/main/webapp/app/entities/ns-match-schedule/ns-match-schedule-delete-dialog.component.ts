import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Ns_Match_Schedule } from './ns-match-schedule.model';
import { Ns_Match_SchedulePopupService } from './ns-match-schedule-popup.service';
import { Ns_Match_ScheduleService } from './ns-match-schedule.service';

@Component({
    selector: 'jhi-ns-match-schedule-delete-dialog',
    templateUrl: './ns-match-schedule-delete-dialog.component.html'
})
export class Ns_Match_ScheduleDeleteDialogComponent {

    ns_Match_Schedule: Ns_Match_Schedule;

    constructor(
        private ns_Match_ScheduleService: Ns_Match_ScheduleService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.ns_Match_ScheduleService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'ns_Match_ScheduleListModification',
                content: 'Deleted an ns_Match_Schedule'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-ns-match-schedule-delete-popup',
    template: ''
})
export class Ns_Match_ScheduleDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private ns_Match_SchedulePopupService: Ns_Match_SchedulePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.ns_Match_SchedulePopupService
                .open(Ns_Match_ScheduleDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
