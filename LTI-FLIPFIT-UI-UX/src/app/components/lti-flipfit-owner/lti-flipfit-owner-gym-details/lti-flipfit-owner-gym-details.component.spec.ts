import { ComponentFixture, TestBed } from '@angular/core/testing';
import { LtiFlipFitOwnerGymDetailsComponent } from './lti-flipfit-owner-gym-details.component';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { MatSnackBarModule } from '@angular/material/snack-bar';

describe('LtiFlipFitOwnerGymDetailsComponent', () => {
  let component: LtiFlipFitOwnerGymDetailsComponent;
  let fixture: ComponentFixture<LtiFlipFitOwnerGymDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        LtiFlipFitOwnerGymDetailsComponent,
        RouterTestingModule,
        HttpClientTestingModule,
        MatSnackBarModule
      ]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(LtiFlipFitOwnerGymDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
