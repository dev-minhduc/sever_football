import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { Ns_HighlightComponent } from './ns-highlight.component';
import { Ns_HighlightDetailComponent } from './ns-highlight-detail.component';
import { Ns_HighlightPopupComponent } from './ns-highlight-dialog.component';
import { Ns_HighlightDeletePopupComponent } from './ns-highlight-delete-dialog.component';

export const ns_HighlightRoute: Routes = [
    {
        path: 'ns-highlight',
        component: Ns_HighlightComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Ns_Highlights'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'ns-highlight/:id',
        component: Ns_HighlightDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Ns_Highlights'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const ns_HighlightPopupRoute: Routes = [
    {
        path: 'ns-highlight-new',
        component: Ns_HighlightPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Ns_Highlights'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ns-highlight/:id/edit',
        component: Ns_HighlightPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Ns_Highlights'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ns-highlight/:id/delete',
        component: Ns_HighlightDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Ns_Highlights'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
