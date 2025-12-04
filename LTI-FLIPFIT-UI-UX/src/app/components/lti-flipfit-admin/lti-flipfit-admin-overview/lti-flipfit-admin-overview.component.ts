import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';

@Component({
  selector: 'app-lti-flipfit-admin-overview',
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatListModule
  ],
  templateUrl: './lti-flipfit-admin-overview.component.html',
  styleUrl: './lti-flipfit-admin-overview.component.scss'
})
export class LtiFlipFitAdminOverviewComponent {
  pendingGyms = [
    { name: 'FlipFit Gym', location: 'Kandwente, GA' },
    { name: 'FlipFit Rover Gym', location: 'Leumbourw, HL' },
    { name: 'FlipFit Gym', location: 'Kanshrame, GA' }
  ];

  recentEvents = [
    { title: 'User registered Administrativn', time: '11 Jun 12, 2023, 07:43 AM' },
    { title: 'User registered Adminisaaow', time: '11 Jun 12, 2023, 07:48 AM' },
    { title: 'Booking Confirmed by natad Rornax Flanana', time: '11 Jun 12, 2023, 13:38 AM' },
    { title: 'Booking Confirmed by nated Aomin Fianana', time: '11 Jun 12, 2023, 12:38 AM' }
  ];
}
