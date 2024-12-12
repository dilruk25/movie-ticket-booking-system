import { Component } from '@angular/core';
import {RouterOutlet} from '@angular/router';

@Component({
  selector: 'app-root',
  imports: [
    RouterOutlet
  ],
  template: `
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
      <a class="navbar-brand" href="#">Ticket Corner</a>
      <div class="collapse navbar-collapse">
        <ul class="navbar-nav">
          <li class="nav-item"><a class="nav-link" routerLink="/config">Configuration</a></li>
          <li class="nav-item"><a class="nav-link" routerLink="/threads">Threads</a></li>
          <li class="nav-item"><a class="nav-link" routerLink="/dashboard">Dashboard</a></li>
        </ul>
      </div>
    </nav>
    <div class="container">
      <router-outlet></router-outlet>
    </div>
  `
})
export class AppComponent { }
