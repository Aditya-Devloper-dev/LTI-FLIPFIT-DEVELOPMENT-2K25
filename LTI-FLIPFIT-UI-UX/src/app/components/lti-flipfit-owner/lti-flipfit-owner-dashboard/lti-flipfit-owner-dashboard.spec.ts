import { ComponentFixture, TestBed } from '@angular/core/testing';
import { LtiFlipFitGymOwnerDashboard } from './lti-flipfit-owner-dashboard';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { UserService } from '../../../services/user-service/user.service';

describe('LtiFlipFitGymOwnerDashboard', () => {
  let component: LtiFlipFitGymOwnerDashboard;
  let fixture: ComponentFixture<LtiFlipFitGymOwnerDashboard>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        LtiFlipFitGymOwnerDashboard,
        RouterTestingModule,
        HttpClientTestingModule,
        NoopAnimationsModule
      ],
      providers: [UserService]
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
