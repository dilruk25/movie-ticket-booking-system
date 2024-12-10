import { Component } from '@angular/core';
import { ConfigService } from '../../services/config.service';

@Component({
  selector: 'app-configuration-form',
  templateUrl: './configuration-form.component.html',
  styleUrls: ['./configuration-form.component.css']
})
export class ConfigurationFormComponent {
  config = { maxTickets: 0, seatLabels: '', vipSeats: 0 };

  constructor(private configService: ConfigService) {}

  submitForm(form: any): void {
    if (form.valid) {
      this.configService.saveConfig(this.config);
      alert('Configuration saved successfully!');
    } else {
      alert('Please fill out the form correctly.');
    }
  }
}