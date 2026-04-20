import { Component, OnInit } from '@angular/core';
import { KorisnikService } from '../service/korisnik.service';
import { HttpErrorResponse } from '@angular/common/http';
import { MessageService } from 'primeng/api';
import { NgForm } from '@angular/forms';
import { Korisnik } from '../model/korisnik';
import { Registracija } from '../model/registracija';

@Component({
  selector: 'app-korisnik',
  templateUrl: './korisnik.component.html',
  styleUrls: ['./korisnik.component.css'],
  providers: [MessageService]
})
export class KorisnikComponent implements OnInit {

  uloge: string[] = ["ADMIN", "KLIJENT"];
  korisnici: Korisnik[] = [];

  showAddAdminDialog: boolean = false; 
  showUpdateKorisnikDialog: boolean = false;
  showDeleteKorisnikDialog: boolean = false;

  korisnikToUpdate: Korisnik | undefined;
  korisnikToDelete: Korisnik | undefined;

  selectedUloga: string | undefined;

  constructor(
    private korisnikService: KorisnikService,
    private messageService: MessageService
  ) {}

  ngOnInit(): void {
    this.getKorisnici();
  }

  getKorisnici(): void {
    this.korisnikService.getAllKorisnici().subscribe({
      next: (response: Korisnik[]) => {
        this.korisnici = response;
      },
      error: (error: HttpErrorResponse) => {
        this.messageService.add({ severity: 'error', summary: 'Greška', detail: 'Greška pri učitavanju korisnika.' });
      }
    });
  }

  onAddAdmin(registracija: Registracija): void {
    this.korisnikService.addAdmin(registracija).subscribe({
      next: (response) => {
        this.messageService.add({ severity: 'success', summary: 'Uspjeh', detail: response });
        this.getKorisnici();
        this.showAddAdminDialog = false;
      },
      error: () => this.messageService.add({ severity: 'error', summary: 'Greška', detail: 'Registrovanje nije uspjelo.' })
    });
  }

  onUpdateKorisnik(korisnik: Korisnik): void {
    this.korisnikService.updateKorisnik(korisnik).subscribe({
      next: (response: string) => {
        this.messageService.add({ severity: 'success', summary: 'Obavještenje', detail: response });
        this.getKorisnici();
        this.showUpdateKorisnikDialog = false;
      },
      error: () => {
        this.messageService.add({ severity: 'error', summary: 'Greška', detail: 'Greška pri ažuriranju korisnika.' });
      }
    });
  }

  onDeleteKorisnik(): void {
    if (this.korisnikToDelete?.idKorisnik) {
      this.korisnikService.deleteKorisnikById(this.korisnikToDelete.idKorisnik).subscribe({
        next: () => {
          this.messageService.add({ severity: 'success', summary: 'Obavještenje', detail: 'Korisnik uspješno uklonjen.' });
          this.getKorisnici();
          this.showDeleteKorisnikDialog = false;
        },
        error: () => {
          this.messageService.add({ severity: 'error', summary: 'Greška', detail: 'Greška pri uklanjanju korisnika.' });
        }
      });
    }
  }

  openAddAdminDialog(): void {
    this.showAddAdminDialog = true; 
  }

  openUpdateKorisnikDialog(korisnik: Korisnik): void {
    this.korisnikToUpdate = { ...korisnik }; 
    console.log(this.korisnikToUpdate.ime); 
    this.showUpdateKorisnikDialog = true; 
  }

  openDeleteKorisnikDialog(korisnik: Korisnik): void {
    this.korisnikToDelete = { ...korisnik }; 
    this.showDeleteKorisnikDialog = true; 
  }

  declareRoleIcon(korisnik: Korisnik) {
    if (korisnik.uloga === 'ADMIN') {
      return 'pi pi-lock';
    } else if (korisnik.uloga === 'KLIJENT') {
      return 'pi pi-user';
    } else {
      return '';
    }
  }

  public searchKorisnici(key: string): void {
    const results: Korisnik[] = [];
    for (const korisnik of this.korisnici) {
      if (
        korisnik.korisnickoIme.toLowerCase().includes(key.toLowerCase()) ||
        korisnik.ime.toLowerCase().includes(key.toLowerCase()) ||
        korisnik.prezime.toLowerCase().includes(key.toLowerCase())
      ) {
        results.push(korisnik);
      }
    }
    this.korisnici = results;
    if (!key) {
      this.getKorisnici();
    }
  }
}
