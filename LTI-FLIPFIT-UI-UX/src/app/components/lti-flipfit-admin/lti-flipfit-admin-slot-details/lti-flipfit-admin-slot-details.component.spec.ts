import { ComponentFixture, TestBed } from '@angular/core/testing';
import { LtiFlipFitAdminSlotDetailsComponent } from './lti-flipfit-admin-slot-details.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';

describe('LtiFlipFitAdminSlotDetailsComponent', () => {
  let component: LtiFlipFitAdminSlotDetailsComponent;
  let fixture: ComponentFixture<LtiFlipFitAdminSlotDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        LtiFlipFitAdminSlotDetailsComponent,
        HttpClientTestingModule,
        RouterTestingModule
      ]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(LtiFlipFitAdminSlotDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
