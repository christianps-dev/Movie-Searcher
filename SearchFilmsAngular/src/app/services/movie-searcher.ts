import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { MovieDTO } from '../dto/moviedto';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class MovieSearch {

  movieAPIURL = "http://localhost:8080/home"

  constructor(private http : HttpClient) {
  }

  token: string | null = null;

  getMovie(title: any): Observable<MovieDTO>{

    let username = sessionStorage.getItem('username');
    let movieReq = {
      title,
      username
    }
    console.log(title);
    return this.http.post<MovieDTO>(this.movieAPIURL + "/movie", movieReq);

  }
}
