import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SvFootballSharedModule } from '../../shared';
import {
    Ns_LivestreamService,
    Ns_LivestreamPopupService,
    Ns_LivestreamComponent,
    Ns_LivestreamDetailComponent,
    Ns_LivestreamDialogComponent,
    Ns_LivestreamPopupComponent,
    Ns_LivestreamDeletePopupComponent,
    Ns_LivestreamDeleteDialogComponent,
    ns_LivestreamRoute,
    ns_LivestreamPopupRoute,
} from './';

const ENTITY_STATES = [
    ...ns_LivestreamRoute,
    ...ns_LivestreamPopupRoute,
];

@NgModule({
    imports: [
        SvFootballSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        Ns_LivestreamComponent,
        Ns_LivestreamDetailComponent,
        Ns_LivestreamDialogComponent,
        Ns_LivestreamDeleteDialogComponent,
        Ns_LivestreamPopupComponent,
        Ns_LivestreamDeletePopupComponent,
    ],
    entryComponents: [
        Ns_LivestreamComponent,
        Ns_LivestreamDialogComponent,
        Ns_LivestreamPopupComponent,
        Ns_LivestreamDeleteDialogComponent,
        Ns_LivestreamDeletePopupComponent,
    ],
    providers: [
        Ns_LivestreamService,
        Ns_LivestreamPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SvFootballNs_LivestreamModule {}
