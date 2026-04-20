import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environment/environment.development';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { Verifikacija } from '../model/verfikacija';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  private baseUrl = environment.apiServerUrl;

  constructor(private http: HttpClient,
    private router: Router,
    private messageService: MessageService
  ) {}

  public loginUser(username: string, password: string): Observable<string> {
    return this.http.post(`${this.baseUrl}/auth/login`, 
        { korisnickoIme: username, lozinka: password },
      { withCredentials: true, responseType: 'text' });
  }

  public verifyCode(dto: Verifikacija): Observable<string> {
    return this.http.post(`${this.baseUrl}/auth/verifikacija-koda`, dto,
      { responseType: 'text', withCredentials: true }
    );
  }

  public logoutUser(): void {
    this.http.post(`${this.baseUrl}/auth/logout`, {}, { withCredentials: true }).subscribe({
      next: () => {
        this.router.navigate(['/login']);
      },
      error: () => {
        this.messageService.add({ severity: 'error', summary: 'Greška', detail: 'Greška prilikom odjave.' });
    }});
  }

  public checkAuthStatus(): Observable<boolean> {
    return this.http.get<boolean>(`${this.baseUrl}/auth/status`, { withCredentials: true });
  }
}
