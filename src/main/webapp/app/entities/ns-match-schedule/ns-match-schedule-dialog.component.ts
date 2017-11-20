import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Ns_Match_Schedule } from './ns-match-schedule.model';
import { Ns_Match_SchedulePopupService } from './ns-match-schedule-popup.service';
import { Ns_Match_ScheduleService } from './ns-match-schedule.service';

@Component({
    selector: 'jhi-ns-match-schedule-dialog',
    templateUrl: './ns-match-schedule-dialog.component.html'
})
export class Ns_Match_ScheduleDialogComponent implements OnInit {

    ns_Match_Schedule: Ns_Match_Schedule;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private ns_Match_ScheduleService: Ns_Match_ScheduleService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.ns_Match_Schedule.id !== undefined) {
            this.subscribeToSaveResponse(
                this.ns_Match_ScheduleService.update(this.ns_Match_Schedule));
        } else {
            this.subscribeToSaveResponse(
                this.ns_Match_ScheduleService.create(this.ns_Match_Schedule));
        }
    }

    private subscribeToSaveResponse(result: Observable<Ns_Match_Schedule>) {
        result.subscribe((res: Ns_Match_Schedule) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Ns_Match_Schedule) {
        this.eventManager.broadcast({ name: 'ns_Match_ScheduleListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-ns-match-schedule-popup',
    template: ''
})
export class Ns_Match_SchedulePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private ns_Match_SchedulePopupService: Ns_Match_SchedulePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.ns_Match_SchedulePopupService
                    .open(Ns_Match_ScheduleDialogComponent as Component, params['id']);
            } else {
                this.ns_Match_SchedulePopupService
                    .open(Ns_Match_ScheduleDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
