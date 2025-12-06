import { ComponentFixture, TestBed } from '@angular/core/testing';
import { LtiFlipFitAdminUserDetailsComponent } from './lti-flipfit-admin-user-details.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { MatDialogModule } from '@angular/material/dialog';
import { MatSnackBarModule } from '@angular/material/snack-bar';

describe('LtiFlipFitAdminUserDetailsComponent', () => {
  let component: LtiFlipFitAdminUserDetailsComponent;
  let fixture: ComponentFixture<LtiFlipFitAdminUserDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        LtiFlipFitAdminUserDetailsComponent,
        HttpClientTestingModule,
        RouterTestingModule,
        MatDialogModule,
        MatSnackBarModule
      ]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(LtiFlipFitAdminUserDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
