import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../models/user';
import { BasicCrud } from './basic-crud.service';

@Injectable({
  providedIn: 'root'
})
export class UserService extends BasicCrud<User, Number>{

  constructor(http: HttpClient) { 
    super(http, 'http://localhost:9595/backend');
  }
}
