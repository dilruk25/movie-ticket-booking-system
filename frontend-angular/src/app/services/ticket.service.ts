import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface DashboardData {
  ticketsBought: number;
  ticketsSold: number;
  availableTickets: number;
}

@Injectable({
  providedIn: 'root'
})
export class TicketService {
  private baseUrl = 'http://localhost:8080/api'; // Adjust accordingly

  constructor(private http: HttpClient) { }

  startCustomerThreads(): Observable<any> {
    return this.http.post(`${this.baseUrl}/customer/start`, {});
  }

  stopCustomerThreads(): Observable<any> {
    return this.http.post(`${this.baseUrl}/customer/stop`, {});
  }

  startVendorThreads(): Observable<any> {
    return this.http.post(`${this.baseUrl}/vendor/start`, {});
  }

  stopVendorThreads(): Observable<any> {
    return this.http.post(`${this.baseUrl}/vendor/stop`, {});
  }

  getDashboardData(): Observable<DashboardData> {
    return this.http.get<DashboardData>(`${this.baseUrl}/dashboard`);
  }
}
