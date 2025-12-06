import { ComponentFixture, TestBed } from '@angular/core/testing';
import { LtiFlipFitAdminGymDetailsComponent } from './lti-flipfit-admin-gym-details.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { MatDialogModule } from '@angular/material/dialog';
import { MatSnackBarModule } from '@angular/material/snack-bar';

describe('LtiFlipFitAdminGymDetailsComponent', () => {
  let component: LtiFlipFitAdminGymDetailsComponent;
  let fixture: ComponentFixture<LtiFlipFitAdminGymDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        LtiFlipFitAdminGymDetailsComponent,
        HttpClientTestingModule,
        RouterTestingModule,
        MatDialogModule,
        MatSnackBarModule
      ]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(LtiFlipFitAdminGymDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
