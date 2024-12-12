import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfigGatherComponent } from './config-gather.component';

describe('ConfigGatherComponent', () => {
  let component: ConfigGatherComponent;
  let fixture: ComponentFixture<ConfigGatherComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ConfigGatherComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ConfigGatherComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
