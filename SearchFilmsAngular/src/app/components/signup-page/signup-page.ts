import { AuthUser } from './../../services/auth-user/auth-user';
import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { RegisterUserDTO } from '../../dto/register-userdto';

@Component({
  selector: 'app-signup-page',
  imports: [ReactiveFormsModule],
  templateUrl: './signup-page.html',
  styleUrl: './signup-page.css',
})
export class SignupPage {

  RegisterForm = new FormGroup({
    username: new FormControl("", [Validators.required, Validators.nullValidator]),
    email: new FormControl("", [Validators.required, Validators.email, Validators.nullValidator]),
    password: new FormControl("", [Validators.required, Validators.nullValidator, Validators.minLength(8)])
  })

  constructor(private router: Router, private auth: AuthUser){}

  usernameUsed = sessionStorage.getItem("username");
  emailUsed = sessionStorage.getItem("email")

  signupUser(){
    const newUser = this.RegisterForm.value as RegisterUserDTO;
    this.auth.registerUser(newUser);
    this.emailUsed = sessionStorage.getItem("email") || "null";
    this.usernameUsed = sessionStorage.getItem("username") || "null";

  }

  sendLoginPage(){
    return this.router.navigate(["/login"])
  }
}
