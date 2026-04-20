import { Component, OnInit } from '@angular/core';
import { Polisa } from '../model/polisa';
import { PolisaService } from '../service/polisa.service';
import { MessageService } from 'primeng/api';
import { ActivatedRoute, Router } from '@angular/router';
import { loadStripe } from '@stripe/stripe-js'; 

@Component({
  selector: 'app-polisa',
  templateUrl: './polisa.component.html',
  styleUrls: ['./polisa.component.css'],
  providers: [MessageService]
})
export class PolisaComponent implements OnInit {
  polise: Polisa[] = [];
  stripePublicKey: string = "pk_test_51RafyBHIdO7MFScyZtz03KJLuHjM1geGsAql1lSlB6vBuD6R1V1s5v8ietgOLsQ7vbWrVweohXGaSWtio2IXPI5S00axJXwXHR";

  showDeactivated: boolean = false; 
  loading: boolean = false; 

  constructor(
    private polisaService: PolisaService,
    private messageService: MessageService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.ucitajPolise();

    this.route.queryParams.subscribe(params => {
      const success = params['success'];
      const sessionId = params['session_id'];
      const idPolise = params['idPolise'];

      if (success && sessionId && idPolise) {
        this.loading = true; 
        this.polisaService.zavrsiKupovinu(idPolise, sessionId).subscribe({
          next: () => {
            this.messageService.add({ severity: 'success', summary: 'Uspjeh', detail: 'Kupovina uspješno evidentirana.' });
            this.ucitajPolise();
            this.loading = false;  
          },
          error: () => {
            this.messageService.add({ severity: 'error', summary: 'Greška', detail: 'Kupovina nije evidentirana.' });
            this.loading = false; 
          }
        });
      }

      if (params['canceled']) {
        this.messageService.add({ severity: 'warn', summary: 'Otkazano', detail: 'Kupovina nije dovršena.' });
      }
    });
  }

  ucitajPolise(): void {
    this.polisaService.getAllPolise().subscribe({
      next: data => {
        this.polise = this.showDeactivated
        ? data
        : data.filter(p => p.aktivna);  
      },
      error: () => this.messageService.add({ severity: 'error', summary: 'Greška', detail: 'Greška pri učitavanju polisa.' })
    });
  }

  searchPolise(keyword: string): void {
    const results = this.polise.filter(p =>
      p.tip.toLowerCase().includes(keyword.toLowerCase()) ||
      p.opis.toLowerCase().includes(keyword.toLowerCase())
    );
    this.polise = results;
    if (!keyword) this.ucitajPolise();
  }

  async kupiPolisu(polisa: Polisa): Promise<void> {
    this.polisaService.kupiPolisu(polisa.idPolisa).subscribe({
      next: async (res) => {
        const stripe = await loadStripe(this.stripePublicKey); 
        if (stripe && res.sessionId) {
          await stripe.redirectToCheckout({ sessionId: res.sessionId });
        }
      },
      error: () => {
        this.messageService.add({ severity: 'error', summary: 'Greška', detail: 'Kupovina nije uspjela.' });
      }
    });
  }
}
