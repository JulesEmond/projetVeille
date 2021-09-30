import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Reservation } from 'src/app/models/reservation';
import { ReservationService } from 'src/app/services/reservation.service';

@Component({
  selector: 'app-reservations-organisme',
  templateUrl: './reservations-organisme.component.html',
  styleUrls: ['./reservations-organisme.component.css']
})
export class ReservationsOrganismeComponent implements OnInit {
  listReservations: Array<Reservation>;
  validMessage: string = '';

  constructor(private service: ReservationService, private route: Router) {}

  ngOnInit(): void {
      this.getAllReservations();
  }

  getAllReservations(): void {
    this.service.findAllReservations(parseInt(sessionStorage.getItem('ID'))).subscribe(
      (data) => {
        this.listReservations = data;
      },
      (err) => {
        console.log(err);
      }
    );
  }


}
