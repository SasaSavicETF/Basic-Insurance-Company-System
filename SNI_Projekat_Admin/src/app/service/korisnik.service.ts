import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Korisnik } from '../model/korisnik';
import { environment } from '../../environment/environment.development';
import { Registracija } from '../model/registracija';

@Injectable({
  providedIn: 'root'
})
export class KorisnikService {

  private baseUrl = `${environment.apiServerUrl}/admin`;

  constructor(private http: HttpClient) {}

  public getAllKorisnici(): Observable<Korisnik[]> {
    return this.http.get<Korisnik[]>(`${this.baseUrl}/klijenti`, { withCredentials: true });
  }

  public addAdmin(registracija: Registracija): Observable<string> {
    return this.http.post(`${this.baseUrl}/add`, registracija, { responseType: 'text', 
      withCredentials: true }); 
  }

  public updateKorisnik(korisnik: Korisnik): Observable<string> {
    return this.http.put(`${this.baseUrl}/klijenti/${korisnik.idKorisnik}`, korisnik, 
      { responseType: 'text', withCredentials: true });
  }

  public deleteKorisnikById(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/klijenti/${id}`, { withCredentials: true });
  }
}
