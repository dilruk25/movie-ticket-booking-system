import { Component } from '@angular/core';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-config-gather',
  imports: [
    FormsModule
  ],
  templateUrl: './config-gather.component.html'
})
export class ConfigGatherComponent {
  configField: string | undefined;

  onSubmit() {
    // TODO: Logic?
    console.log('Configuration submitted:', this.configField);
  }
}
