import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { Ns_Match_ScheduleComponent } from './ns-match-schedule.component';
import { Ns_Match_ScheduleDetailComponent } from './ns-match-schedule-detail.component';
import { Ns_Match_SchedulePopupComponent } from './ns-match-schedule-dialog.component';
import { Ns_Match_ScheduleDeletePopupComponent } from './ns-match-schedule-delete-dialog.component';

export const ns_Match_ScheduleRoute: Routes = [
    {
        path: 'ns-match-schedule',
        component: Ns_Match_ScheduleComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Ns_Match_Schedules'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'ns-match-schedule/:id',
        component: Ns_Match_ScheduleDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Ns_Match_Schedules'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const ns_Match_SchedulePopupRoute: Routes = [
    {
        path: 'ns-match-schedule-new',
        component: Ns_Match_SchedulePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Ns_Match_Schedules'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ns-match-schedule/:id/edit',
        component: Ns_Match_SchedulePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Ns_Match_Schedules'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ns-match-schedule/:id/delete',
        component: Ns_Match_ScheduleDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Ns_Match_Schedules'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
