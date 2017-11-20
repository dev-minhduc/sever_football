import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { Ns_Livestream } from './ns-livestream.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class Ns_LivestreamService {

    private resourceUrl = SERVER_API_URL + 'api/ns-livestreams';

    constructor(private http: Http) { }

    create(ns_Livestream: Ns_Livestream): Observable<Ns_Livestream> {
        const copy = this.convert(ns_Livestream);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(ns_Livestream: Ns_Livestream): Observable<Ns_Livestream> {
        const copy = this.convert(ns_Livestream);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Ns_Livestream> {
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
     * Convert a returned JSON object to Ns_Livestream.
     */
    private convertItemFromServer(json: any): Ns_Livestream {
        const entity: Ns_Livestream = Object.assign(new Ns_Livestream(), json);
        return entity;
    }

    /**
     * Convert a Ns_Livestream to a JSON which can be sent to the server.
     */
    private convert(ns_Livestream: Ns_Livestream): Ns_Livestream {
        const copy: Ns_Livestream = Object.assign({}, ns_Livestream);
        return copy;
    }
}
