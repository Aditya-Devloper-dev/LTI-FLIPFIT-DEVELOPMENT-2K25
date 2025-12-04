import { ComponentFixture, TestBed } from '@angular/core/testing';
import { LtiFlipFitNotificationComponent } from './lti-flipfit-notification.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { NotificationService } from '../../../services/notification-service/notification.service';

describe('LtiFlipFitNotificationComponent', () => {
  let component: LtiFlipFitNotificationComponent;
  let fixture: ComponentFixture<LtiFlipFitNotificationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        LtiFlipFitNotificationComponent,
        HttpClientTestingModule,
        MatCardModule,
        MatIconModule,
        MatButtonModule
      ],
      providers: [NotificationService]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LtiFlipFitNotificationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
