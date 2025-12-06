import { Component, Inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatDialogModule, MAT_DIALOG_DATA, MatDialogRef, MatDialog } from '@angular/material/dialog';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { BookingDialogComponent } from '../../../common/booking-dialog/booking-dialog.component';
import { CustomerService } from '../../../../services/customer-service/customer.service';

import { LtiFlipFitConfirmDialogComponent } from '../../../../components/common/lti-flipfit-confirm-dialog/lti-flipfit-confirm-dialog.component';

/**
 * @author: 
 * @version: 1.0
 * @Component: GymSlotsDialogComponent
 * @description: Dialog component for displaying available slots for a gym and handling booking/waitlist actions.
 */
@Component({
  selector: 'app-gym-slots-dialog',
  standalone: true,
  imports: [CommonModule, MatDialogModule, MatButtonModule, MatIconModule, MatListModule, MatSnackBarModule],
  templateUrl: './gym-slots-dialog.component.html',
  styleUrls: ['./gym-slots-dialog.component.scss']
})
export class GymSlotsDialogComponent implements OnInit {
  customerId: number | null = null;
  
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private dialog: MatDialog,
    private customerService: CustomerService,
    private dialogRef: MatDialogRef<GymSlotsDialogComponent>,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit() {
    const userStr = localStorage.getItem('user');
    if (userStr) {
      const user = JSON.parse(userStr);
      this.customerId = user.customerId;
    }
  }

  /**
   * @methodname: onBook
   * @description: Opens the booking confirmation dialog for a selected slot.
   * @param: slot - The slot to be booked.
   */
  onBook(slot: any) {
    const dialogRef = this.dialog.open(BookingDialogComponent, {
      data: {
        gymName: this.data.gym.name,
        activity: slot.activity,
        date: slot.date,
        time: `${slot.startTime} - ${slot.endTime}`,
        slotId: slot.slotId,
        price: slot.price
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.processBooking(slot);
      }
    });
  }

  /**
   * @methodname: onJoinWaitlist
   * @description: Handles the process of joining the waitlist for a slot.
   * @param: slot - The slot to join waitlist for.
   */
  onJoinWaitlist(slot: any) {
    if (!this.customerId) {
        this.snackBar.open('Please login to join waitlist', 'Close', { duration: 3000 });
        return;
    }
    const customerId = this.customerId; 
    
    const dialogRef = this.dialog.open(LtiFlipFitConfirmDialogComponent, {
      width: '400px',
      data: {
        title: 'Join Waitlist',
        message: `Do you want to join the waitlist for ${slot.activity}?`
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.customerService.joinWaitlist(customerId, slot.slotId).subscribe({
          next: (res) => {
            console.log('Joined waitlist:', res);
            this.snackBar.open('Successfully joined the waitlist!', 'Close', { duration: 3000 });
            this.dialogRef.close(true); // Close dialog on success
          },
          error: (err) => {
            console.error('Failed to join waitlist:', err);
            const errorMsg = this.getErrorMessage(err);
            this.snackBar.open(errorMsg, 'Close', { duration: 3000 });
          }
        });
      }
    });
  }

  /**
   * @methodname: processBooking
   * @description: Processes the booking request after confirmation.
   * @param: slot - The slot to be booked.
   */
  processBooking(slot: any) {
    if (!this.customerId) {
        this.snackBar.open('Please login to book a slot', 'Close', { duration: 3000 });
        return;
    }
    const bookingRequest = {
        customer: { customerId: this.customerId }, // Object structure
        slot: { slotId: slot.slotId },
        center: { centerId: this.data.gym.id }, // Retrieve centerId from passed gym data
        bookingDate: new Date().toISOString().split('T')[0]
    };

    this.customerService.bookSlot(bookingRequest).subscribe({
        next: (response) => {
            console.log('Booking successful', response);
            this.snackBar.open('Booking Successful!', 'Close', { duration: 3000 });
            this.dialogRef.close(true);
        },
        error: (err) => {
            console.error('Booking failed', err);
            const errorMsg = this.getErrorMessage(err);
            this.snackBar.open(errorMsg, 'Close', { duration: 3000 });
        }
    });
  }

  private getErrorMessage(err: any): string {
    if (err.error) {
        try {
            // Try parsing if it's a JSON string
            const errorObj = JSON.parse(err.error);
            if (errorObj && errorObj.message) {
                return errorObj.message;
            }
        } catch (e) {
            // If parsing fails, use the raw string
            return err.error;
        }
        return err.error; // Fallback to raw string if no message field
    }
    return 'An error occurred. Please try again.';
  }
}
