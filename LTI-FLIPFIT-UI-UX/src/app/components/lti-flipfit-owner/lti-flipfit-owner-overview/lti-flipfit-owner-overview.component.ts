import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { MatTableModule } from '@angular/material/table';
import { MatChipsModule } from '@angular/material/chips';
import { LtiFlipFitNotificationComponent } from '../../common/lti-flipfit-notification/lti-flipfit-notification.component';
import { Router } from '@angular/router';
import { OwnerService } from '../../../services/owner-service/owner.service';
import { UserService } from '../../../services/user-service/user.service';
import { forkJoin } from 'rxjs';

/**
 * @author:
 * @version: 1.0
 * @description: Overview component for the Gym Owner Dashboard, displaying stats and recent bookings.
 */
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
    MatMenuModule,
    LtiFlipFitNotificationComponent
  ],
  templateUrl: './lti-flipfit-owner-overview.component.html',
  styleUrl: './lti-flipfit-owner-overview.component.scss'
})
export class LtiFlipFitOwnerOverviewComponent implements OnInit {

  recentBookings: any[] = [];
  displayedColumns: string[] = ['customer', 'time', 'gymCenter', 'status'];
  ownerId: number | null = null;

  todaysBookingsCount: number = 0;
  todaysBookedCount: number = 0;
  todaysCancelledCount: number = 0;
  todaysPendingCount: number = 0;

  activeSlotsCount: number = 0;
  totalSlotsCount: number = 0;
  approvedSlotsCount: number = 0;

  activeGymsCount: number = 0;
  totalGymsCount: number = 0;
  approvedGymsCount: number = 0;

  monthlyRevenue: number = 0;
  weeklyRevenue: number = 0;
  todaysRevenue: number = 0;
  revenueView: 'monthly' | 'today' = 'monthly';

  constructor(
    private router: Router,
    private ownerService: OwnerService,
    private userService: UserService
  ) { }

  /**
   * @method ngOnInit
   * @description Initializes the component and loads dashboard data.
   */
  ngOnInit() {
    const userStr = localStorage.getItem('user');
    if (userStr) {
      const user = JSON.parse(userStr);
      if (user.ownerId) {
        this.ownerId = user.ownerId;
        this.loadDashboardData();
      } else if (user.userId) {
          this.userService.getUserById(user.userId).subscribe(u => {
              if (u.ownerId) {
                  this.ownerId = u.ownerId;
                  this.loadDashboardData();
              }
          });
      }
    }
  }

  /**
   * @method loadDashboardData
   * @description Triggers loading of recent bookings and calculation of statistics.
   */
  loadDashboardData() {
    if (this.ownerId) {
      this.loadRecentBookings();
      this.calculateStats();
    }
  }

  /**
   * @method loadRecentBookings
   * @description Fetches and displays recent bookings for the owner.
   */
  loadRecentBookings() {
    if (this.ownerId) {
      this.ownerService.getAllBookingsByOwner(this.ownerId).subscribe({
        next: (data) => {
          // Map backend data to table format
          this.recentBookings = data.map(booking => {
            let dateStr = '';
            if (Array.isArray(booking.bookingDate)) {
               // [year, month, day]
               const year = booking.bookingDate[0];
               const month = String(booking.bookingDate[1]).padStart(2, '0');
               const day = String(booking.bookingDate[2]).padStart(2, '0');
               dateStr = `${year}-${month}-${day}`;
            } else {
               dateStr = String(booking.bookingDate);
            }
            
            return {
              customer: booking.customer?.user?.fullName || 'Unknown Customer',
              time: `${dateStr} ${booking.slot.startTime}`,
              gymCenter: booking.center.centerName,
              status: booking.status
            };
          }).sort((a, b) => new Date(b.time).getTime() - new Date(a.time).getTime());
        },
        error: (err) => console.error('Failed to load bookings', err)
      });
    }
  }

  /**
   * @method calculateStats
   * @description Calculates various statistics like revenue, booking counts, and active slots.
   */
  calculateStats() {
    const ownerId = this.ownerId;
    if (!ownerId) return;

    // 1. Calculate Bookings and Revenue
    this.ownerService.getAllBookingsByOwner(ownerId).subscribe({
      next: (bookings) => {
        const today = new Date();
        const currentMonth = today.getMonth();
        const currentYear = today.getFullYear();

        // Calculate start of the week (Sunday)
        const startOfWeek = new Date(today);
        startOfWeek.setDate(today.getDate() - today.getDay());
        startOfWeek.setHours(0, 0, 0, 0);

        this.todaysBookingsCount = 0;
        this.todaysBookedCount = 0;
        this.todaysCancelledCount = 0;
        this.todaysPendingCount = 0;

        this.monthlyRevenue = 0;
        this.weeklyRevenue = 0;
        this.todaysRevenue = 0;

        bookings.forEach(booking => {
          // Handle potential date formats (string or array)
          let bookingDate: Date;
          if (Array.isArray(booking.bookingDate)) {
             // [year, month, day] - month is 1-indexed in Java, 0-indexed in JS
             bookingDate = new Date(booking.bookingDate[0], booking.bookingDate[1] - 1, booking.bookingDate[2]);
          } else {
             // Append T00:00:00 to ensure local time parsing if it's just YYYY-MM-DD
             // If it already has time, this might be invalid, so check length
             const dateStr = String(booking.bookingDate);
             if (dateStr.length === 10) {
                bookingDate = new Date(dateStr + 'T00:00:00');
             } else {
                bookingDate = new Date(dateStr);
             }
          }

          const price = booking.slot?.price || 0;

          // Today's Bookings & Revenue
          if (bookingDate.toDateString() === today.toDateString()) {
            this.todaysBookingsCount++;
            this.todaysRevenue += price;

            const status = booking.status ? booking.status.toUpperCase() : '';
            if (status === 'BOOKED' || status === 'CONFIRMED') {
              this.todaysBookedCount++;
            } else if (status === 'CANCELLED') {
              this.todaysCancelledCount++;
            } else if (status === 'PENDING' || status === 'WAITING') {
              this.todaysPendingCount++;
            }
          }

          // Weekly Revenue
          if (bookingDate >= startOfWeek && bookingDate <= today) {
            this.weeklyRevenue += price;
          }

          // Monthly Revenue
          if (bookingDate.getMonth() === currentMonth && bookingDate.getFullYear() === currentYear) {
            this.monthlyRevenue += price;
          }
        });
      },
      error: (err) => console.error('Failed to load bookings for stats', err)
    });

    // 2. Calculate Active Slots and Active Gyms
    this.ownerService.getGymsByOwnerId(ownerId).subscribe({
      next: (gyms) => {
        this.totalGymsCount = gyms.length;
        // Check for isApproved property, default to false if not present
        this.approvedGymsCount = gyms.filter(gym => gym.isApproved).length;
        this.activeGymsCount = gyms.filter(gym => gym.isActive).length;

        if (gyms.length === 0) {
          this.activeSlotsCount = 0;
          this.totalSlotsCount = 0;
          this.approvedSlotsCount = 0;
          return;
        }

        const slotRequests = gyms
          .filter(gym => gym.centerId !== undefined)
          .map(gym => this.ownerService.getSlotsByCenterId(gym.centerId as number));

        forkJoin(slotRequests).subscribe({
          next: (slotsArray) => {
            this.activeSlotsCount = 0;
            this.totalSlotsCount = 0;
            this.approvedSlotsCount = 0;

            slotsArray.forEach(slots => {
              this.totalSlotsCount += slots.length;
              const activeInGym = slots.filter(slot => slot.isActive).length;
              this.activeSlotsCount += activeInGym;
              const approvedInGym = slots.filter(slot => slot.isApproved).length;
              this.approvedSlotsCount += approvedInGym;
            });
          },
          error: (err) => console.error('Failed to load slots for stats', err)
        });
      },
      error: (err) => console.error('Failed to load gyms for stats', err)
    });
  }

  /**
   * @method onManageGymProfile
   * @description Navigates to the gym profile management page.
   */
  onManageGymProfile() {
    this.router.navigate(['/gym-owner-dashboard/profile']);
  }

  /**
   * @method onAddSlot
   * @description Navigates to the add slot page.
   */
  onAddSlot() {
    this.router.navigate(['/gym-owner-dashboard/slots']);
  }

  /**
   * @method formatStat
   * @description Formats numbers for display (e.g., 10k for >10000) and adds currency symbol if needed.
   */
  formatStat(value: number, isCurrency: boolean = false): string {
    if (value === undefined || value === null) return '0';
    
    let formattedValue = '';
    if (value >= 10000) {
      formattedValue = (value / 1000).toFixed(1).replace(/\.0$/, '') + 'k';
    } else {
      // Add commas for thousands separator if not using k notation
      formattedValue = value.toLocaleString('en-IN');
    }

    if (isCurrency) {
      return '₹' + formattedValue;
    }
    return formattedValue;
  }
}
