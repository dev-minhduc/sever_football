import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { Ns_RankComponent } from './ns-rank.component';
import { Ns_RankDetailComponent } from './ns-rank-detail.component';
import { Ns_RankPopupComponent } from './ns-rank-dialog.component';
import { Ns_RankDeletePopupComponent } from './ns-rank-delete-dialog.component';

export const ns_RankRoute: Routes = [
    {
        path: 'ns-rank',
        component: Ns_RankComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Ns_Ranks'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'ns-rank/:id',
        component: Ns_RankDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Ns_Ranks'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const ns_RankPopupRoute: Routes = [
    {
        path: 'ns-rank-new',
        component: Ns_RankPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Ns_Ranks'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ns-rank/:id/edit',
        component: Ns_RankPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Ns_Ranks'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ns-rank/:id/delete',
        component: Ns_RankDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Ns_Ranks'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
