import { ComponentFixture, TestBed } from '@angular/core/testing';
import { LtiFlipFitGymOwnerDashboard } from './lti-flipfit-owner-dashboard';

describe('LtiFlipFitGymOwnerDashboard', () => {
  let component: LtiFlipFitGymOwnerDashboard;
  let fixture: ComponentFixture<LtiFlipFitGymOwnerDashboard>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LtiFlipFitGymOwnerDashboard]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(LtiFlipFitGymOwnerDashboard);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
