import { BaseEntity } from './../../shared';

export class Ns_Livestream implements BaseEntity {
    constructor(
        public id?: number,
        public title?: string,
        public link?: string,
        public thumb1?: string,
        public thumb2?: string,
        public status?: boolean,
    ) {
        this.status = false;
    }
}
