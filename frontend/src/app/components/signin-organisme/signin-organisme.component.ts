import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Organisme } from 'src/app/models/organisme';
import { OrganismeService } from 'src/app/services/organisme.service';

@Component({
  selector: 'app-signin-organisme',
  templateUrl: './signin-organisme.component.html',
  styleUrls: ['./signin-organisme.component.css']
})
export class SigninOrganismeComponent implements OnInit {
  validMessage:string;
  organisme:Organisme;

  signinOrganismeForm = new FormGroup({
    courriel : new FormControl('', Validators.required),
    motDePasse : new FormControl('', Validators.required),
    nomOrganisme : new FormControl('', Validators.required),
    numTelephone : new FormControl('', Validators.required),
  });

  constructor(private organismeService : OrganismeService, private router : Router) { }

  ngOnInit(): void {
    sessionStorage.clear();
  }

  onSubmit(){
    if(this.signinOrganismeForm.valid){
      this.organismeService.saveOrganisme(this.signinOrganismeForm.value).subscribe(
        (data) => {
          this.organisme = data;
        if (this.organisme != null){
          this.signinOrganismeForm.reset();
          sessionStorage.setItem('ID', data.id.toString());
          this.router.navigate(['/accueil-organisme']);
        } else {
          this.validMessage = 'Ce courriel est déjà utilisé pour un autre compte';
        }

        },
        (err) => {
          console.log(err);
          this.validMessage = "Erreur lors de la transmission de l'organisme";
        }
      );
    } else {
      this.validMessage = 'Veuillez remplir le formulaire avant de le soumettre!';
    }
  }

}
