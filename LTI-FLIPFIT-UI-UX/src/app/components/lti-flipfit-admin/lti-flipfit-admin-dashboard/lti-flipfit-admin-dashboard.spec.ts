import { ComponentFixture, TestBed } from '@angular/core/testing';
import { LtiFlipFitAdminDashboard } from './lti-flipfit-admin-dashboard';
import { RouterTestingModule } from '@angular/router/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

describe('LtiFlipFitAdminDashboard', () => {
  let component: LtiFlipFitAdminDashboard;
  let fixture: ComponentFixture<LtiFlipFitAdminDashboard>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        LtiFlipFitAdminDashboard,
        RouterTestingModule,
        NoopAnimationsModule
      ]
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
