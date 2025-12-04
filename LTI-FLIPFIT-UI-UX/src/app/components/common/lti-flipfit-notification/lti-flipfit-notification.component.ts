import { Component, OnInit, Inject, PLATFORM_ID } from '@angular/core';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { NotificationService } from '../../../services/notification-service/notification.service';

@Component({
  selector: 'app-lti-flipfit-notification',
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatIconModule,
    MatButtonModule
  ],
  templateUrl: './lti-flipfit-notification.component.html',
  styleUrl: './lti-flipfit-notification.component.scss'
})
export class LtiFlipFitNotificationComponent implements OnInit {
  notifications: any[] = [];

  constructor(
    private notificationService: NotificationService,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {}

  ngOnInit() {
    if (isPlatformBrowser(this.platformId)) {
      const userString = localStorage.getItem('user');
      const user = userString ? JSON.parse(userString) : null;
      const userId = user?.userId;

      if (userId) {
        this.notificationService.getNotifications(userId).subscribe({
          next: (data) => {
            this.notifications = data;
          },
          error: (error) => {
            console.error('Error fetching notifications', error);
          }
        });
      }
    }
  }

  onClearAll() {
    if (isPlatformBrowser(this.platformId)) {
      const userString = localStorage.getItem('user');
      const user = userString ? JSON.parse(userString) : null;
      const userId = user?.userId;

      if (userId) {
        this.notificationService.clearAllNotifications(userId).subscribe({
          next: () => {
            this.notifications = [];
          },
          error: (error) => {
            console.error('Error clearing notifications', error);
          }
        });
      }
    }
  }
}
