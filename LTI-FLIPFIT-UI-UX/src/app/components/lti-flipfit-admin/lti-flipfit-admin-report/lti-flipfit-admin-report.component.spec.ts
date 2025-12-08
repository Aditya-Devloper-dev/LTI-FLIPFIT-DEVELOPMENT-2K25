import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LtiFlipfitAdminReportComponent } from './lti-flipfit-admin-report.component';

describe('LtiFlipfitAdminReportComponent', () => {
  let component: LtiFlipfitAdminReportComponent;
  let fixture: ComponentFixture<LtiFlipfitAdminReportComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LtiFlipfitAdminReportComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(LtiFlipfitAdminReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
