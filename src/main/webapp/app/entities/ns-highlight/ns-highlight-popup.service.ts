import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Ns_Highlight } from './ns-highlight.model';
import { Ns_HighlightService } from './ns-highlight.service';

@Injectable()
export class Ns_HighlightPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private ns_HighlightService: Ns_HighlightService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.ns_HighlightService.find(id).subscribe((ns_Highlight) => {
                    if (ns_Highlight.date) {
                        ns_Highlight.date = {
                            year: ns_Highlight.date.getFullYear(),
                            month: ns_Highlight.date.getMonth() + 1,
                            day: ns_Highlight.date.getDate()
                        };
                    }
                    this.ngbModalRef = this.ns_HighlightModalRef(component, ns_Highlight);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.ns_HighlightModalRef(component, new Ns_Highlight());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    ns_HighlightModalRef(component: Component, ns_Highlight: Ns_Highlight): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.ns_Highlight = ns_Highlight;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
