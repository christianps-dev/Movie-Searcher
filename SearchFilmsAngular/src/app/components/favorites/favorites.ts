import { MovieSearch } from './../../services/movie-searcher';
import { Component } from '@angular/core';
import { FavoritesMoviesDTO } from '../../dto/favorites-moviedto';
import { FormControl, FormGroup, ɵInternalFormsSharedModule, ReactiveFormsModule } from "@angular/forms";
import { Router } from '@angular/router';
import { Footer } from "../footer/footer";
import { Header } from "../header/header";

@Component({
  selector: 'app-favorites',
  imports: [ɵInternalFormsSharedModule, ReactiveFormsModule, Footer, Header],
  templateUrl: './favorites.html',
  styleUrl: './favorites.css',
})
export class Favorites {

  FavoritesMovies = new FormGroup({
    genre: new FormControl("")
  });

  constructor(private router : Router, private searcher : MovieSearch) {

  }

  favoritesMovies?: FavoritesMoviesDTO[];

  getFavorites(){
    let username = this.FavoritesMovies.value.genre;
    return this.searcher.getFavorites(username).subscribe({
      next: (nxt) => this.favoritesMovies = nxt,
      error: (err) => console.log("Error whith query ", err)
    })
  }

  sendHomePage(){
    return this.router.navigate(["/home"])
  }

}
