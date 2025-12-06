import { ComponentFixture, TestBed } from '@angular/core/testing';
import { LtiFlipFitAdminGymsComponent } from './lti-flipfit-admin-gyms.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';

describe('LtiFlipFitAdminGymsComponent', () => {
  let component: LtiFlipFitAdminGymsComponent;
  let fixture: ComponentFixture<LtiFlipFitAdminGymsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        LtiFlipFitAdminGymsComponent,
        HttpClientTestingModule,
        RouterTestingModule
      ]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(LtiFlipFitAdminGymsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
