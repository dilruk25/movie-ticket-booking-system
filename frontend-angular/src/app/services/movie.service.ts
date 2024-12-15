import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { MovieDTO } from '../models/movie.model';

@Injectable({
  providedIn: 'root'
})
export class MovieService {
  private baseUrl = 'http://localhost:8080/api/v1/movies';

  constructor(private http: HttpClient) {}

  // Fetch all movies
  getAllMovies(): Observable<MovieDTO[]> {
    return this.http.get<MovieDTO[]>(this.baseUrl);
  }
}
