import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Location } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatTableModule } from '@angular/material/table';
import { MatChipsModule } from '@angular/material/chips';

@Component({
  selector: 'app-lti-flipfit-admin-user-details',
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatTableModule,
    MatChipsModule
  ],
  templateUrl: './lti-flipfit-admin-user-details.component.html',
  styleUrl: './lti-flipfit-admin-user-details.component.scss'
})
export class LtiFlipFitAdminUserDetailsComponent {
  displayedColumns: string[] = ['date', 'gymName', 'activity', 'time', 'status'];
  bookings = [
    { date: '23 Nov 2023', gymName: 'FitZone Indiranagar', activity: 'Activity & Prwer meaning', time: '10:00-12:00', status: 'Confirmed' },
    { date: '26 Nov 2023', gymName: 'FitZone Indiranagar', activity: 'Froo hoataing', time: '10:00-13:00', status: 'Completed' },
    { date: '26 Nov 2023', gymName: 'FitZone Indiranagar', activity: 'Activity & Prwer meaning', time: '10:00-13:00', status: 'Cancelled' },
    { date: '26 Nov 2023', gymName: 'FitZone Indiranagar', activity: 'Froo hoataing', time: '10:00-13:00', status: 'Cancelled' }
  ];

  constructor(private location: Location) {}

  goBack() {
    this.location.back();
  }
}
