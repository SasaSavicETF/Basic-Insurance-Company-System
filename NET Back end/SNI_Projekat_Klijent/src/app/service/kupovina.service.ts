import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Kupovina } from '../model/kupovina';
import { environment } from '../../environment/environment.development';

@Injectable({
  providedIn: 'root'
})
export class KupovinaService {
  private baseUrl = `${environment.apiServerUrl}/klijent/moje-kupovine`;

  constructor(private http: HttpClient) {}

  public getMyKupovine(): Observable<Kupovina[]> {
    return this.http.get<Kupovina[]>(this.baseUrl, { withCredentials: true });
  }
}
