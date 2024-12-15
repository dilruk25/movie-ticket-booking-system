import { Component, OnInit } from '@angular/core';
import { TicketService, DashboardData } from '../../services/ticket.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html'
})
export class DashboardComponent implements OnInit {
  dashboardData: DashboardData | undefined;

  constructor(private ticketService: TicketService) { }

  ngOnInit() {
    this.fetchDashboardData();
  }

  fetchDashboardData() {
    this.ticketService.getDashboardData().subscribe(data => {
      this.dashboardData = data;
    }, error => {
      console.error('Error fetching dashboard data:', error);
    });
  }
}
