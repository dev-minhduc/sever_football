import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { Ns_Match_Schedule } from './ns-match-schedule.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class Ns_Match_ScheduleService {

    private resourceUrl = SERVER_API_URL + 'api/ns-match-schedules';

    constructor(private http: Http) { }

    create(ns_Match_Schedule: Ns_Match_Schedule): Observable<Ns_Match_Schedule> {
        const copy = this.convert(ns_Match_Schedule);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(ns_Match_Schedule: Ns_Match_Schedule): Observable<Ns_Match_Schedule> {
        const copy = this.convert(ns_Match_Schedule);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Ns_Match_Schedule> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to Ns_Match_Schedule.
     */
    private convertItemFromServer(json: any): Ns_Match_Schedule {
        const entity: Ns_Match_Schedule = Object.assign(new Ns_Match_Schedule(), json);
        return entity;
    }

    /**
     * Convert a Ns_Match_Schedule to a JSON which can be sent to the server.
     */
    private convert(ns_Match_Schedule: Ns_Match_Schedule): Ns_Match_Schedule {
        const copy: Ns_Match_Schedule = Object.assign({}, ns_Match_Schedule);
        return copy;
    }
}
