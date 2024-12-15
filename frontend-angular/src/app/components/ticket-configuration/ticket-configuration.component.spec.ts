import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TicketConfigurationComponent } from './ticket-configuration.component';

describe('TicketConfigurationComponent', () => {
  let component: TicketConfigurationComponent;
  let fixture: ComponentFixture<TicketConfigurationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TicketConfigurationComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TicketConfigurationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
