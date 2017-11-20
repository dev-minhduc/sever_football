import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Ns_Livestream } from './ns-livestream.model';
import { Ns_LivestreamPopupService } from './ns-livestream-popup.service';
import { Ns_LivestreamService } from './ns-livestream.service';

@Component({
    selector: 'jhi-ns-livestream-dialog',
    templateUrl: './ns-livestream-dialog.component.html'
})
export class Ns_LivestreamDialogComponent implements OnInit {

    ns_Livestream: Ns_Livestream;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private ns_LivestreamService: Ns_LivestreamService,
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
        if (this.ns_Livestream.id !== undefined) {
            this.subscribeToSaveResponse(
                this.ns_LivestreamService.update(this.ns_Livestream));
        } else {
            this.subscribeToSaveResponse(
                this.ns_LivestreamService.create(this.ns_Livestream));
        }
    }

    private subscribeToSaveResponse(result: Observable<Ns_Livestream>) {
        result.subscribe((res: Ns_Livestream) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Ns_Livestream) {
        this.eventManager.broadcast({ name: 'ns_LivestreamListModification', content: 'OK'});
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
    selector: 'jhi-ns-livestream-popup',
    template: ''
})
export class Ns_LivestreamPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private ns_LivestreamPopupService: Ns_LivestreamPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.ns_LivestreamPopupService
                    .open(Ns_LivestreamDialogComponent as Component, params['id']);
            } else {
                this.ns_LivestreamPopupService
                    .open(Ns_LivestreamDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
