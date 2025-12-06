import { Component, OnInit } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatTableModule } from '@angular/material/table';
import { MatTabsModule } from '@angular/material/tabs';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { CustomerService } from '../../../services/customer-service/customer.service';
import { UserService } from '../../../services/user-service/user.service';
import { LtiFlipFitNotificationComponent } from '../../common/lti-flipfit-notification/lti-flipfit-notification.component';

@Component({
  selector: 'app-lti-flipfit-customer-profile',
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatInputModule,
    MatFormFieldModule,
    MatButtonModule,
    MatIconModule,
    MatTableModule,
    MatTabsModule,
    MatSnackBarModule,
    MatSnackBarModule,
    DatePipe,
    LtiFlipFitNotificationComponent
  ],
  templateUrl: './lti-flipfit-customer-profile.component.html',
  styleUrl: './lti-flipfit-customer-profile.component.scss'
})
export class LtiFlipfitCustomerProfileComponent implements OnInit {
  customerProfile: any = {};
  bookings: any[] = [];
  displayedColumns: string[] = ['bookingId', 'gymName', 'activity', 'date', 'status'];
  
  userId: number | null = null;
  customerId: number | null = null;
  isLoading = false;

  constructor(
    private customerService: CustomerService,
    private userService: UserService,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    const storedUser = localStorage.getItem('user');
    if (storedUser) {
      const user = JSON.parse(storedUser);
      this.userId = user.userId;
      this.customerId = user.customerId;
      
      this.loadProfile();
      this.loadBookings();
    }
  }

  loadProfile() {
    if (this.userId) {
      this.userService.getUserById(this.userId).subscribe({
        next: (response) => {
          this.customerProfile = response;
        },
        error: (error) => {
          console.error('Error fetching profile:', error);
          this.showNotification('Failed to load profile details');
        }
      });
    }
  }

  loadBookings() {
    if (this.customerId) {
        this.isLoading = true;
        this.customerService.getCustomerBookings(this.customerId).subscribe({
            next: (response) => {
                this.bookings = response.map((booking: any) => ({
                    bookingId: booking.bookingId,
                    gymName: booking.slot?.center?.centerName || 'Unknown Gym',
                    activity: 'Gym Session', // Default or derived from slot
                    date: booking.bookingDate,
                    time: `${booking.slot?.startTime} - ${booking.slot?.endTime}`,
                    status: this.deriveStatus(booking)
                }));
                this.isLoading = false;
            },
            error: (error) => {
                console.error('Error fetching bookings:', error);
                this.showNotification('Failed to load booking history');
                this.isLoading = false;
            }
        });
    }
  }

  deriveStatus(booking: any): string {
      // Logic to derive status if not explicitly provided, or normalize it
      // For now, assuming booking.status exists or using a cancelled flag if available
      if (booking.isCancelled) return 'CANCELLED';
      // Basic check, adjust based on actual API response
      return booking.status || 'BOOKED'; 
  }

  private showNotification(message: string) {
    this.snackBar.open(message, 'Close', {
      duration: 3000,
      horizontalPosition: 'end',
      verticalPosition: 'top'
    });
  }
}
