import { BaseEntity } from './../../shared';

export class Ns_Match_Schedule implements BaseEntity {
    constructor(
        public id?: number,
        public team1?: string,
        public team2?: string,
        public thumbnail1?: string,
        public thumbnail2?: string,
        public satus?: boolean,
    ) {
        this.satus = false;
    }
}
