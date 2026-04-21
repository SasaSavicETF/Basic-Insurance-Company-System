import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Polisa } from '../model/polisa';
import { Observable } from 'rxjs';
import { environment } from '../../environment/environment.development';

@Injectable({
  providedIn: 'root'
})
export class PolisaService {

  private baseUrl = `${environment.apiServerUrl}/admin/polise`;

  constructor(private http: HttpClient) {}

  public getAllPolise(): Observable<Polisa[]> {
    return this.http.get<Polisa[]>(this.baseUrl, { withCredentials: true });
  }

  public addPolisa(polisa: Polisa): Observable<string> {
    return this.http.post(this.baseUrl, polisa, { responseType: 'text', withCredentials: true });
  }

  public updatePolisa(polisa: Polisa): Observable<string> {
    return this.http.put(`${this.baseUrl}/${polisa.idPolisa}`, polisa, { responseType: 'text', withCredentials: true });
  }

  public deactivatePolisa(id: number): Observable<string> {
    return this.http.delete(`${this.baseUrl}/${id}`, { responseType: 'text', withCredentials: true });
  }
}
