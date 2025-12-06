import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Location } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatTableModule } from '@angular/material/table';
import { MatChipsModule } from '@angular/material/chips';
import { UserService } from '../../../services/user-service/user.service';
import { CustomerService } from '../../../services/customer-service/customer.service';
import { OwnerService } from '../../../services/owner-service/owner.service';
import { AdminService } from '../../../services/admin-service/admin.service';
import { RoleType } from '../../../models/enums/role.type';

/**
 * @author: 
 * @version: 1.0
 * @Component: LtiFlipFitAdminUserDetailsComponent
 * @description: Component for displaying detailed information about a user (Customer or Owner) and performing admin actions like approval.
 */
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
export class LtiFlipFitAdminUserDetailsComponent implements OnInit {
  displayedColumns: string[] = [];
  tableData: any[] = [];
  user: any = null;
  stats: any = {};
  roleType = RoleType;

  constructor(
    private location: Location,
    private route: ActivatedRoute,
    private userService: UserService,
    private customerService: CustomerService,
    private ownerService: OwnerService,
    private adminService: AdminService
  ) {}

  /**
   * @methodname: ngOnInit
   * @description: Lifecycle hook. Reads user ID from route and initiates detail loading.
   */
  ngOnInit() {
    const userId = this.route.snapshot.paramMap.get('id');
    if (userId) {
      this.loadUserDetails(userId);
    }
  }

  /**
   * @methodname: loadUserDetails
   * @description: Fetches base user details and directs to role-specific data loading (Customer or Owner).
   * @param: userId - The user ID string from route.
   */
  loadUserDetails(userId: string) {
    this.userService.getAllUsers().subscribe(users => {
      const foundUser = users.find(u => u.userId.toString() === userId);
      if (foundUser) {
        this.user = foundUser;
        this.user.roleName = foundUser.role?.roleName;
        // Default isApproved to true for customers, logic will update for owners
        this.user.isApproved = true;
        this.user.updatedAt = foundUser.updatedAt;

        if (this.user.roleName === RoleType.CUSTOMER) {
          this.loadCustomerData(foundUser.userId);
        } else if (this.user.roleName === RoleType.OWNER) {
          this.loadOwnerData(foundUser.userId);
        }
      }
    });
  }

  /**
   * @methodname: approveOwner
   * @description: Approves a gym owner account.
   */
  approveOwner() {
    if (this.user && this.user.ownerId) {
      this.adminService.approveOwner(this.user.ownerId).subscribe({
        next: () => {
          this.user.isApproved = true;
          this.loadOwnerData(this.user.userId); // Reload to metrics
        },
        error: (err) => console.error('Approval failed', err)
      });
    }
  }

  /**
   * @methodname: loadCustomerData
   * @description: Fetches specific data for customers (bookings, stats).
   * @param: userId - The user ID.
   */
  loadCustomerData(userId: number) {
    this.customerService.getCustomerByUserId(userId).subscribe(customer => {
      if (customer) {
        this.customerService.getCustomerBookings(customer.customerId).subscribe(bookings => {
          this.tableData = bookings.map(b => ({
            date: b.bookingDate,
            gymName: b.center?.centerName || 'N/A',
            activity: 'Workout', // Placeholder as booking doesn't have activity name directly
            time: `${b.slot?.startTime} - ${b.slot?.endTime}`,
            status: b.status
          }));
          this.displayedColumns = ['date', 'gymName', 'activity', 'time', 'status'];
          
          // Calculate Stats
          const totalBookings = bookings.length;
          const attended = bookings.filter(b => b.status === 'ATTENDED').length;
          const cancelled = bookings.filter(b => b.status === 'CANCELLED').length;
          const lastActive = this.user.updatedAt || 'N/A';

          this.stats = {
            box1: { value: totalBookings, label: 'Total Bookings' },
            box2: { value: attended, label: 'Attended Workouts', class: 'success' },
            box3: { value: cancelled, label: 'Cancelled Workouts', class: 'error' },
            box4: { value: lastActive, label: 'Last Updated', class: 'info' }
          };
        });
      }
    });
  }

  /**
   * @methodname: loadOwnerData
   * @description: Fetches specific data for owners (gyms, approval status, stats).
   * @param: userId - The user ID.
   */
  loadOwnerData(userId: number) {
    this.ownerService.getOwnerByUserId(userId).subscribe(owner => {
      if (owner) {
        this.user.ownerId = owner.ownerId;
        this.user.isApproved = owner.approved;
        this.user.businessName = owner.businessName;
        this.user.gstNumber = owner.gstNumber;
        this.user.panNumber = owner.panNumber;

        this.ownerService.getGymsByOwnerId(owner.ownerId).subscribe(gyms => {
          this.tableData = gyms.map(g => ({
            name: g.centerName,
            location: g.city,
            slots: 'N/A', // slotCount not available in GymCenter model
            status: g.isActive ? 'Active' : 'Inactive'
          }));
          this.displayedColumns = ['name', 'location', 'slots', 'status'];

          // Calculate Stats
          const totalGyms = gyms.length;
          const activeGyms = gyms.filter(g => g.isActive).length;
          const pendingGyms = totalGyms - activeGyms;
          const lastActive = this.user.updatedAt || 'N/A';

          this.stats = {
            box1: { value: totalGyms, label: 'Total Gyms' },
            box2: { value: activeGyms, label: 'Active Gyms', class: 'success' },
            box3: { value: pendingGyms, label: 'Pending Gyms', class: 'warning' },
            box4: { value: lastActive, label: 'Last Updated', class: 'info' }
          };
        });
      }
    });
  }

  /**
   * @methodname: goBack
   * @description: Navigates back to the users list.
   */
  goBack() {
    this.location.back();
  }
}
