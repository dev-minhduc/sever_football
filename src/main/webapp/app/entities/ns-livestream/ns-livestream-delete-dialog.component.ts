import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Ns_Livestream } from './ns-livestream.model';
import { Ns_LivestreamPopupService } from './ns-livestream-popup.service';
import { Ns_LivestreamService } from './ns-livestream.service';

@Component({
    selector: 'jhi-ns-livestream-delete-dialog',
    templateUrl: './ns-livestream-delete-dialog.component.html'
})
export class Ns_LivestreamDeleteDialogComponent {

    ns_Livestream: Ns_Livestream;

    constructor(
        private ns_LivestreamService: Ns_LivestreamService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.ns_LivestreamService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'ns_LivestreamListModification',
                content: 'Deleted an ns_Livestream'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-ns-livestream-delete-popup',
    template: ''
})
export class Ns_LivestreamDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private ns_LivestreamPopupService: Ns_LivestreamPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.ns_LivestreamPopupService
                .open(Ns_LivestreamDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
