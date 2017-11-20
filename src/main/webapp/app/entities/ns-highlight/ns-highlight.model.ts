import { BaseEntity } from './../../shared';

export class Ns_Highlight implements BaseEntity {
    constructor(
        public id?: number,
        public title?: string,
        public img?: string,
        public link?: string,
        public date?: any,
        public status?: boolean,
    ) {
        this.status = false;
    }
}
