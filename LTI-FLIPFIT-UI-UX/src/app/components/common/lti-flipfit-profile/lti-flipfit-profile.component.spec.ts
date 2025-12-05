import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { LtiFlipFitProfileComponent } from './lti-flipfit-profile.component';
import { UserService } from '../../../services/user-service/user.service';

describe('LtiFlipFitProfileComponent', () => {
  let component: LtiFlipFitProfileComponent;
  let fixture: ComponentFixture<LtiFlipFitProfileComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        LtiFlipFitProfileComponent,
        HttpClientTestingModule,
        NoopAnimationsModule
      ],
      providers: [UserService]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(LtiFlipFitProfileComponent);
    component = fixture.componentInstance;
    
    // Mock localStorage
    const mockUser = { userId: 1, email: 'test@example.com', roleName: 'CUSTOMER' };
    spyOn(localStorage, 'getItem').and.returnValue(JSON.stringify(mockUser));
    
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
