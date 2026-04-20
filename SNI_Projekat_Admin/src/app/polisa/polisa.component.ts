import { Component, OnInit } from '@angular/core';
import { MessageService } from 'primeng/api';
import { PolisaService } from '../service/polisa.service';
import { Polisa } from '../model/polisa';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-polisa',
  templateUrl: './polisa.component.html',
  styleUrls: ['./polisa.component.css'],
  providers: [MessageService]
})
export class PolisaComponent implements OnInit {

  polise: Polisa[] = [];

  showAddPolisaDialog = false;
  showUpdatePolisaDialog = false;
  showDeletePolisaDialog = false;

  showDeactivated: boolean = false;

  polisaToUpdate: Polisa | undefined;
  polisaToDeactivate: Polisa | undefined;

  constructor(private polisaService: PolisaService, private messageService: MessageService) {}

  ngOnInit(): void {
    this.getPolise();
  }

  getPolise(): void { 
    this.polisaService.getAllPolise().subscribe({
      next: data => {
        this.polise = this.showDeactivated
        ? data
        : data.filter(p => p.aktivna);  
      },
      error: () => this.messageService.add({ severity: 'error', summary: 'Greška', detail: 'Greška pri učitavanju polisa.' })
    });
  }

  onAddPolisa(form: NgForm): void {
    this.polisaService.addPolisa(form.value).subscribe({
      next: () => {
        this.messageService.add({ severity: 'success', summary: 'Uspjeh', detail: 'Polisa uspješno dodana.' });
        this.getPolise();
        this.showAddPolisaDialog = false;
      },
      error: () => this.messageService.add({ severity: 'error', summary: 'Greška', detail: 'Dodavanje nije uspjelo.' })
    });
  }

  onUpdatePolisa(polisa: Polisa): void { 
    this.polisaService.updatePolisa(polisa).subscribe({
      next: () => {
        this.messageService.add({ severity: 'success', summary: 'Uspjeh', detail: 'Polisa uspješno ažurirana.' });
        this.getPolise();
        this.showUpdatePolisaDialog = false;
      },
      error: () => this.messageService.add({ severity: 'error', summary: 'Greška', detail: 'Ažuriranje nije uspjelo.' })
    });
  }

  onDeactivatePolisa(): void {
    if (this.polisaToDeactivate)
      this.polisaService.deactivatePolisa(this.polisaToDeactivate.idPolisa).subscribe({
        next: (response) => {
          this.messageService.add({ severity: 'success', summary: 'Uspjeh', detail: response });
          this.getPolise();
          this.showDeletePolisaDialog = false;
        },
        error: () => this.messageService.add({ severity: 'error', summary: 'Greška', detail: 'Deaktiviranje nije uspjelo.' })
      });
  }

  openAddPolisaDialog(): void {
    this.showAddPolisaDialog = true;
  }

  openUpdatePolisaDialog(polisa: Polisa): void {
    this.polisaToUpdate = { ...polisa };
    this.showUpdatePolisaDialog = true;
  }

  openDeletePolisaDialog(polisa: Polisa): void {
    this.polisaToDeactivate = polisa;
    this.showDeletePolisaDialog = true;
  }

  closeDeletePolisaDialog(): void {
    this.showDeletePolisaDialog = false;
  }

  searchPolise(query: string): void {
    const filtered = this.polise.filter(p =>
      p.tip.toLowerCase().includes(query.toLowerCase()) ||
      p.opis.toLowerCase().includes(query.toLowerCase())
    );
    this.polise = filtered;
    if (!query) this.getPolise();
  }
}
