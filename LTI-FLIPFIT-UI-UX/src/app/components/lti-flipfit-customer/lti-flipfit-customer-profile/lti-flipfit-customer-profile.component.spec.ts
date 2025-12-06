import { ComponentFixture, TestBed } from '@angular/core/testing';
import { LtiFlipfitCustomerProfileComponent } from './lti-flipfit-customer-profile.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { MatDialogModule } from '@angular/material/dialog';
import { MatSnackBarModule } from '@angular/material/snack-bar';

describe('LtiFlipfitCustomerProfileComponent', () => {
  let component: LtiFlipfitCustomerProfileComponent;
  let fixture: ComponentFixture<LtiFlipfitCustomerProfileComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        LtiFlipfitCustomerProfileComponent,
        HttpClientTestingModule,
        MatDialogModule,
        MatSnackBarModule
      ]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(LtiFlipfitCustomerProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
