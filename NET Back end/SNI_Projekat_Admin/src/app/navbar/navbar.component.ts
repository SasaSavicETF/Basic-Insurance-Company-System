import { Component, OnInit } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { filter } from 'rxjs';
import { LoginService } from '../service/login.service';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css'],
  providers: [MessageService]
})
export class NavbarComponent implements OnInit {

  currentRoute!: string;
  showUpperDiv: boolean = false;
  showNavbar: boolean = false;
  showMenu: boolean = false;

  constructor(
    private router: Router,
    private loginService: LoginService,
    private messageService: MessageService
  ) {}

  ngOnInit(): void {
    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd)
    ).subscribe({
      next: () => {
        this.currentRoute = this.router.url;
        this.updateNavbarVisibility();
      },
      error: () => {
        this.messageService.add({ severity: 'error', summary: 'Greška', detail: 'Greška pri rutiranju.' });
      }
    });
  }

  updateNavbarVisibility(): void {
    if (this.currentRoute === '/login') {
      this.showUpperDiv = false;
      this.showNavbar = false;
    } else {
      this.showUpperDiv = true;
      this.showNavbar = true;
    }
  }

  toggleNavbar(): void {
    if (window.innerWidth < 768) {
      this.showNavbar = !this.showNavbar;
    }
  }

  showPaddingMenu(): void {
    this.showMenu = !this.showMenu;
  }

  logout(): void {
    this.showMenu = false;
    this.loginService.logoutUser();
    this.router.navigate(['/login']);
  }
}
