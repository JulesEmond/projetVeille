import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Reservation } from '../models/reservation';
import { BasicCrud } from './basic-crud.service';

@Injectable({
  providedIn: 'root'
})
export class ReservationService extends BasicCrud<Reservation, Number>{

  constructor(http: HttpClient) { 
    super(http, 'http://localhost:9595/backend');
  }
}
