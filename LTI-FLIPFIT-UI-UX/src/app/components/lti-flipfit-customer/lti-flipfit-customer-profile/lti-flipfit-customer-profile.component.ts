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
import { MatDialog } from '@angular/material/dialog';
import { LtiFlipFitConfirmDialogComponent } from '../../common/lti-flipfit-confirm-dialog/lti-flipfit-confirm-dialog.component';

/**
 * @author: 
 * @version: 1.0
 * @Component: LtiFlipfitCustomerProfileComponent
 * @description: Component for managing customer profile, viewing details, and booking history.
 */
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
  displayedColumns: string[] = ['bookingId', 'gymName', 'activity', 'date', 'status', 'actions']; // Added actions column
  
  userId: number | null = null;
  customerId: number | null = null;
  isLoading = false;

  constructor(
    private customerService: CustomerService,
    private userService: UserService,
    private snackBar: MatSnackBar,
    private dialog: MatDialog
  ) {}

  /**
   * @methodname: ngOnInit
   * @description: Lifecycle hook. Initializes user data, profile, and bookings.
   */
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

  /**
   * @methodname: loadProfile
   * @description: Fetches user profile details from the backend.
   */
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

  /**
   * @methodname: loadBookings
   * @description: Fetches booking history for the customer.
   */
  loadBookings() {
    if (this.customerId) {
        this.isLoading = true;
        this.customerService.getCustomerBookings(this.customerId).subscribe({
            next: (response) => {
                console.log('Fetched bookings for customer:', this.customerId, response);
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

  /**
   * @methodname: deriveStatus
   * @description: Helper to determine or normalize the booking status.
   * @param: booking - The booking object.
   * @return: Status string.
   */
  deriveStatus(booking: any): string {
      // Logic to derive status if not explicitly provided, or normalize it
      // For now, assuming booking.status exists or using a cancelled flag if available
      if (booking.isCancelled) return 'CANCELLED';
      // Basic check, adjust based on actual API response
      return booking.status || 'BOOKED'; 
  }

  /**
   * @methodname: showNotification
   * @description: Displays a snackbar notification.
   * @param: message - Message to display.
   */
  private showNotification(message: string) {
    this.snackBar.open(message, 'Close', {
      duration: 3000,
      horizontalPosition: 'end',
      verticalPosition: 'top'
    });
  }

  /**
   * @methodname: cancelBooking
   * @description: Initiates the cancellation process for a booking.
   * @param: booking - The booking to cancel.
   */
  cancelBooking(booking: any) {
    const dialogRef = this.dialog.open(LtiFlipFitConfirmDialogComponent, {
      width: '400px',
      data: {
        title: 'Cancel Booking',
        message: `Are you sure you want to cancel your booking at ${booking.gymName}?`
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.customerService.cancelBooking(booking.bookingId).subscribe({
          next: () => {
            this.showNotification('Booking cancelled successfully');
            this.loadBookings(); // Reload to update list
          },
          error: (err) => {
            console.error('Cancellation failed:', err);
            this.showNotification('Failed to cancel booking');
          }
        });
      }
    });
  }
}
