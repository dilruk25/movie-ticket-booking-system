import { Component } from '@angular/core';
import {RouterOutlet} from '@angular/router';
import {MovieLandingComponent} from './components/movie-landing/movie-landing.component';

@Component({
  selector: 'app-root',
  template: `
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
      <a class="navbar-brand" href="#">TICKET Corner</a>
      <div class="collapse navbar-collapse">
        <ul class="navbar-nav">
          <li class="nav-item">
            <button class="nav-link btn btn-link" routerLink="/config">Configuration</button>
          </li>
          <li class="nav-item">
            <button class="nav-link btn btn-link" routerLink="/threads">Threads</button>
          </li>
          <li class="nav-item">
            <button class="nav-link btn btn-link" routerLink="/dashboard">Dashboard</button>
          </li>
        </ul>
      </div>
    </nav>
    <div class="container">
      <router-outlet></router-outlet>
      <app-movie-landing></app-movie-landing>
    </div>
  `,
  imports: [
    RouterOutlet,
    MovieLandingComponent
  ],
  styles: [`
    .nav-link {
      cursor: pointer;
    }
  `]
})
export class AppComponent {}
