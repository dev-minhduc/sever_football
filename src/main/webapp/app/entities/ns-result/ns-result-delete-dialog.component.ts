import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Ns_Result } from './ns-result.model';
import { Ns_ResultPopupService } from './ns-result-popup.service';
import { Ns_ResultService } from './ns-result.service';

@Component({
    selector: 'jhi-ns-result-delete-dialog',
    templateUrl: './ns-result-delete-dialog.component.html'
})
export class Ns_ResultDeleteDialogComponent {

    ns_Result: Ns_Result;

    constructor(
        private ns_ResultService: Ns_ResultService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.ns_ResultService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'ns_ResultListModification',
                content: 'Deleted an ns_Result'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-ns-result-delete-popup',
    template: ''
})
export class Ns_ResultDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private ns_ResultPopupService: Ns_ResultPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.ns_ResultPopupService
                .open(Ns_ResultDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
