import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Client } from 'src/app/models/client';
import { Organisme } from 'src/app/models/organisme';
import { User } from 'src/app/models/user';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  
  validMessage:string = '';
  user:User;
  type:string = '';

  loginForm = new FormGroup({
    courriel : new FormControl('', Validators.required),
    motDePasse : new FormControl('', Validators.required)
  });
  
  constructor(private userService : UserService, private router : Router) { }

  ngOnInit(): void {
  }

  public onSubmit(){
    if(this.loginForm.valid){
      this.userService.login(this.loginForm.get('courriel').value, this.loginForm.get('motDePasse').value).subscribe(
        (data) => {
          this.loginForm.reset();
          this.user = data;
            if(this.user != null){
              sessionStorage.setItem('ID', this.user.id.toString());
                  console.log(this.type);
                  if(this.isClient(this.user)){
                    this.router.navigate(['/accueil-client']);
                  } else if (this.isOrganisme(this.user)){
                    this.router.navigate(['/accueil-organisme']);
                  } else {
                    this.validMessage = 'Erreur lors de la connexion';
                  }
                }
            }
        );
        (err) => {
          console.log(err);
          this.validMessage = 'Erreur lors de la connexion';
        }
      } else {
        this.validMessage = 'Veuillez remplir le formulaire avant de le soumettre!';
      }
  }

  isClient(user: Client | User) : user is Client {
    console.log("client");
    return (<Client>user).nomUtilisateur !== undefined;
  }

  isOrganisme(user: Organisme | User) : user is Organisme {
    console.log("organisme");
    return (<Organisme>user).nomOrganisme!== undefined;
  }
}
