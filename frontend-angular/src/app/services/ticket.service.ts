import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AddTicketsRequest } from '../models/add-tickets-request.model';

@Injectable({
  providedIn: 'root'
})
export class TicketService {
  private baseUrl = 'http://localhost:8080/api/v1/tickets'; // Adjust based on your backend

  constructor(private http: HttpClient) {}

  // Add tickets for a movie
  addTickets(addTicketsRequest: AddTicketsRequest): Observable<any> {
    return this.http.post(`${this.baseUrl}/add-tickets`, addTicketsRequest);
  }

  // Get the total ticket count
  getTicketCount(): Observable<number> {
    return this.http.get<number>(`${this.baseUrl}/count`);
  }
}
