import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Ns_Result } from './ns-result.model';
import { Ns_ResultPopupService } from './ns-result-popup.service';
import { Ns_ResultService } from './ns-result.service';

@Component({
    selector: 'jhi-ns-result-dialog',
    templateUrl: './ns-result-dialog.component.html'
})
export class Ns_ResultDialogComponent implements OnInit {

    ns_Result: Ns_Result;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private ns_ResultService: Ns_ResultService,
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
        if (this.ns_Result.id !== undefined) {
            this.subscribeToSaveResponse(
                this.ns_ResultService.update(this.ns_Result));
        } else {
            this.subscribeToSaveResponse(
                this.ns_ResultService.create(this.ns_Result));
        }
    }

    private subscribeToSaveResponse(result: Observable<Ns_Result>) {
        result.subscribe((res: Ns_Result) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Ns_Result) {
        this.eventManager.broadcast({ name: 'ns_ResultListModification', content: 'OK'});
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
    selector: 'jhi-ns-result-popup',
    template: ''
})
export class Ns_ResultPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private ns_ResultPopupService: Ns_ResultPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.ns_ResultPopupService
                    .open(Ns_ResultDialogComponent as Component, params['id']);
            } else {
                this.ns_ResultPopupService
                    .open(Ns_ResultDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
