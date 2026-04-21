import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environment/environment.development';
import { Registracija } from '../model/registracija';

@Injectable({
  providedIn: 'root'
})
export class RegistracijaService {
  private baseUrl = `${environment.apiServerUrl}/auth/register`;

  constructor(private http: HttpClient) {}

  public register(register: Registracija): Observable<string> {
    return this.http.post(this.baseUrl, register, { responseType: 'text', withCredentials: true });
  }
}
