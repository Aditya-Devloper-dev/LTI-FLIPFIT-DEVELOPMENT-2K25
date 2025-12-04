import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LtiFlipfitLandingComponent } from './lti-flipfit-landing.component';

describe('LtiFlipfitLandingComponent', () => {
  let component: LtiFlipfitLandingComponent;
  let fixture: ComponentFixture<LtiFlipfitLandingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LtiFlipfitLandingComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(LtiFlipfitLandingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
