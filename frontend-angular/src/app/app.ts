import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Navbar } from './layout/navbar/navbar';
import { Footer } from './layout/footer/footer';
import { MovieList } from './features/movies/components/movie-list/movie-list';
import { Card } from './shared/card/card';

@Component({
  selector: 'app-root',
  imports: [Navbar, Footer, MovieList, Card],
  templateUrl: './app.html',
  styleUrl: './app.scss',
})
export class App {
  protected readonly title = signal('ticket-corner');
}
