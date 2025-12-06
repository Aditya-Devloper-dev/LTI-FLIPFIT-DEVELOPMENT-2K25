import { ComponentFixture, TestBed } from '@angular/core/testing';
import { LtiFlipFitAdminSlotsComponent } from './lti-flipfit-admin-slots.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';

describe('LtiFlipFitAdminSlotsComponent', () => {
  let component: LtiFlipFitAdminSlotsComponent;
  let fixture: ComponentFixture<LtiFlipFitAdminSlotsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        LtiFlipFitAdminSlotsComponent,
        HttpClientTestingModule,
        RouterTestingModule
      ]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(LtiFlipFitAdminSlotsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
