import { Component } from '@angular/core';
import { TicketService } from '../../services/ticket.service';

@Component({
  selector: 'app-thread-management',
  templateUrl: './thread-management.component.html'
})
export class ThreadManagementComponent {
  constructor(private ticketService: TicketService) { }

  startCustomer() {
    this.ticketService.startCustomerThreads().subscribe(() => {
      alert('Customer threads started!');
    });
  }

  stopCustomer() {
    this.ticketService.stopCustomerThreads().subscribe(() => {
      alert('Customer threads stopped!');
    });
  }

  startVendor() {
    this.ticketService.startVendorThreads().subscribe(() => {
      alert('Vendor threads started!');
    });
  }

  stopVendor() {
    this.ticketService.stopVendorThreads().subscribe(() => {
      alert('Vendor threads stopped!');
    });
  }
}
