import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { Ns_LivestreamComponent } from './ns-livestream.component';
import { Ns_LivestreamDetailComponent } from './ns-livestream-detail.component';
import { Ns_LivestreamPopupComponent } from './ns-livestream-dialog.component';
import { Ns_LivestreamDeletePopupComponent } from './ns-livestream-delete-dialog.component';

export const ns_LivestreamRoute: Routes = [
    {
        path: 'ns-livestream',
        component: Ns_LivestreamComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Ns_Livestreams'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'ns-livestream/:id',
        component: Ns_LivestreamDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Ns_Livestreams'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const ns_LivestreamPopupRoute: Routes = [
    {
        path: 'ns-livestream-new',
        component: Ns_LivestreamPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Ns_Livestreams'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ns-livestream/:id/edit',
        component: Ns_LivestreamPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Ns_Livestreams'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ns-livestream/:id/delete',
        component: Ns_LivestreamDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Ns_Livestreams'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
