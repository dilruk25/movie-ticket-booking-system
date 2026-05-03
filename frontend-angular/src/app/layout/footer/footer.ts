import { Component } from '@angular/core';
import { MatDivider } from '@angular/material/list';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-footer',
  imports: [MatDivider, RouterLink],
  templateUrl: './footer.html',
  styleUrl: './footer.scss',
})
export class Footer {}
