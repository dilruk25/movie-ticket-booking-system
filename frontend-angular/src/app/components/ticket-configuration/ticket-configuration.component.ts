import { Component } from '@angular/core';
import { TicketService } from '../../services/ticket.service';
import { AddTicketsRequest } from '../../models/add-tickets-request.model';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-ticket-configuration',
  imports: [
    FormsModule
  ],
  templateUrl: './ticket-configuration.component.html'
})
export class TicketConfigurationComponent {
  addTicketsRequest: AddTicketsRequest = { movieId: '', tickets: 0 };

  constructor(private ticketService: TicketService) {}

  onSubmit() {
    this.ticketService.addTickets(this.addTicketsRequest).subscribe(tickets => {
      console.log('Tickets added:', tickets);
    }, error => {
      console.error('Error adding tickets:', error);
    });
  }
}
