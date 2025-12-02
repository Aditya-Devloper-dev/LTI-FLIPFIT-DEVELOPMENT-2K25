import { ComponentFixture, TestBed } from '@angular/core/testing';
import { LtiFlipFitAdminDashboard } from './lti-flipfit-admin-dashboard';

describe('LtiFlipFitAdminDashboard', () => {
  let component: LtiFlipFitAdminDashboard;
  let fixture: ComponentFixture<LtiFlipFitAdminDashboard>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LtiFlipFitAdminDashboard]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(LtiFlipFitAdminDashboard);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
