import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environment/environment.development';
import { Polisa } from '../model/polisa';

@Injectable({
  providedIn: 'root'
})
export class PolisaService {
  private baseUrl = `${environment.apiServerUrl}/klijent`;

  constructor(private http: HttpClient) {}

  public getAllPolise(): Observable<Polisa[]> {
    return this.http.get<Polisa[]>(this.baseUrl + "/polise", { withCredentials: true });
  }

  public kupiPolisu(idPolise: number): Observable<{ sessionId: string }> {
    return this.http.post<{ sessionId: string }>(
      `${this.baseUrl}/stripe/checkout-session/${idPolise}`,
      {},
      { withCredentials: true }
    );
  }

  public zavrsiKupovinu(idPolise: number, sessionId: string): Observable<string> {
    return this.http.post(
      `${this.baseUrl}/kupi/${idPolise}?sessionId=${sessionId}`,
      {},
      { responseType: 'text', withCredentials: true }
    );
  }
}
