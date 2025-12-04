import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatTableModule } from '@angular/material/table';
import { MatChipsModule } from '@angular/material/chips';
import { LtiFlipFitNotificationComponent } from '../../common/lti-flipfit-notification/lti-flipfit-notification.component';
import { Router } from '@angular/router';
import { OwnerService } from '../../../services/owner-service/owner.service';
import { UserService } from '../../../services/user-service/user.service';

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
export class LtiFlipFitOwnerOverviewComponent implements OnInit {

  recentBookings: any[] = [];
  displayedColumns: string[] = ['customer', 'time', 'gymCenter', 'status'];
  ownerId: number | null = null;

  constructor(
    private router: Router,
    private ownerService: OwnerService,
    private userService: UserService
  ) { }

  ngOnInit() {
    const userStr = localStorage.getItem('user');
    if (userStr) {
      const user = JSON.parse(userStr);
      if (user.ownerId) {
        this.ownerId = user.ownerId;
        this.loadRecentBookings();
      } else if (user.userId) {
          this.userService.getUserById(user.userId).subscribe(u => {
              if (u.ownerId) {
                  this.ownerId = u.ownerId;
                  this.loadRecentBookings();
              }
          });
      }
    }
  }

  loadRecentBookings() {
    if (this.ownerId) {
      this.ownerService.getAllBookingsByOwner(this.ownerId).subscribe({
        next: (data) => {
          // Map backend data to table format
          this.recentBookings = data.map(booking => ({
            customer: booking.customer.name,
            time: `${booking.bookingDate} ${booking.slot.startTime}`,
            gymCenter: booking.center.centerName,
            status: booking.status
          }));
        },
        error: (err) => console.error('Failed to load bookings', err)
      });
    }
  }

  onManageGymProfile() {
    this.router.navigate(['/gym-owner-dashboard/profile']);
  }

  onAddSlot() {
    this.router.navigate(['/gym-owner-dashboard/slots']);
  }
}
