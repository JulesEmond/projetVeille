import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Client } from 'src/app/models/client';
import { ClientService } from 'src/app/services/client.service';

@Component({
  selector: 'app-signin-client',
  templateUrl: './signin-client.component.html',
  styleUrls: ['./signin-client.component.css']
})
export class SigninClientComponent implements OnInit {

  validMessage:string;
  client:Client;

  signinClientForm = new FormGroup({
    courriel : new FormControl('', Validators.required),
    motDePasse : new FormControl('', Validators.required),
    nomUtilisateur : new FormControl('', Validators.required),
    adresse : new FormControl('', Validators.required),
  });

  constructor(private clientService : ClientService, private router : Router) { }

  ngOnInit(): void {
  }

  onSubmit(){
    if(this.signinClientForm.valid){
      this.clientService.saveClient(this.signinClientForm.value).subscribe(
        (data) => {
          this.client = data;
          if (this.client != null){
            this.signinClientForm.reset();
            sessionStorage.setItem('ID', data.id.toString());
            this.router.navigate(['/accueil-client']);
          } else {
            this.validMessage = 'Ce courriel est déjà utilisé pour un autre compte';
          }

        },
        (err) => {
          console.log(err);
          this.validMessage = 'Erreur lors de la transmission du client';
        }
      );
    } else {
      this.validMessage = 'Veuillez remplir le formulaire avant de le soumettre!';
    }
  }

}
