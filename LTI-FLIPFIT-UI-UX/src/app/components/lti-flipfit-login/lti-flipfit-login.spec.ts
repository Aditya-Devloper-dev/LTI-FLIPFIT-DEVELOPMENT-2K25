import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LtiFlipFitLogin } from './lti-flipfit-login';

describe('LtiFlipFitLogin', () => {
  let component: LtiFlipFitLogin;
  let fixture: ComponentFixture<LtiFlipFitLogin>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LtiFlipFitLogin]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LtiFlipFitLogin);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
