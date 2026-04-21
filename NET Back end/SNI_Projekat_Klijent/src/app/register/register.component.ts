import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { Registracija } from '../model/registracija';
import { RegistracijaService } from '../service/registracija.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
  providers: [MessageService]
})
export class RegisterComponent {
  constructor(private registracijaService: RegistracijaService,
    private messageService: MessageService, 
    private router: Router) {}

  onRegister(registracija: Registracija): void {
    this.registracijaService.register(registracija).subscribe({
      next: (response: string) => {
        this.messageService.add({ severity: 'info', summary: 'Obavještenje', detail: 'Registracija uspješna!' });
        this.router.navigate(['/login']); 
      },
      error: (error: HttpErrorResponse) => {
        this.messageService.add({ severity: 'error', summary: 'Greška', detail: 'Korisničko ime je zauzeto.' });
      }
    });
  }
}
