import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ThreadManagementComponent } from './thread-management.component';

describe('ThreadManagementComponent', () => {
  let component: ThreadManagementComponent;
  let fixture: ComponentFixture<ThreadManagementComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ThreadManagementComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ThreadManagementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
