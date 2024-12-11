import { Injectable } from '@angular/core';
import { webSocket } from 'rxjs/webSocket';

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {
  private socket = webSocket('ws://localhost:8080/ticket-updates');

  sendMessage(message: any): void {
    this.socket.next(message);
  }

  getUpdates() {
    return this.socket.asObservable();
  }
}