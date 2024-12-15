import { Component } from '@angular/core';

@Component({
  selector: 'app-config-gather',
  templateUrl: './config-gather.component.html'
})
export class ConfigGatherComponent {
  configField: string = '';
  submissionSuccess: boolean = false;

  onSubmit() {
    // Logic to handle configuration submission
    console.log('Configuration submitted:', this.configField);
    this.submissionSuccess = true;

    // Reset the field
    this.configField = '';
  }
}
