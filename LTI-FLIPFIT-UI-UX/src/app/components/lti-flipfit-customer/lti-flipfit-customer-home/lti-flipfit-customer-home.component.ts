import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { Router, RouterModule } from '@angular/router';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { CustomerService } from '../../../services/customer-service/customer.service';
import { BookingDialogComponent } from '../../common/booking-dialog/booking-dialog.component';

@Component({
  selector: 'app-lti-flipfit-customer-home',
  standalone: true,
  imports: [
    CommonModule,
    MatButtonModule,
    MatCardModule,
    MatIconModule,
    RouterModule,
    MatDialogModule
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
    private dialog: MatDialog
  ) {}

  ngOnInit() {
    const userStr = localStorage.getItem('user');
    if (userStr) {
      const user = JSON.parse(userStr);
      this.customerId = user.customerId;
    }

    this.customerService.getAllSlots().subscribe(slots => {
      // Show all data
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
        availableSeats: slot.availableSeats
      }));
    });
  }

  onJoinWaitlist(classItem: any) {
    if (!this.customerId) {
        alert('Please login to join waitlist');
        return;
    }
    const customerId = this.customerId; 
    
    if(confirm(`Do you want to join the waitlist for ${classItem.title}?`)) {
      this.customerService.joinWaitlist(customerId, classItem.slotId).subscribe({
        next: (res) => {
          console.log('Joined waitlist:', res);
          alert('Successfully joined the waitlist!');
        },
        error: (err) => {
          console.error('Failed to join waitlist:', err);
          alert('Failed to join waitlist. Please try again.');
        }
      });
    }
  }

  onFindGym() {
    this.router.navigate(['/customer-dashboard/workouts']);
  }

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

  bookSlot(slot: any) {
    if (!this.customerId) {
        alert('Please login to book a slot');
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
            alert('Booking Successful!');
        },
        error: (err) => {
            console.error('Booking failed', err);
            alert('Booking Failed');
        }
    });
  }
}
