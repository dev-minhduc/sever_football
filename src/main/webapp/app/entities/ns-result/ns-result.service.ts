import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { Ns_Result } from './ns-result.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class Ns_ResultService {

    private resourceUrl = SERVER_API_URL + 'api/ns-results';

    constructor(private http: Http) { }

    create(ns_Result: Ns_Result): Observable<Ns_Result> {
        const copy = this.convert(ns_Result);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(ns_Result: Ns_Result): Observable<Ns_Result> {
        const copy = this.convert(ns_Result);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Ns_Result> {
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
     * Convert a returned JSON object to Ns_Result.
     */
    private convertItemFromServer(json: any): Ns_Result {
        const entity: Ns_Result = Object.assign(new Ns_Result(), json);
        return entity;
    }

    /**
     * Convert a Ns_Result to a JSON which can be sent to the server.
     */
    private convert(ns_Result: Ns_Result): Ns_Result {
        const copy: Ns_Result = Object.assign({}, ns_Result);
        return copy;
    }
}
