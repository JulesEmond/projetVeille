import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './components/header/header.component';
import { LoginComponent } from './components/login/login.component';
import { SigninClientComponent } from './components/signin-client/signin-client.component';
import { SigninOrganismeComponent } from './components/signin-organisme/signin-organisme.component';
import { HttpClientModule } from '@angular/common/http';
import { Error404Component } from './components/error404/error404.component';
import { HeaderOrganismeComponent } from './components/header-organisme/header-organisme.component';
import { AccueilClientComponent } from './components/accueil-client/accueil-client.component';
import { AccueilOrganismeComponent } from './components/accueil-organisme/accueil-organisme.component';
import { SanitizeHtmlPipe } from './pipes/sanitize-html.pipe';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    LoginComponent,
    SigninClientComponent,
    SigninOrganismeComponent,
    Error404Component,
    HeaderOrganismeComponent,
    AccueilClientComponent,
    AccueilOrganismeComponent,
    SanitizeHtmlPipe
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
