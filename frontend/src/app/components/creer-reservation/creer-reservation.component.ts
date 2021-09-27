import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Reservation } from 'src/app/models/reservation';
import { ReservationService } from 'src/app/services/reservation.service';

@Component({
  selector: 'app-creer-reservation',
  templateUrl: './creer-reservation.component.html',
  styleUrls: ['./creer-reservation.component.css']
})
export class CreerReservationComponent implements OnInit {
  validMessage:string;
  reservation:Reservation;

  creerReservationForm = new FormGroup({
    description : new FormControl('', Validators.required),
    nbPlaces : new FormControl('', Validators.required),
    lieu : new FormControl('', Validators.required),
    dateLimite : new FormControl('', Validators.required),
  });

  constructor(private service : ReservationService, private router : Router) { }

  ngOnInit(): void {
    
  }

  onSubmit(){
    if(this.creerReservationForm.valid){
      this.service.saveReservation(this.creerReservationForm.value, parseInt(sessionStorage.getItem('ID'))).subscribe(
        (data) => {
          this.reservation = data;
        if (this.reservation != null){
          this.creerReservationForm.reset();
          console.log(this.reservation);
          this.router.navigate(['/accueil-organisme']);
        } else {
          this.validMessage = "Date limite invalide : elle doit être après aujourd'hui";
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
