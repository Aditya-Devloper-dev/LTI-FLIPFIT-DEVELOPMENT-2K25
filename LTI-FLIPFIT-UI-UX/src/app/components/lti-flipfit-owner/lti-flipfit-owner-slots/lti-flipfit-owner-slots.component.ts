import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatTableModule } from '@angular/material/table';
import { MatChipsModule } from '@angular/material/chips';

@Component({
  selector: 'app-lti-flipfit-owner-slots',
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatTableModule,
    MatChipsModule
  ],
  templateUrl: './lti-flipfit-owner-slots.component.html',
  styleUrl: './lti-flipfit-owner-slots.component.scss'
})
export class LtiFlipFitOwnerSlotsComponent {
  showAddForm = false;

  gyms = [
    { id: '1', name: 'FitLife Studio - Downtown Branch' },
    { id: '2', name: 'PowerHouse Gym - Westside' }
  ];

  slots = [
    { date: '2023-11-24', time: '10:00 AM - 11:00 AM', activity: 'Yoga', capacity: 20, booked: 15, status: 'Active' },
    { date: '2023-11-24', time: '06:00 PM - 07:00 PM', activity: 'HIIT', capacity: 15, booked: 15, status: 'Full' },
    { date: '2023-11-25', time: '07:00 AM - 08:00 AM', activity: 'Cardio', capacity: 30, booked: 5, status: 'Active' }
  ];

  displayedColumns: string[] = ['date', 'time', 'activity', 'capacity', 'booked', 'status', 'actions'];

  toggleAddForm() {
    this.showAddForm = !this.showAddForm;
  }

  onSubmit() {
    console.log('Slot added');
    this.showAddForm = false;
  }

  onCancel() {
    this.showAddForm = false;
  }
}
