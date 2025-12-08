import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LtiFlipfitOwnerReportComponent } from './lti-flipfit-owner-report.component';

describe('LtiFlipfitOwnerReportComponent', () => {
  let component: LtiFlipfitOwnerReportComponent;
  let fixture: ComponentFixture<LtiFlipfitOwnerReportComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LtiFlipfitOwnerReportComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(LtiFlipfitOwnerReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
