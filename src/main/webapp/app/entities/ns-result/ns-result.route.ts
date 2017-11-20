import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { Ns_ResultComponent } from './ns-result.component';
import { Ns_ResultDetailComponent } from './ns-result-detail.component';
import { Ns_ResultPopupComponent } from './ns-result-dialog.component';
import { Ns_ResultDeletePopupComponent } from './ns-result-delete-dialog.component';

export const ns_ResultRoute: Routes = [
    {
        path: 'ns-result',
        component: Ns_ResultComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Ns_Results'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'ns-result/:id',
        component: Ns_ResultDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Ns_Results'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const ns_ResultPopupRoute: Routes = [
    {
        path: 'ns-result-new',
        component: Ns_ResultPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Ns_Results'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ns-result/:id/edit',
        component: Ns_ResultPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Ns_Results'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ns-result/:id/delete',
        component: Ns_ResultDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Ns_Results'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
