import { Component } from '@angular/core';
import { MatButton, MatIconButton } from '@angular/material/button';
import { RouterLink, RouterOutlet } from '@angular/router';
import { MatToolbar } from '@angular/material/toolbar';
import { MatSidenav, MatSidenavContainer, MatSidenavContent } from '@angular/material/sidenav';
import { MatListItem, MatNavList } from '@angular/material/list';
import { MatIcon } from '@angular/material/icon';

@Component({
  selector: 'app-navbar',
  imports: [
    MatButton,
    RouterLink,
    MatToolbar,
    MatSidenav,
    MatNavList,
    MatListItem,
    MatSidenavContent,
    RouterOutlet,
    MatSidenavContainer,
    MatIcon,
    MatIconButton,
  ],
  templateUrl: './navbar.html',
  styleUrl: './navbar.scss',
})
export class Navbar {}
