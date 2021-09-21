import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Organisme } from 'src/app/models/organisme';
import { OrganismeService } from 'src/app/services/organisme.service';

@Component({
  selector: 'app-accueil-organisme',
  templateUrl: './accueil-organisme.component.html',
  styleUrls: ['./accueil-organisme.component.css']
})
export class AccueilOrganismeComponent implements OnInit {

  organisme: Organisme;
  nomOrganisme: string;
  id : number;

  constructor(private router: Router, public service: OrganismeService) { }



  ngOnInit(): void {
    this.id = parseInt(sessionStorage.getItem('ID'));
    if(this.id == null){
      this.router.navigate(['/login']);
    }
    else {
      this.service.findByIdOrganisme(this.id).subscribe(
        (data) => {
          this.organisme = data;
          this.nomOrganisme = this.organisme.nomOrganisme;
        }
      )
    }
  }


}
