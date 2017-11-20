import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Ns_Highlight } from './ns-highlight.model';
import { Ns_HighlightPopupService } from './ns-highlight-popup.service';
import { Ns_HighlightService } from './ns-highlight.service';

@Component({
    selector: 'jhi-ns-highlight-delete-dialog',
    templateUrl: './ns-highlight-delete-dialog.component.html'
})
export class Ns_HighlightDeleteDialogComponent {

    ns_Highlight: Ns_Highlight;

    constructor(
        private ns_HighlightService: Ns_HighlightService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.ns_HighlightService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'ns_HighlightListModification',
                content: 'Deleted an ns_Highlight'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-ns-highlight-delete-popup',
    template: ''
})
export class Ns_HighlightDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private ns_HighlightPopupService: Ns_HighlightPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.ns_HighlightPopupService
                .open(Ns_HighlightDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
