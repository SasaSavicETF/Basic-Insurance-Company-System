import { Component, OnInit } from '@angular/core';
import { Log } from '../model/log';
import { LogService } from '../service/log.service';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-log',
  templateUrl: './log.component.html',
  styleUrls: ['./log.component.css'],
  providers: [MessageService]
})
export class LogComponent implements OnInit {
  logovi: Log[] = [];
  sviLogovi: Log[] = [];

  constructor(private logService: LogService, private messageService: MessageService) {}

  ngOnInit(): void {
    this.getLogovi();
  }

  getLogovi(): void {
    this.logService.getAllLogovi().subscribe({
      next: data => {
        this.logovi = data;
        this.sviLogovi = data;
      },
      error: () => this.messageService.add({ severity: 'error', summary: 'Greška', detail: 'Greška pri učitavanju logova.' })
    });
  }

  searchLogovi(keyword: string): void {
    if (!keyword) {
      this.logovi = this.sviLogovi;
      return;
    }

    this.logovi = this.sviLogovi.filter(l =>
      l.detalji.toLowerCase().includes(keyword.toLowerCase()) ||
      l.akcija.toLowerCase().includes(keyword.toLowerCase())
    );
  }
}
