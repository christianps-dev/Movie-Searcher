import { LoginUserDTO } from './../../dto/login-userdto';
import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthUser } from '../../services/auth-user/auth-user';

@Component({
  selector: 'app-login-page',
  imports: [ReactiveFormsModule],
  templateUrl: './login-page.html',
  styleUrl: './login-page.css',
})
export class LoginPage {

  LoginForm = new FormGroup({
    username: new FormControl("", [Validators.required, Validators.nullValidator]),
    password: new FormControl("", [Validators.required, Validators.nullValidator, Validators.minLength(8)])
  })

  constructor(private router: Router, private authUser: AuthUser){}

  userToken = sessionStorage.getItem('token');

  siginUser(){
    this.userToken = sessionStorage.getItem('token');
    const userLogin = this.LoginForm.value as LoginUserDTO;
    this.authUser.siginUser(userLogin);
  }

  sendSignupPage(){
    return this.router.navigate(["/signup"])
  }

}
