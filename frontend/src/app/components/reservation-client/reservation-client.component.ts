import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Reservation } from 'src/app/models/reservation';
import { ClientService } from 'src/app/services/client.service';
import { ReservationService } from 'src/app/services/reservation.service';

@Component({
  selector: 'app-reservation-client',
  templateUrl: './reservation-client.component.html',
  styleUrls: ['./reservation-client.component.css']
})
export class ReservationClientComponent implements OnInit {
  
  listReservations: Array<Reservation>;
  validMessage: string = '';

  constructor(private reservationService: ReservationService, private clientService: ClientService, private router: Router) {}

  ngOnInit(): void {
      this.getAllReservations();
  }

  getAllReservations(): void {
    this.reservationService.listeReservationsDispo(parseInt(sessionStorage.getItem('ID'))).subscribe(
      (data) => {
        this.listReservations = data;
      },
      (err) => {
        console.log(err);
      }
    );
  }

  public reserver(idReservation : number) {
    console.log("Reservation effectue")
  }

}
