import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPonto } from 'app/shared/model/ponto.model';

type EntityResponseType = HttpResponse<IPonto>;
type EntityArrayResponseType = HttpResponse<IPonto[]>;

@Injectable({ providedIn: 'root' })
export class PontoService {
    public resourceUrl = SERVER_API_URL + 'api/pontos';

    constructor(private http: HttpClient) {}

    create(ponto: IPonto): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(ponto);
        return this.http
            .post<IPonto>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(ponto: IPonto): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(ponto);
        return this.http
            .put<IPonto>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http
            .get<IPonto>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPonto[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(ponto: IPonto): IPonto {
        const copy: IPonto = Object.assign({}, ponto, {
            validade: ponto.validade != null && ponto.validade.isValid() ? ponto.validade.format(DATE_FORMAT) : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.validade = res.body.validade != null ? moment(res.body.validade) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((ponto: IPonto) => {
            ponto.validade = ponto.validade != null ? moment(ponto.validade) : null;
        });
        return res;
    }
}
