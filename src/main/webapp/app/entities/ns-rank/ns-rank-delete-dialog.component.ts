import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Ns_Rank } from './ns-rank.model';
import { Ns_RankPopupService } from './ns-rank-popup.service';
import { Ns_RankService } from './ns-rank.service';

@Component({
    selector: 'jhi-ns-rank-delete-dialog',
    templateUrl: './ns-rank-delete-dialog.component.html'
})
export class Ns_RankDeleteDialogComponent {

    ns_Rank: Ns_Rank;

    constructor(
        private ns_RankService: Ns_RankService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.ns_RankService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'ns_RankListModification',
                content: 'Deleted an ns_Rank'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-ns-rank-delete-popup',
    template: ''
})
export class Ns_RankDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private ns_RankPopupService: Ns_RankPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.ns_RankPopupService
                .open(Ns_RankDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
