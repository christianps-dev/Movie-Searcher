import { MovieDTO } from './../../dto/moviedto';
import { Component } from '@angular/core';
import { NgOptimizedImage } from '@angular/common';
import { Router } from '@angular/router';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MovieSearch } from '../../services/movie-searcher';

@Component({
  selector: 'app-home',
  imports: [NgOptimizedImage, ReactiveFormsModule],
  templateUrl: './home.html',
  styleUrl: './home.css',
})
export class Home {

  MovieForm = new FormGroup({
    title: new FormControl("", [Validators.required, Validators.nullValidator])
  })

  movie?: MovieDTO;

  constructor(private router: Router, private getMovie: MovieSearch){
  }

  favoritesPage(){
    return this.router.navigate(["/favorites"]);
  }

  searchMovie(){
    const movieTitle = this.MovieForm.value.title;
    this.getMovie.getMovie(movieTitle).subscribe({
      next: (nxt) => {this.movie = nxt as MovieDTO,
        console.log(nxt.genre)
      },
      error: (err) => console.log("Error in movie query", err)
    })
    console.log(this.movie?.country)
    return;
  }
}
