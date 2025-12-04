import { ComponentFixture, TestBed } from '@angular/core/testing';
import { LtiFlipFitCustomerLayoutComponent } from './lti-flipfit-customer-layout.component';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { UserService } from '../../../services/user-service/user.service';

describe('LtiFlipFitCustomerLayoutComponent', () => {
  let component: LtiFlipFitCustomerLayoutComponent;
  let fixture: ComponentFixture<LtiFlipFitCustomerLayoutComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        LtiFlipFitCustomerLayoutComponent,
        RouterTestingModule,
        HttpClientTestingModule,
        NoopAnimationsModule
      ],
      providers: [UserService]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(LtiFlipFitCustomerLayoutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
