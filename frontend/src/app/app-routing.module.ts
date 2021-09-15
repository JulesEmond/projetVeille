import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Error404Component } from './components/error404/error404.component';
import { LoginComponent } from './components/login/login.component';
import { SigninClientComponent } from './components/signin-client/signin-client.component';
import { SigninOrganismeComponent } from './components/signin-organisme/signin-organisme.component';

const routes: Routes = [
  {path: 'login', component:LoginComponent},
  {path: 'signinClient', component:SigninClientComponent},
  {path: 'signinOrganisme', component:SigninOrganismeComponent},
  {path: '', redirectTo: 'login', pathMatch: 'full'},
  {path: '**', component:Error404Component}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
