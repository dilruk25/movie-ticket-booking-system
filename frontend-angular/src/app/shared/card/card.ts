import { Component } from '@angular/core';
import { MatCard, MatCardAvatar, MatCardImage } from '@angular/material/card';
import { NgOptimizedImage } from '@angular/common';

@Component({
  selector: 'app-card',
  imports: [MatCard, MatCardImage, NgOptimizedImage],
  templateUrl: './card.html',
  styleUrl: './card.scss',
})
export class Card {}
