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
    }, error => {
      alert('Error starting customer threads: ' + error.message);
    });
  }

  stopCustomer() {
    this.ticketService.stopCustomerThreads().subscribe(() => {
      alert('Customer threads stopped!');
    }, error => {
      alert('Error stopping customer threads: ' + error.message);
    });
  }

  startVendor() {
    this.ticketService.startVendorThreads().subscribe(() => {
      alert('Vendor threads started!');
    }, error => {
      alert('Error starting vendor threads: ' + error.message);
    });
  }

  stopVendor() {
    this.ticketService.stopVendorThreads().subscribe(() => {
      alert('Vendor threads stopped!');
    }, error => {
      alert('Error stopping vendor threads: ' + error.message);
    });
  }
}
