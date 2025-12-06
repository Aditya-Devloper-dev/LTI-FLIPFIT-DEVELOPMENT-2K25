import { Component, Inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatDialogModule, MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';

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
          <span class="label">Time:</span>
          <span class="value">{{data.time}}</span>
        </div>
        <div class="detail-row">
          <span class="label">Price:</span>
          <span class="value">₹{{data.price}}</span>
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
