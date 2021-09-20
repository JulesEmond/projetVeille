import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Client } from '../models/client';
import { BasicCrud } from './basic-crud.service';

@Injectable({
  providedIn: 'root'
})
export class ClientService extends BasicCrud<Client, Number>{

  constructor(http: HttpClient) { 
    super(http, 'http://localhost:9595/backend');
  }

  public logout(){
    sessionStorage.clear();
  }
}
