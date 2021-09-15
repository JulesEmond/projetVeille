import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  
  validMessage:string = '';
  loginForm = new FormGroup({
    courriel : new FormControl('', Validators.required),
    motDePasse : new FormControl('', Validators.required)
  });
  
  constructor() { }

  ngOnInit(): void {
  }

  public onSubmit(){
    
  }
}
