import { Component } from '@angular/core';
import { Card } from '../../../../shared/card/card';

@Component({
  selector: 'app-movie-list',
  imports: [Card],
  templateUrl: './movie-list.html',
  styleUrl: './movie-list.scss',
})
export class MovieList {}
