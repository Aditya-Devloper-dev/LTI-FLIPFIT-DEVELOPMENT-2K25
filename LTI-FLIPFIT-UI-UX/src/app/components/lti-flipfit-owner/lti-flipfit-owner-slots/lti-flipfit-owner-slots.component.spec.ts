import { ComponentFixture, TestBed } from '@angular/core/testing';
import { LtiFlipFitOwnerSlotsComponent } from './lti-flipfit-owner-slots.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { OwnerService } from '../../../services/owner-service/owner.service';
import { UserService } from '../../../services/user-service/user.service';
import { of } from 'rxjs';
import { FormsModule } from '@angular/forms';

describe('LtiFlipFitOwnerSlotsComponent', () => {
  let component: LtiFlipFitOwnerSlotsComponent;
  let fixture: ComponentFixture<LtiFlipFitOwnerSlotsComponent>;
  let ownerServiceSpy: jasmine.SpyObj<OwnerService>;
  let userServiceSpy: jasmine.SpyObj<UserService>;

  beforeEach(async () => {
    const ownerSpy = jasmine.createSpyObj('OwnerService', ['getGymsByOwnerId', 'getSlotsByCenterId', 'toggleSlotActive', 'addSlot']);
    const userSpy = jasmine.createSpyObj('UserService', ['getUserById']);

    await TestBed.configureTestingModule({
      imports: [
        LtiFlipFitOwnerSlotsComponent,
        HttpClientTestingModule,
        MatSnackBarModule,
        NoopAnimationsModule,
        FormsModule
      ],
      providers: [
        { provide: OwnerService, useValue: ownerSpy },
        { provide: UserService, useValue: userSpy }
      ]
    })
    .compileComponents();
    
    ownerServiceSpy = TestBed.inject(OwnerService) as jasmine.SpyObj<OwnerService>;
    userServiceSpy = TestBed.inject(UserService) as jasmine.SpyObj<UserService>;

    fixture = TestBed.createComponent(LtiFlipFitOwnerSlotsComponent);
    component = fixture.componentInstance;
    
    // Mock localStorage
    const mockUser = { userId: 1, ownerId: 101, email: 'owner@example.com', roleName: 'OWNER' };
    spyOn(localStorage, 'getItem').and.returnValue(JSON.stringify(mockUser));
    
    // Mock service calls
    userServiceSpy.getUserById.and.returnValue(of({ fullName: 'Owner Name' } as any));
    ownerServiceSpy.getGymsByOwnerId.and.returnValue(of([]));

    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should load gyms on init', () => {
    expect(ownerServiceSpy.getGymsByOwnerId).toHaveBeenCalledWith(101);
  });
});
