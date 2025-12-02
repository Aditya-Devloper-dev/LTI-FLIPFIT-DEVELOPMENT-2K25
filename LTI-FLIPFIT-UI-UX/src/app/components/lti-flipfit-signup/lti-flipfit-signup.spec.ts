import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LtiFlipFitSignUp } from './lti-flipfit-signup';

describe('LtiFlipFitSignUp', () => {
  let component: LtiFlipFitSignUp;
  let fixture: ComponentFixture<LtiFlipFitSignUp>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LtiFlipFitSignUp]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LtiFlipFitSignUp);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
