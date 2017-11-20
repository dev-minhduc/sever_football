import { BaseEntity } from './../../shared';

export class Ns_Result implements BaseEntity {
    constructor(
        public id?: number,
        public team1?: string,
        public team2?: string,
        public result?: string,
        public thumbnail1?: string,
        public thumbnail2?: string,
        public satus?: boolean,
    ) {
        this.satus = false;
    }
}
