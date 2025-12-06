import { ComponentFixture, TestBed } from '@angular/core/testing';
import { LtiFlipFitCustomerHomeComponent } from './lti-flipfit-customer-home.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { MatDialogModule } from '@angular/material/dialog';
import { MatSnackBarModule } from '@angular/material/snack-bar';

describe('LtiFlipFitCustomerHomeComponent', () => {
  let component: LtiFlipFitCustomerHomeComponent;
  let fixture: ComponentFixture<LtiFlipFitCustomerHomeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        LtiFlipFitCustomerHomeComponent,
        HttpClientTestingModule,
        RouterTestingModule,
        MatDialogModule,
        MatSnackBarModule
      ]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(LtiFlipFitCustomerHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
