import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatTableModule } from '@angular/material/table';
import { MatChipsModule } from '@angular/material/chips';
import { LtiFlipFitNotificationComponent } from '../../common/lti-flipfit-notification/lti-flipfit-notification.component';

@Component({
  selector: 'app-lti-flipfit-owner-overview',
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatTableModule,
    MatChipsModule,
    LtiFlipFitNotificationComponent
  ],
  templateUrl: './lti-flipfit-owner-overview.component.html',
  styleUrl: './lti-flipfit-owner-overview.component.scss'
})
export class LtiFlipFitOwnerOverviewComponent {
  recentBookings = [
    { customer: 'Marsi Bastin', time: 'Feb 9:00 PM', status: 'Booking' },
    { customer: 'Elvan Hanley', time: 'Feb 4:30 PM', status: 'Booking' },
    { customer: 'David Pamson', time: 'Feb 9:00 PM', status: 'Booking' },
    { customer: 'Rohero Waht', time: 'Feb 6:30 PM', status: 'Booking' }
  ];

  displayedColumns: string[] = ['customer', 'time', 'status'];
}
