import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Ns_Highlight } from './ns-highlight.model';
import { Ns_HighlightPopupService } from './ns-highlight-popup.service';
import { Ns_HighlightService } from './ns-highlight.service';

@Component({
    selector: 'jhi-ns-highlight-dialog',
    templateUrl: './ns-highlight-dialog.component.html'
})
export class Ns_HighlightDialogComponent implements OnInit {

    ns_Highlight: Ns_Highlight;
    isSaving: boolean;
    dateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private ns_HighlightService: Ns_HighlightService,
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
        if (this.ns_Highlight.id !== undefined) {
            this.subscribeToSaveResponse(
                this.ns_HighlightService.update(this.ns_Highlight));
        } else {
            this.subscribeToSaveResponse(
                this.ns_HighlightService.create(this.ns_Highlight));
        }
    }

    private subscribeToSaveResponse(result: Observable<Ns_Highlight>) {
        result.subscribe((res: Ns_Highlight) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Ns_Highlight) {
        this.eventManager.broadcast({ name: 'ns_HighlightListModification', content: 'OK'});
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
    selector: 'jhi-ns-highlight-popup',
    template: ''
})
export class Ns_HighlightPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private ns_HighlightPopupService: Ns_HighlightPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.ns_HighlightPopupService
                    .open(Ns_HighlightDialogComponent as Component, params['id']);
            } else {
                this.ns_HighlightPopupService
                    .open(Ns_HighlightDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
