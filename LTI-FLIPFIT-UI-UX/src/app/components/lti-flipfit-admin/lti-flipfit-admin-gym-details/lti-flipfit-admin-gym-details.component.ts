import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Location } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatTableModule } from '@angular/material/table';
import { MatChipsModule } from '@angular/material/chips';

@Component({
  selector: 'app-lti-flipfit-admin-gym-details',
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatTableModule,
    MatChipsModule
  ],
  templateUrl: './lti-flipfit-admin-gym-details.component.html',
  styleUrl: './lti-flipfit-admin-gym-details.component.scss'
})
export class LtiFlipFitAdminGymDetailsComponent {
  displayedColumns: string[] = ['date', 'time', 'activity', 'capacity', 'booked', 'status'];
  slots = [
    { date: '23 Nov 2023', time: '10:00-12:00', activity: 'Activity & Prwer meaning', capacity: 30, booked: 0, status: 'Open' },
    { date: '26 Nov 2023', time: '10:00-13:00', activity: 'Froo hoataing', capacity: 30, booked: 0, status: 'Full' }
  ];

  constructor(private location: Location) {}

  goBack() {
    this.location.back();
  }
}
