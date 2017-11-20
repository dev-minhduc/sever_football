import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Ns_Match_Schedule } from './ns-match-schedule.model';
import { Ns_Match_ScheduleService } from './ns-match-schedule.service';

@Injectable()
export class Ns_Match_SchedulePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private ns_Match_ScheduleService: Ns_Match_ScheduleService

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
                this.ns_Match_ScheduleService.find(id).subscribe((ns_Match_Schedule) => {
                    this.ngbModalRef = this.ns_Match_ScheduleModalRef(component, ns_Match_Schedule);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.ns_Match_ScheduleModalRef(component, new Ns_Match_Schedule());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    ns_Match_ScheduleModalRef(component: Component, ns_Match_Schedule: Ns_Match_Schedule): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.ns_Match_Schedule = ns_Match_Schedule;
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
