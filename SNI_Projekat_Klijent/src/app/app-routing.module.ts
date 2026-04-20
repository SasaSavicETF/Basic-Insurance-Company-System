import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { KupovinaComponent } from './kupovina/kupovina.component';
import { PolisaComponent } from './polisa/polisa.component';
import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { AuthGuard } from './guards/auth.guard';

const routes: Routes = [
  { path: 'kupovine', component: KupovinaComponent, canActivate: [AuthGuard] },
  { path: 'polise', component: PolisaComponent, canActivate: [AuthGuard] },
  { path: 'register', component: RegisterComponent },
  { path: 'login', component: LoginComponent },
  { path: '', pathMatch: 'full', redirectTo: '/login' },
  { path: '**', redirectTo: '/login' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
