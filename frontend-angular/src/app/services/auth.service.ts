import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UserRequest } from '../models/user-request.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private baseUrl = 'http://localhost:8080/api/v1/users'; // Adjust based on your backend

  constructor(private http: HttpClient) {}

  // Register a new user
  register(userRequest: UserRequest): Observable<any> {
    return this.http.post(`${this.baseUrl}/register`, userRequest);
  }

  // Login a user
  login(username: string, password: string): Observable<any> {
    const headers = { 'Authorization': 'Basic ' + btoa(username + ':' + password) };
    return this.http.post(`${this.baseUrl}/login`, {}, { headers });
  }
}
