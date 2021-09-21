import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Client } from 'src/app/models/client';
import { ClientService } from 'src/app/services/client.service';

@Component({
  selector: 'app-accueil-client',
  templateUrl: './accueil-client.component.html',
  styleUrls: ['./accueil-client.component.css']
})
export class AccueilClientComponent implements OnInit {

  client: Client;
  nomUtilisateur: string;
  id : number;

  constructor(private router: Router, public service: ClientService) { }



  ngOnInit(): void {
    this.id = parseInt(sessionStorage.getItem('ID'));
    if(this.id == null){
      this.router.navigate(['/login']);
    }
    else {
      this.service.findByIdClient(this.id).subscribe(
        (data) => {
          this.client = data;
          this.nomUtilisateur = this.client.nomUtilisateur;
        }
      )
    }
  }

}
