import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { Router, RouterModule } from '@angular/router';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { CustomerService } from '../../../services/customer-service/customer.service';
import { BookingDialogComponent } from '../../common/booking-dialog/booking-dialog.component';
import { LtiFlipFitConfirmDialogComponent } from '../../common/lti-flipfit-confirm-dialog/lti-flipfit-confirm-dialog.component';

/**
 * @author: 
 * @version: 1.0
 * @Component: LtiFlipFitCustomerHomeComponent
 * @description: Home component for customers, displaying popular classes and quick booking options.
 */
@Component({
  selector: 'app-lti-flipfit-customer-home',
  standalone: true,
  imports: [
    CommonModule,
    MatButtonModule,
    MatCardModule,
    MatIconModule,
    RouterModule,
    MatDialogModule,
    MatSnackBarModule
  ],
  templateUrl: './lti-flipfit-customer-home.component.html',
  styleUrl: './lti-flipfit-customer-home.component.scss'
})
export class LtiFlipFitCustomerHomeComponent implements OnInit {
  customerId: number | null = null;
  popularClasses: any[] = [];
  stockImages = [
    'https://images.unsplash.com/photo-1534438327276-14e5300c3a48?q=80&w=600&auto=format&fit=crop', // Gym 1
    'https://images.unsplash.com/photo-1517836357463-d25dfeac3438?q=80&w=600&auto=format&fit=crop', // Gym 2
    'https://images.unsplash.com/photo-1571019614242-c5c5dee9f50b?q=80&w=600&auto=format&fit=crop', // Gym 3
    'https://images.unsplash.com/photo-1581009146145-b5ef050c2e1e?q=80&w=600&auto=format&fit=crop', // Gym 4
    'https://images.unsplash.com/photo-1599058945522-28d584b6f0ff?q=80&w=600&auto=format&fit=crop'  // Gym 5
  ];

  constructor(
    private router: Router,
    private customerService: CustomerService,
    private dialog: MatDialog,
    private snackBar: MatSnackBar
  ) {}

  /**
   * @methodname: ngOnInit
   * @description: Lifecycle hook. Initializes user context and loads popular classes.
   */
  ngOnInit() {
    const userStr = localStorage.getItem('user');
    if (userStr) {
      const user = JSON.parse(userStr);
      this.customerId = user.customerId;
    }

    this.customerService.getAllSlots().subscribe(slots => {
      this.popularClasses = slots.map((slot, index) => ({
        slotId: slot.slotId,
        title: slot.activity && slot.activity.trim() !== '' ? slot.activity : 'Workout Session', // Use activity if available
        location: slot.center ? `${slot.center.centerName}, ${slot.center.city}` : 'Unknown Location',
        time: `${slot.startTime.substring(0, 5)} - ${slot.endTime.substring(0, 5)}`,
        price: slot.price,
        date: slot.date,
        image: this.stockImages[index % this.stockImages.length],
        gymName: slot.center ? slot.center.centerName : 'Gym',
        activity: slot.activity,
        // Map availability from backend
        availableSeats: slot.availableSeats,
        centerId: slot.center ? slot.center.centerId : null // Ensure centerId is mapped for booking
      }));
    });
  }

  /**
   * @methodname: onJoinWaitlist
   * @description: Allows a customer to join the waitlist for a specific class.
   * @param: classItem - The class/slot details.
   */
  onJoinWaitlist(classItem: any) {
    if (!this.customerId) {
        this.snackBar.open('Please login to join waitlist', 'Close', { duration: 3000 });
        return;
    }
    const customerId = this.customerId; 
    
    const dialogRef = this.dialog.open(LtiFlipFitConfirmDialogComponent, {
      width: '400px',
      data: {
        title: 'Join Waitlist',
        message: `Do you want to join the waitlist for ${classItem.title}?`
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.customerService.joinWaitlist(customerId, classItem.slotId).subscribe({
          next: (res) => {
            console.log('Joined waitlist:', res);
            this.snackBar.open('Successfully joined the waitlist!', 'Close', { 
              duration: 3000,
              panelClass: ['success-snackbar']
            });
          },
          error: (err) => {
            console.error('Failed to join waitlist:', err);
            const errorMsg = this.getErrorMessage(err);
            this.snackBar.open(errorMsg, 'Close', { 
              duration: 3000, 
              panelClass: ['error-snackbar']
            });
          }
        });
      }
    });
  }

  /**
   * @methodname: onFindGym
   * @description: Navigates to the workout/gyms browsing page.
   */
  onFindGym() {
    this.router.navigate(['/customer-dashboard/workouts']);
  }

  /**
   * @methodname: openBookingDialog
   * @description: Opens the booking confirmation dialog for a slot.
   * @param: classItem - The slot details.
   */
  openBookingDialog(classItem: any) {
    const dialogRef = this.dialog.open(BookingDialogComponent, {
      data: {
        gymName: classItem.gymName || classItem.location,
        activity: classItem.title,
        date: classItem.date,
        time: classItem.time,
        slotId: classItem.slotId,
        price: classItem.price
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.bookSlot(classItem);
      }
    });
  }

  /**
   * @methodname: bookSlot
   * @description: Processes the slot booking.
   * @param: slot - The slot object to be booked.
   */
  bookSlot(slot: any) {
    if (!this.customerId) {
        this.snackBar.open('Please login to book a slot', 'Close', { duration: 3000 });
        return;
    }
    const bookingRequest = {
        customer: { customerId: this.customerId },
        slot: { slotId: slot.slotId },
        center: { centerId: slot.center ? slot.center.centerId : slot.centerId }, // Handle both cases just in case
        bookingDate: new Date().toISOString().split('T')[0]
    };

    this.customerService.bookSlot(bookingRequest).subscribe({
        next: (response) => {
            console.log('Booking successful', response);
            this.snackBar.open('Booking Successful!', 'Close', {
                duration: 3000,
                panelClass: ['success-snackbar']
            });
        },
        error: (err) => {
            console.error('Booking failed', err);
            const errorMsg = this.getErrorMessage(err);
            this.snackBar.open(errorMsg, 'Close', {
                duration: 3000,
                panelClass: ['error-snackbar']
            });
        }
    });
  }

  /**
   * @methodname: getErrorMessage
   * @description: Parses backend error messages logic.
   * @param: err - Error object.
   * @return: String message.
   */
  private getErrorMessage(err: any): string {
    if (err.error) {
        try {
            const errorObj = JSON.parse(err.error);
            if (errorObj && errorObj.message) {
                return errorObj.message;
            }
        } catch (e) {
            return err.error;
        }
        return err.error;
    }
    return 'An error occurred. Please try again.';
  }
}
