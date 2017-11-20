import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SvFootballSharedModule } from '../../shared';
import {
    Ns_ResultService,
    Ns_ResultPopupService,
    Ns_ResultComponent,
    Ns_ResultDetailComponent,
    Ns_ResultDialogComponent,
    Ns_ResultPopupComponent,
    Ns_ResultDeletePopupComponent,
    Ns_ResultDeleteDialogComponent,
    ns_ResultRoute,
    ns_ResultPopupRoute,
} from './';

const ENTITY_STATES = [
    ...ns_ResultRoute,
    ...ns_ResultPopupRoute,
];

@NgModule({
    imports: [
        SvFootballSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        Ns_ResultComponent,
        Ns_ResultDetailComponent,
        Ns_ResultDialogComponent,
        Ns_ResultDeleteDialogComponent,
        Ns_ResultPopupComponent,
        Ns_ResultDeletePopupComponent,
    ],
    entryComponents: [
        Ns_ResultComponent,
        Ns_ResultDialogComponent,
        Ns_ResultPopupComponent,
        Ns_ResultDeleteDialogComponent,
        Ns_ResultDeletePopupComponent,
    ],
    providers: [
        Ns_ResultService,
        Ns_ResultPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SvFootballNs_ResultModule {}
