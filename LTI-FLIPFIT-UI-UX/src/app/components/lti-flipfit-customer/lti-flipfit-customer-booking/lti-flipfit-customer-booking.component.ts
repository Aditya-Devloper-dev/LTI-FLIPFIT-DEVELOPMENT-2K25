import { Component, Inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatIconModule } from '@angular/material/icon';
import { MatDialog, MatDialogModule, MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

// Booking Dialog Component
@Component({
  selector: 'app-booking-dialog',
  standalone: true,
  imports: [CommonModule, MatDialogModule, MatButtonModule],
  template: `
    <h2 mat-dialog-title>Confirm Booking</h2>
    <mat-dialog-content>
      <div class="booking-details">
        <div class="detail-row">
          <span class="label">Gym:</span>
          <span class="value">{{data.gymName}}</span>
        </div>
        <div class="detail-row">
          <span class="label">Activity:</span>
          <span class="value">{{data.activity}}</span>
        </div>
        <div class="detail-row">
          <span class="label">Date:</span>
          <span class="value">{{data.date}}</span>
        </div>
        <div class="detail-row">
          <span class="label">Time:</span>
          <span class="value">{{data.time}}</span>
        </div>
        <div class="detail-row">
          <span class="label">Slot ID:</span>
          <span class="value">{{data.slotId}}</span>
        </div>
      </div>
    </mat-dialog-content>
    <mat-dialog-actions align="end">
      <button mat-button mat-dialog-close>Cancel</button>
      <button mat-flat-button color="primary" [mat-dialog-close]="true">Confirm Booking</button>
    </mat-dialog-actions>
  `,
  styles: [`
    .booking-details {
      display: flex;
      flex-direction: column;
      gap: 12px;
      min-width: 300px;
    }
    .detail-row {
      display: flex;
      justify-content: space-between;
    }
    .label {
      font-weight: 500;
      color: #666;
    }
    .value {
      font-weight: 600;
      color: #333;
    }
  `]
})
export class BookingDialogComponent {
  constructor(@Inject(MAT_DIALOG_DATA) public data: any) {}
}

@Component({
  selector: 'app-lti-flipfit-customer-booking',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatCardModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatIconModule,
    MatDialogModule
  ],
  templateUrl: './lti-flipfit-customer-booking.component.html',
  styleUrl: './lti-flipfit-customer-booking.component.scss'
})
export class LtiFlipFitCustomerBookingComponent {
  searchFilters = {
    location: 'Bangalore',
    date: new Date(),
    time: '',
    activity: ''
  };

  gyms = [
    {
      id: '1',
      name: 'FitZone Indiranagar',
      location: 'Bangalore, 18 Ar 202K',
      image: 'assets/gym1.jpg',
      tags: ['Nominal', 'Indiranagar'],
      activity: 'Yoga',
      time: '10:00 AM - 11:00 AM',
      slotId: '18 Ar 202K'
    },
    {
      id: '2',
      name: 'Muscle Works Koramangala',
      location: 'Bangalore, 18 Ar 201X',
      image: 'assets/gym2.jpg',
      tags: ['Muscle Works', 'Koramangala'],
      activity: 'HIIT',
      time: '06:00 PM - 07:00 PM',
      slotId: '18 Ar 201X'
    }
  ];

  constructor(private dialog: MatDialog) {}

  onSearch() {
    console.log('Searching with filters:', this.searchFilters);
  }

  openBookingDialog(gym: any) {
    const dialogRef = this.dialog.open(BookingDialogComponent, {
      data: {
        gymName: gym.name,
        activity: gym.activity,
        date: this.searchFilters.date.toDateString(),
        time: gym.time,
        slotId: gym.slotId
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        console.log('Booking confirmed for', gym.name);
        // Implement booking logic here
      }
    });
  }
}
