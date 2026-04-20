import { Component, OnInit } from '@angular/core';
import { KupovinaService } from '../service/kupovina.service';
import { MessageService } from 'primeng/api';
import { Kupovina } from '../model/kupovina';
import { Polisa } from '../model/polisa';

@Component({
  selector: 'app-kupovinas',
  templateUrl: './kupovina.component.html',
  styleUrls: ['./kupovina.component.css'],
  providers: [MessageService]
})
export class KupovinaComponent implements OnInit {
  kupovine: Kupovina[] = [];
  sveKupovine: Kupovina[] = [];

  showPolisaInfoDialog: boolean = false; 
  infoPolisa: Polisa | undefined; 

  constructor(private kupovinaService: KupovinaService, private messageService: MessageService) {}

  ngOnInit(): void {
    this.getMyKupovine();
  }

  getMyKupovine(): void {
    this.kupovinaService.getMyKupovine().subscribe({
      next: data => {
        this.kupovine = data; 
        this.sveKupovine = data;
      },
      error: () => this.messageService.add({ severity: 'error', summary: 'Greška', detail: 'Greška pri učitavanju kupovina.' })
    });
  }

  searchKupovine(keyword: string): void {
    if (!keyword) {
      this.kupovine = this.sveKupovine;
      return;
    }

    this.kupovine = this.sveKupovine.filter(k =>
      k.korisnik?.korisnickoIme.toLowerCase().includes(keyword.toLowerCase()) ||
      k.polisa?.tip.toLowerCase().includes(keyword.toLowerCase())
    );
  }

  openPolisaInfoDialog(kupovina: Kupovina): void {
    this.showPolisaInfoDialog = true; 
    this.infoPolisa = { ...kupovina.polisa };  
  }

  closePolisaInfoDialog(): void {
    this.showPolisaInfoDialog = false; 
  }
}
