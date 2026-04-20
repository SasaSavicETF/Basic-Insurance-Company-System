import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { KorisnikComponent } from './korisnik/korisnik.component';
import { PolisaComponent } from './polisa/polisa.component';
import { KupovinaComponent } from './kupovina/kupovina.component';
import { LogComponent } from './log/log.component';
import { LoginComponent } from './login/login.component';
import { AuthGuard } from './guards/auth.guard';

const routes: Routes = [
  { path: 'korisnici', component: KorisnikComponent, canActivate: [AuthGuard]  },
  { path: 'polise', component: PolisaComponent, canActivate: [AuthGuard] },
  { path: 'kupovine', component: KupovinaComponent, canActivate: [AuthGuard] },
  { path: 'logovi', component: LogComponent, canActivate: [AuthGuard] },
  { path: 'login', component: LoginComponent },
  { path: '', pathMatch: 'full', redirectTo: '/login' },
  { path: '**', redirectTo: '/login' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
