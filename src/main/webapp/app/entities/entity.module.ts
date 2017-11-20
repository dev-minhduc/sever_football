import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { SvFootballNs_LivestreamModule } from './ns-livestream/ns-livestream.module';
import { SvFootballNs_HighlightModule } from './ns-highlight/ns-highlight.module';
import { SvFootballNs_RankModule } from './ns-rank/ns-rank.module';
import { SvFootballNs_ResultModule } from './ns-result/ns-result.module';
import { SvFootballNs_Match_ScheduleModule } from './ns-match-schedule/ns-match-schedule.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        SvFootballNs_LivestreamModule,
        SvFootballNs_HighlightModule,
        SvFootballNs_RankModule,
        SvFootballNs_ResultModule,
        SvFootballNs_Match_ScheduleModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SvFootballEntityModule {}
