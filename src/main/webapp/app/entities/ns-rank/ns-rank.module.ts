import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SvFootballSharedModule } from '../../shared';
import {
    Ns_RankService,
    Ns_RankPopupService,
    Ns_RankComponent,
    Ns_RankDetailComponent,
    Ns_RankDialogComponent,
    Ns_RankPopupComponent,
    Ns_RankDeletePopupComponent,
    Ns_RankDeleteDialogComponent,
    ns_RankRoute,
    ns_RankPopupRoute,
} from './';

const ENTITY_STATES = [
    ...ns_RankRoute,
    ...ns_RankPopupRoute,
];

@NgModule({
    imports: [
        SvFootballSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        Ns_RankComponent,
        Ns_RankDetailComponent,
        Ns_RankDialogComponent,
        Ns_RankDeleteDialogComponent,
        Ns_RankPopupComponent,
        Ns_RankDeletePopupComponent,
    ],
    entryComponents: [
        Ns_RankComponent,
        Ns_RankDialogComponent,
        Ns_RankPopupComponent,
        Ns_RankDeleteDialogComponent,
        Ns_RankDeletePopupComponent,
    ],
    providers: [
        Ns_RankService,
        Ns_RankPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SvFootballNs_RankModule {}
