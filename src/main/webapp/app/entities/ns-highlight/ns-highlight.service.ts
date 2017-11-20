import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Ns_Highlight } from './ns-highlight.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class Ns_HighlightService {

    private resourceUrl = SERVER_API_URL + 'api/ns-highlights';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(ns_Highlight: Ns_Highlight): Observable<Ns_Highlight> {
        const copy = this.convert(ns_Highlight);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(ns_Highlight: Ns_Highlight): Observable<Ns_Highlight> {
        const copy = this.convert(ns_Highlight);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Ns_Highlight> {
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
     * Convert a returned JSON object to Ns_Highlight.
     */
    private convertItemFromServer(json: any): Ns_Highlight {
        const entity: Ns_Highlight = Object.assign(new Ns_Highlight(), json);
        entity.date = this.dateUtils
            .convertLocalDateFromServer(json.date);
        return entity;
    }

    /**
     * Convert a Ns_Highlight to a JSON which can be sent to the server.
     */
    private convert(ns_Highlight: Ns_Highlight): Ns_Highlight {
        const copy: Ns_Highlight = Object.assign({}, ns_Highlight);
        copy.date = this.dateUtils
            .convertLocalDateToServer(ns_Highlight.date);
        return copy;
    }
}
