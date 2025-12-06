import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { Router } from '@angular/router';
import { AdminService } from '../../../services/admin-service/admin.service';
import { UserService } from '../../../services/user-service/user.service';
import { LtiFlipFitNotificationComponent } from '../../common/lti-flipfit-notification/lti-flipfit-notification.component';
import { RoleType } from '../../../models/enums/role.type';

/**
 * @author: 
 * @version: 1.0
 * @Component: LtiFlipFitAdminOverviewComponent
 * @description: Dashboard overview component for admin, displaying high-level statistics like total gyms, users, bookings, and revenue.
 */
@Component({
  selector: 'app-lti-flipfit-admin-overview',
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatListModule,
    LtiFlipFitNotificationComponent
  ],
  templateUrl: './lti-flipfit-admin-overview.component.html',
  styleUrl: './lti-flipfit-admin-overview.component.scss'
})
export class LtiFlipFitAdminOverviewComponent implements OnInit {
  // Gym Stats
  totalGyms = 0;
  pendingGymsCount = 0;
  approvedGymsCount = 0;

  // User Stats
  totalUsers = 0;
  customerCount = 0;
  ownerCount = 0;

  // Booking Stats
  totalBookings = 0;
  bookedCount = 0;
  attendedCount = 0; // Pending or Attended

  // Revenue Stats
  monthlyRevenue = 0;
  weeklyRevenue = 0;
  dailyRevenue = 0;

  // Recent Action lists
  pendingGymsList: any[] = [];
  pendingOwnersList: any[] = [];
  
  constructor(
    private adminService: AdminService,
    private userService: UserService,
    private router: Router
  ) {}

  /**
   * @methodname: ngOnInit
   * @description: Lifecycle hook. Triggers loading of all dashboard statistics.
   */
  ngOnInit(): void {
    this.loadGymStats();
    this.loadUserStats();
    this.loadBookingAndRevenueStats();
  }

  /**
   * @methodname: viewGymDetails
   * @description: Navigates to the details page of a specific gym center.
   * @param: gymId - The gym's ID.
   */
  viewGymDetails(gymId: number) {
    this.router.navigate(['/admin-dashboard/gyms', gymId]);
  }

  /**
   * @methodname: viewUserDetails
   * @description: Navigates to the details page of a specific user.
   * @param: userId - The user's ID.
   */
  viewUserDetails(userId: number) {
    this.router.navigate(['/admin-dashboard/users', userId]);
  }

  /**
   * @methodname: loadGymStats
   * @description: Fetches and aggregates gym-related statistics (total, approved, pending) and lists.
   */
  loadGymStats() {
    this.adminService.getAllCenters().subscribe({
      next: (centers) => {
        console.log('Centers:', centers);
        this.totalGyms = centers.length;
        this.approvedGymsCount = centers.filter(c => c.isApproved).length;
        this.pendingGymsCount = this.totalGyms - this.approvedGymsCount;
      },
      error: (e) => console.error('Error loading gyms', e)
    });

    this.adminService.getPendingCenters().subscribe({
      next: (pending) => {
        this.pendingGymsList = pending;
      }
    });

    this.adminService.getPendingOwners().subscribe({
      next: (pending) => {
        this.pendingOwnersList = pending;
      },
      error: (e) => console.error('Error loading pending owners', e)
    });
  }

  /**
   * @methodname: loadUserStats
   * @description: Fetches and aggregates user-related statistics (total, customers, owners).
   */
  loadUserStats() {
    this.userService.getAllUsers().subscribe({
      next: (users) => {
        console.log('Users:', users);
        this.totalUsers = users.length;
        this.customerCount = users.filter((u: any) => u.role?.roleName === RoleType.CUSTOMER).length;
        this.ownerCount = users.filter((u: any) => u.role?.roleName === RoleType.OWNER).length;
      },
      error: (e) => console.error('Error loading users', e)
    });
  }

  /**
   * @methodname: loadBookingAndRevenueStats
   * @description: Fetches payment data to calculate revenue (monthly, weekly, daily) and total bookings.
   */
  loadBookingAndRevenueStats() {
    this.adminService.viewPayments('MONTHLY').subscribe(payments => {
         this.monthlyRevenue = payments.reduce((sum: number, p: any) => sum + (p.amount || 0), 0);
    });
    this.adminService.viewPayments('WEEKLY').subscribe(payments => {
         this.weeklyRevenue = payments.reduce((sum: number, p: any) => sum + (p.amount || 0), 0);
    });
    this.adminService.viewPayments('DAILY').subscribe(payments => {
         this.dailyRevenue = payments.reduce((sum: number, p: any) => sum + (p.amount || 0), 0);
    });

    // Approximate bookings from all payments
    this.adminService.viewPayments('ALL').subscribe(payments => {
        this.totalBookings = payments.length; 
        this.bookedCount = payments.length; // Assuming all payments = booked
        // Attempt to find attended status if available in nested object
        this.attendedCount = payments.filter((p: any) => p.booking?.status === 'ATTENDED').length; 
    });
  }

  /**
   * @methodname: formatStat
   * @description: Reformats numbers or currency values for display.
   * @param: value - The number to format.
   * @param: isCurrency - Whether to format as currency (INR).
   * @return: Formatted string.
   */
  formatStat(value: number, isCurrency: boolean = false): string {
    if (value === undefined || value === null) return '0';
    if (isCurrency) {
      return '₹' + value.toLocaleString('en-IN');
    }
    return value.toLocaleString('en-IN');
  }
}
