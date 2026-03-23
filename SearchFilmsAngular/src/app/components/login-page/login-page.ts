import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LoginUserDTO } from '../../dto/login-userdto';
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

  siginUser(){
    const userLogin = this.LoginForm.value as LoginUserDTO;
    this.authUser.siginUser(userLogin);
  }

  sendSignupPage(){
    return this.router.navigate(["/register"])
  }

}
