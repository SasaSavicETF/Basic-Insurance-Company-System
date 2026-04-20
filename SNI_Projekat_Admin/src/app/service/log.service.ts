import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Log } from '../model/log';
import { environment } from '../../environment/environment.development';

@Injectable({
  providedIn: 'root'
})
export class LogService {
  private baseUrl = `${environment.apiServerUrl}/admin/logovi`;

  constructor(private http: HttpClient) {}

  public getAllLogovi(): Observable<Log[]> {
    return this.http.get<Log[]>(this.baseUrl, { withCredentials: true });
  }
}
