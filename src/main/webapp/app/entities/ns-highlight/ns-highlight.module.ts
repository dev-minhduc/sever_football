import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SvFootballSharedModule } from '../../shared';
import {
    Ns_HighlightService,
    Ns_HighlightPopupService,
    Ns_HighlightComponent,
    Ns_HighlightDetailComponent,
    Ns_HighlightDialogComponent,
    Ns_HighlightPopupComponent,
    Ns_HighlightDeletePopupComponent,
    Ns_HighlightDeleteDialogComponent,
    ns_HighlightRoute,
    ns_HighlightPopupRoute,
} from './';

const ENTITY_STATES = [
    ...ns_HighlightRoute,
    ...ns_HighlightPopupRoute,
];

@NgModule({
    imports: [
        SvFootballSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        Ns_HighlightComponent,
        Ns_HighlightDetailComponent,
        Ns_HighlightDialogComponent,
        Ns_HighlightDeleteDialogComponent,
        Ns_HighlightPopupComponent,
        Ns_HighlightDeletePopupComponent,
    ],
    entryComponents: [
        Ns_HighlightComponent,
        Ns_HighlightDialogComponent,
        Ns_HighlightPopupComponent,
        Ns_HighlightDeleteDialogComponent,
        Ns_HighlightDeletePopupComponent,
    ],
    providers: [
        Ns_HighlightService,
        Ns_HighlightPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SvFootballNs_HighlightModule {}
