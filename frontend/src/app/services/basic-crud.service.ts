import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BasicCrud <T, ID> {
  constructor(protected http: HttpClient, protected url: string) {}

  saveClient(t: T): Observable<T> {
    return this.http.post<T>(this.url + '/client', t);
  }

  saveOrganisme(t: T): Observable<T> {
    return this.http.post<T>(this.url + '/organisme', t);
  }

  findAll(): Observable<T[]> {
    return this.http.get<T[]>(this.url);
  }

  findByIdClient(id: ID): Observable<T> {
    return this.http.get<T>(this.url + '/client/' + id);
  }

  findByIdOrganisme(id: ID): Observable<T> {
    return this.http.get<T>(this.url + '/organisme/' + id);
  }

  update(id: ID, t: T): Observable<T> {
    return this.http.put<T>(this.url + '/' + id, t, {});
  }

  deleteById(id: ID): Observable<T> {
    return this.http.delete<T>(this.url + '/' + id);
  }

  //Service extra
  login(courriel:string, motDePasse:string) : Observable<T>{
    return this.http.get<T>(this.url + '/' + courriel + '/' + motDePasse);
  }

}
