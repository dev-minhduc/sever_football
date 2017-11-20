import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SvFootballSharedModule } from '../../shared';
import {
    Ns_Match_ScheduleService,
    Ns_Match_SchedulePopupService,
    Ns_Match_ScheduleComponent,
    Ns_Match_ScheduleDetailComponent,
    Ns_Match_ScheduleDialogComponent,
    Ns_Match_SchedulePopupComponent,
    Ns_Match_ScheduleDeletePopupComponent,
    Ns_Match_ScheduleDeleteDialogComponent,
    ns_Match_ScheduleRoute,
    ns_Match_SchedulePopupRoute,
} from './';

const ENTITY_STATES = [
    ...ns_Match_ScheduleRoute,
    ...ns_Match_SchedulePopupRoute,
];

@NgModule({
    imports: [
        SvFootballSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        Ns_Match_ScheduleComponent,
        Ns_Match_ScheduleDetailComponent,
        Ns_Match_ScheduleDialogComponent,
        Ns_Match_ScheduleDeleteDialogComponent,
        Ns_Match_SchedulePopupComponent,
        Ns_Match_ScheduleDeletePopupComponent,
    ],
    entryComponents: [
        Ns_Match_ScheduleComponent,
        Ns_Match_ScheduleDialogComponent,
        Ns_Match_SchedulePopupComponent,
        Ns_Match_ScheduleDeleteDialogComponent,
        Ns_Match_ScheduleDeletePopupComponent,
    ],
    providers: [
        Ns_Match_ScheduleService,
        Ns_Match_SchedulePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SvFootballNs_Match_ScheduleModule {}
