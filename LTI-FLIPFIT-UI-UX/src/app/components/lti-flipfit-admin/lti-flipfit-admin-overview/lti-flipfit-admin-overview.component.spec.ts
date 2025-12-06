import { ComponentFixture, TestBed } from '@angular/core/testing';
import { LtiFlipFitAdminOverviewComponent } from './lti-flipfit-admin-overview.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';

describe('LtiFlipFitAdminOverviewComponent', () => {
  let component: LtiFlipFitAdminOverviewComponent;
  let fixture: ComponentFixture<LtiFlipFitAdminOverviewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        LtiFlipFitAdminOverviewComponent,
        HttpClientTestingModule,
        RouterTestingModule
      ]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(LtiFlipFitAdminOverviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
