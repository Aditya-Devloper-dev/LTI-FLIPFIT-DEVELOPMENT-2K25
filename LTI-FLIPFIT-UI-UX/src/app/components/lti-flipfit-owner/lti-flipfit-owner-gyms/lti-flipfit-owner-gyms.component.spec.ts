import { ComponentFixture, TestBed } from '@angular/core/testing';
import { LtiFlipFitOwnerGymsComponent } from './lti-flipfit-owner-gyms.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatChipsModule } from '@angular/material/chips';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { OwnerService } from '../../../services/owner-service/owner.service';

describe('LtiFlipFitOwnerGymsComponent', () => {
  let component: LtiFlipFitOwnerGymsComponent;
  let fixture: ComponentFixture<LtiFlipFitOwnerGymsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        LtiFlipFitOwnerGymsComponent,
        HttpClientTestingModule,
        RouterTestingModule,
        MatCardModule,
        MatButtonModule,
        MatIconModule,
        MatChipsModule,
        MatSnackBarModule
      ],
      providers: [OwnerService]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(LtiFlipFitOwnerGymsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
