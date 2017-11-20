import { BaseEntity } from './../../shared';

export class Ns_Rank implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public thumbnail?: string,
        public battle?: number,
        public win?: number,
        public draw?: number,
        public lose?: number,
        public diff?: string,
        public point?: number,
        public satus?: boolean,
    ) {
        this.satus = false;
    }
}
