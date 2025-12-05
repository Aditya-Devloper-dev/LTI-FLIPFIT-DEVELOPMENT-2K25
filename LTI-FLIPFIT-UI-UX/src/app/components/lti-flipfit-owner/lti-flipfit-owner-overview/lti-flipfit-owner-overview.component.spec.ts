import { ComponentFixture, TestBed } from '@angular/core/testing';
import { LtiFlipFitOwnerOverviewComponent } from './lti-flipfit-owner-overview.component';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('LtiFlipFitOwnerOverviewComponent', () => {
  let component: LtiFlipFitOwnerOverviewComponent;
  let fixture: ComponentFixture<LtiFlipFitOwnerOverviewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        LtiFlipFitOwnerOverviewComponent,
        RouterTestingModule,
        HttpClientTestingModule
      ]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(LtiFlipFitOwnerOverviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
