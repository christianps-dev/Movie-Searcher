import { enviroment } from './../../enviroment';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { UserResponseDTO } from '../../dto/user-responsedto';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class AuthUser {

  authUserURL = enviroment.apiURL + "/auth"

  constructor(private http: HttpClient, private router: Router){}

  siginUser(user: any){

    this.http.post<UserResponseDTO>(this.authUserURL + "/login", user).subscribe({
      next: (nxt) => { sessionStorage.setItem("token", nxt.token),
                       sessionStorage.setItem("username", nxt.username),
                       sessionStorage.setItem("email", nxt.email),
                       console.log("username: " + sessionStorage.getItem("username")),
                       this.router.navigate(["/home"],
                       )
      },
      error: (err) => {console.log("Error: " + err)
                       sessionStorage.setItem("token", "Not authenticated"),
                       sessionStorage.setItem("username", "Not authenticated")
      },
    })
  }

  registerUser(user: any){

    this.http.post<UserResponseDTO>(this.authUserURL + "/register", user).subscribe({
      next: (nxt) => { sessionStorage.setItem("token", nxt.token),
                       sessionStorage.setItem("username", nxt.username),
                       sessionStorage.setItem("email", nxt.email),
                       console.log("username: " + sessionStorage.getItem("username"))
                       this.router.navigate(["/home"])

      },
      error: (err) => console.log("Error while registering", err)

    })
  }
}
