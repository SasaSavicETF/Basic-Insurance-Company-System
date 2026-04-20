import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from '../service/login.service';
import { MessageService } from 'primeng/api';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  providers: [MessageService]
})
export class LoginComponent implements OnInit {
  username: string = "";
  password: string = "";
  responseCode: string = ""; 
  verificationCode: string = ""; 
  showVerificationDialog: boolean = false;
  showLoginButton: boolean = true; 

  constructor(
    private router: Router,
    private loginService: LoginService,
    private messageService: MessageService
  ) {}

  ngOnInit(): void {}

  login(): void {
    this.loginService.loginUser(this.username, this.password).subscribe({
      next: (response: string) => {
        this.responseCode = response; 
        this.messageService.add({ severity: 'info', summary: 'Verifikacija', detail: 'Unesite verifikacioni kod poslan na email.' });
        this.showLoginButton = false; 
        this.showVerificationDialog = true;
      },
      error: (error: HttpErrorResponse) => {
        this.messageService.add({ severity: 'error', summary: 'Greška', detail: 'Neispravno korisničko ime ili lozinka.' });
      }
    });
  }

  loginOrVerify(): void {
    if(this.showLoginButton) {
      this.login(); 
    } else {
      this.openCodeDialog(); 
    }
  }

  openCodeDialog(): void {
    this.showVerificationDialog = true; 
  }

  closeCodeDialog(): void {
    this.showVerificationDialog = false;  
  }

  verifyCode(): void {
    if(this.verificationCode.length !== 6) {
      alert("Niste unijeli 6 cifri."); 
      return; 
    }
    this.loginService.verifyCode({
      korisnickoIme: this.username,
      kod: this.verificationCode,
      vrijediDo: this.responseCode
    }).subscribe({
      next: () => {
        this.messageService.add({ severity: 'success', summary: 'Uspjeh', detail: 'Uspješna autentikacija.' });
        this.showVerificationDialog = false;
        this.router.navigate(['/polise']);
      },
      error: () => {
        this.messageService.add({ severity: 'error', summary: 'Neuspjeh', detail: 'Verifikacioni kod nije ispravan.' });
      }
    });
  }

  registerPage(): void {
    this.router.navigate(['/register']); 
  }
}
