import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Kupovina } from '../model/kupovina';
import { environment } from '../../environment/environment.development';

@Injectable({
  providedIn: 'root'
})
export class KupovinaService {
  private baseUrl = `${environment.apiServerUrl}/admin/kupovine`;

  constructor(private http: HttpClient) {}

  public getAllKupovine(): Observable<Kupovina[]> {
    return this.http.get<Kupovina[]>(this.baseUrl, { withCredentials: true });
  }
}
