import { ComponentFixture, TestBed } from '@angular/core/testing';
import { LtiFlipFitCustomerBookingComponent } from './lti-flipfit-customer-booking.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { MatDialogModule } from '@angular/material/dialog';
import { MatSnackBarModule } from '@angular/material/snack-bar';

describe('LtiFlipFitCustomerBookingComponent', () => {
  let component: LtiFlipFitCustomerBookingComponent;
  let fixture: ComponentFixture<LtiFlipFitCustomerBookingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        LtiFlipFitCustomerBookingComponent,
        HttpClientTestingModule,
        RouterTestingModule,
        MatDialogModule,
        MatSnackBarModule
      ]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(LtiFlipFitCustomerBookingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
