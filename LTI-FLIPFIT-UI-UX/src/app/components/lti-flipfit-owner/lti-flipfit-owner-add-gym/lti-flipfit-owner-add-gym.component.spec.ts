import { ComponentFixture, TestBed } from '@angular/core/testing';
import { LtiFlipFitOwnerAddGymComponent } from './lti-flipfit-owner-add-gym.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { FormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { OwnerService } from '../../../services/owner-service/owner.service';

describe('LtiFlipFitOwnerAddGymComponent', () => {
  let component: LtiFlipFitOwnerAddGymComponent;
  let fixture: ComponentFixture<LtiFlipFitOwnerAddGymComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        LtiFlipFitOwnerAddGymComponent,
        HttpClientTestingModule,
        RouterTestingModule,
        MatCardModule,
        MatButtonModule,
        MatFormFieldModule,
        MatInputModule,
        MatIconModule,
        MatSnackBarModule,
        FormsModule,
        BrowserAnimationsModule
      ],
      providers: [OwnerService]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(LtiFlipFitOwnerAddGymComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
