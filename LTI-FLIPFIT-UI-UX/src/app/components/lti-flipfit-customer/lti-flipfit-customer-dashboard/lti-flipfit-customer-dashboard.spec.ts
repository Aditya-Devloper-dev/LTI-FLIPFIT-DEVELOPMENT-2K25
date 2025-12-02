import { ComponentFixture, TestBed } from '@angular/core/testing';
import { LtiFlipFitCustomerDashboard } from './lti-flipfit-customer-dashboard';

describe('LtiFlipFitCustomerDashboard', () => {
  let component: LtiFlipFitCustomerDashboard;
  let fixture: ComponentFixture<LtiFlipFitCustomerDashboard>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LtiFlipFitCustomerDashboard]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(LtiFlipFitCustomerDashboard);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
