import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MovieLandingComponent } from './movie-landing.component';

describe('MovieLandingComponent', () => {
  let component: MovieLandingComponent;
  let fixture: ComponentFixture<MovieLandingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MovieLandingComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MovieLandingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
