import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Ns_Livestream } from './ns-livestream.model';
import { Ns_LivestreamService } from './ns-livestream.service';

@Injectable()
export class Ns_LivestreamPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private ns_LivestreamService: Ns_LivestreamService

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
                this.ns_LivestreamService.find(id).subscribe((ns_Livestream) => {
                    this.ngbModalRef = this.ns_LivestreamModalRef(component, ns_Livestream);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.ns_LivestreamModalRef(component, new Ns_Livestream());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    ns_LivestreamModalRef(component: Component, ns_Livestream: Ns_Livestream): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.ns_Livestream = ns_Livestream;
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
