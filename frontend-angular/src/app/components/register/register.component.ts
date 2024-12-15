import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { UserRequest } from '../../models/user-request.model';
import { Router } from '@angular/router';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-register',
  imports: [
    FormsModule
  ],
  templateUrl: './register.component.html'
})
export class RegisterComponent {
  userRequest: UserRequest = {
    email: '',
    password: '',
    fullName: ''
  };

  constructor(private authService: AuthService, private router: Router) {}

  onSubmit() {
    this.authService.register(this.userRequest).subscribe(() => {
      this.router.navigate(['/login']); // Redirect to login after registration
    }, error => {
      console.error('Registration failed', error);
    });
  }
}
