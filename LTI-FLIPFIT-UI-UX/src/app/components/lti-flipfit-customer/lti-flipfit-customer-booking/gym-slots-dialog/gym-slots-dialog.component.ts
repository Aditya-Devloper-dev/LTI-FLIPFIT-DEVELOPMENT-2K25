import { Component, Inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatDialogModule, MAT_DIALOG_DATA, MatDialogRef, MatDialog } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { BookingDialogComponent } from '../../../common/booking-dialog/booking-dialog.component';
import { CustomerService } from '../../../../services/customer-service/customer.service';

@Component({
  selector: 'app-gym-slots-dialog',
  standalone: true,
  imports: [CommonModule, MatDialogModule, MatButtonModule, MatIconModule, MatListModule],
  templateUrl: './gym-slots-dialog.component.html',
  styleUrls: ['./gym-slots-dialog.component.scss']
})
export class GymSlotsDialogComponent implements OnInit {
  customerId: number | null = null;
  
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private dialog: MatDialog,
    private customerService: CustomerService,
    private dialogRef: MatDialogRef<GymSlotsDialogComponent>
  ) {}

  ngOnInit() {
    const userStr = localStorage.getItem('user');
    if (userStr) {
      const user = JSON.parse(userStr);
      this.customerId = user.customerId;
    }
  }

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

  onJoinWaitlist(slot: any) {
    if (!this.customerId) {
        alert('Please login to join waitlist');
        return;
    }
    const customerId = this.customerId; 
    
    if(confirm(`Do you want to join the waitlist for ${slot.activity}?`)) {
      this.customerService.joinWaitlist(customerId, slot.slotId).subscribe({
        next: (res) => {
          console.log('Joined waitlist:', res);
          alert('Successfully joined the waitlist!');
          this.dialogRef.close(true); // Close dialog on success
        },
        error: (err) => {
          console.error('Failed to join waitlist:', err);
          alert('Failed to join waitlist. Please try again.');
        }
      });
    }
  }

  processBooking(slot: any) {
    if (!this.customerId) {
        alert('Please login to book a slot');
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
            alert('Booking Successful!');
            this.dialogRef.close(true);
        },
        error: (err) => {
            console.error('Booking failed', err);
            alert('Booking Failed: ' + (err.error?.message || 'Server error'));
        }
    });
  }
}
