import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Ns_Rank } from './ns-rank.model';
import { Ns_RankPopupService } from './ns-rank-popup.service';
import { Ns_RankService } from './ns-rank.service';

@Component({
    selector: 'jhi-ns-rank-dialog',
    templateUrl: './ns-rank-dialog.component.html'
})
export class Ns_RankDialogComponent implements OnInit {

    ns_Rank: Ns_Rank;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private ns_RankService: Ns_RankService,
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
        if (this.ns_Rank.id !== undefined) {
            this.subscribeToSaveResponse(
                this.ns_RankService.update(this.ns_Rank));
        } else {
            this.subscribeToSaveResponse(
                this.ns_RankService.create(this.ns_Rank));
        }
    }

    private subscribeToSaveResponse(result: Observable<Ns_Rank>) {
        result.subscribe((res: Ns_Rank) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Ns_Rank) {
        this.eventManager.broadcast({ name: 'ns_RankListModification', content: 'OK'});
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
    selector: 'jhi-ns-rank-popup',
    template: ''
})
export class Ns_RankPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private ns_RankPopupService: Ns_RankPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.ns_RankPopupService
                    .open(Ns_RankDialogComponent as Component, params['id']);
            } else {
                this.ns_RankPopupService
                    .open(Ns_RankDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
