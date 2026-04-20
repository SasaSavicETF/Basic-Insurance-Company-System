import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations'; 

import { ButtonModule } from 'primeng/button';
import { DialogModule } from 'primeng/dialog';
import { InputTextModule } from 'primeng/inputtext';
import { TableModule } from 'primeng/table';
import { DropdownModule } from 'primeng/dropdown';
import { ToastModule } from 'primeng/toast';
import { AvatarModule } from 'primeng/avatar';
import { FileUploadModule } from 'primeng/fileupload';
import { TabViewModule } from 'primeng/tabview';
import { InputNumberModule } from 'primeng/inputnumber';
import { TooltipModule } from 'primeng/tooltip';
import { CheckboxModule } from 'primeng/checkbox'; 
import { MessageService } from 'primeng/api';



import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { KorisnikComponent } from './korisnik/korisnik.component';
import { NavbarComponent } from './navbar/navbar.component';
import { LoginComponent } from './login/login.component';
import { PolisaComponent } from './polisa/polisa.component';
import { KupovinaComponent } from './kupovina/kupovina.component';
import { LogComponent } from './log/log.component';


@NgModule({
  declarations: [
    AppComponent,
    KorisnikComponent,
    NavbarComponent,
    LoginComponent,
    PolisaComponent,
    KupovinaComponent,
    LogComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule,
    ButtonModule,
    DialogModule,
    InputTextModule,
    TableModule,
    DropdownModule,
    ToastModule,
    AvatarModule,
    FileUploadModule,
    TabViewModule,
    InputNumberModule,
    TooltipModule,
    CheckboxModule,
    BrowserAnimationsModule
  ],
  providers: [MessageService],
  bootstrap: [AppComponent]
})
export class AppModule { }
