import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Organisme } from '../models/organisme';
import { BasicCrud } from './basic-crud.service';

@Injectable({
  providedIn: 'root'
})
export class OrganismeService extends BasicCrud<Organisme, Number>{

  constructor(http: HttpClient) { 
    super(http, 'http://localhost:9595/backend');
  }

  public logout(){
    sessionStorage.clear();
  }
}
