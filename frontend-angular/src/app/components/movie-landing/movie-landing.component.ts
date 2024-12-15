import { Component, OnInit } from '@angular/core';
import { MovieService } from '../../services/movie.service';
import { MovieDTO } from '../../models/movie.model';
import {RouterLink} from '@angular/router';
import {NgForOf} from '@angular/common';

@Component({
  selector: 'app-movie-landing',
  templateUrl: './movie-landing.component.html',
  imports: [
    RouterLink,
    NgForOf
  ],
  styleUrls: ['./movie-landing.component.css'] // Optional: Create a CSS file for styling
})
export class MovieLandingComponent implements OnInit {
  movies: MovieDTO[] = [];

  constructor(private movieService: MovieService) {}

  ngOnInit(): void {
    this.fetchMovies();
  }

  fetchMovies(): void {
    this.movieService.getAllMovies().subscribe({
      next: (movies) => {
        this.movies = movies;
      },
      error: (error) => {
        console.error('Error fetching movies:', error);
        // Handle the error, e.g., display an error message to the user
      }
    });
  }
}
