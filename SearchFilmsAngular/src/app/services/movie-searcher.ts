import { FavoritesMoviesDTO } from './../dto/favorites-moviedto';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { MovieDTO } from '../dto/moviedto';
import { Observable } from 'rxjs';
import { enviroment } from '../enviroment';

@Injectable({
  providedIn: 'root',
})
export class MovieSearch {

  movieAPIURL = enviroment.apiURL + "/home"

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

  getFavorites(genre: any): Observable<FavoritesMoviesDTO[]>{
    let username = sessionStorage.getItem('username');
    let favoriteReq = {
      genre,
      username
    }
    return this.http.post<FavoritesMoviesDTO[]>(this.movieAPIURL + "/favorites", favoriteReq)
  }
}
