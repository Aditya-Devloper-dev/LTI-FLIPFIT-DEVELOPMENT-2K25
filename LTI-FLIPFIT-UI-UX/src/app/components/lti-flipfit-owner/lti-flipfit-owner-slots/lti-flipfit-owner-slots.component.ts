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
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { FormsModule } from '@angular/forms';
import { OwnerService } from '../../../services/owner-service/owner.service';
import { UserService } from '../../../services/user-service/user.service';
import { GymCenter } from '../../../models/gym-center/gym-center.model';
import { GymSlot } from '../../../models/gym-slot/gym-slot.model';
import { MatSnackBar } from '@angular/material/snack-bar';

/**
 * @author: 
 * @version: 1.0
 * @description: Component for managing gym slots (view, add, toggle status).
 */
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
    MatChipsModule,
    MatSlideToggleModule,
    FormsModule
  ],
  templateUrl: './lti-flipfit-owner-slots.component.html',
  styleUrl: './lti-flipfit-owner-slots.component.scss'
})
export class LtiFlipFitOwnerSlotsComponent {
  showAddForm = false;
  gyms: GymCenter[] = [];
  slots: any[] = []; // Using any for now to match backend response structure if needed, or GymSlot
  selectedGymId: number | null = null;
  displayedColumns: string[] = ['activity', 'time', 'capacity', 'booked', 'status', 'approvalStatus', 'actions'];
  ownerId: number | null = null;
  
  newSlot: GymSlot = {
    centerId: 0,
    date: '',
    startTime: '',
    endTime: '',
    capacity: 10,
    availableSeats: 0,
    price: 0,
    activity: ''
  };

  constructor(
    private ownerService: OwnerService,
    private userService: UserService,
    private snackBar: MatSnackBar
  ) {}

  /**
   * @method ngOnInit
   * @description Initializes the component and loads gyms for the owner.
   */
  ngOnInit() {
    const userStr = localStorage.getItem('user');
    if (userStr) {
      const user = JSON.parse(userStr);
      if (user.userId) {
        this.userService.getUserById(user.userId).subscribe(u => {
             if (user.ownerId) {
                 this.ownerId = user.ownerId;
                 this.loadGyms();
             }
        });
      }
    }
  }

  /**
   * @method loadGyms
   * @description Loads the gyms owned by the current user.
   */
  loadGyms() {
    if (this.ownerId) {
      this.ownerService.getGymsByOwnerId(this.ownerId).subscribe({
        next: (data) => {
          this.gyms = data;
        },
        error: (err) => console.error('Failed to load gyms', err)
      });
    }
  }

  /**
   * @method onGymSelect
   * @description Handles gym selection change and loads slots for the selected gym.
   */
  onGymSelect() {
    if (this.selectedGymId) {
      this.loadSlots();
    }
  }

  /**
   * @method loadSlots
   * @description Loads slots for the selected gym.
   */
  loadSlots() {
    if (this.selectedGymId) {
      this.ownerService.getSlotsByCenterId(this.selectedGymId).subscribe({
        next: (data) => {
          this.slots = data;
        },
        error: (err) => console.error('Failed to load slots', err)
      });
    }
  }

  /**
   * @method toggleSlotStatus
   * @param slot - The slot object.
   * @description Toggles the active status of a slot.
   */
  toggleSlotStatus(slot: any) {
    if (this.ownerId && slot.slotId) {
      this.ownerService.toggleSlotActive(slot.slotId, this.ownerId).subscribe({
        next: (res) => {
          this.snackBar.open('Slot status updated', 'Close', { duration: 3000 });
          slot.isActive = !slot.isActive; // Optimistic update or reload
          // this.loadSlots(); // Reload to be sure
        },
        error: (err) => {
          console.error('Failed to toggle slot', err);
          this.snackBar.open('Failed to update status', 'Close', { duration: 3000 });
        }
      });
    }
  }

  /**
   * @method toggleAddForm
   * @description Toggles the visibility of the add slot form.
   */
  toggleAddForm() {
    this.showAddForm = !this.showAddForm;
  }

  /**
   * @method onSubmit
   * @description Submits the new slot form.
   */
  onSubmit() {
    if (this.ownerId && this.selectedGymId) {
      this.newSlot.centerId = this.selectedGymId;
      // Ensure date is formatted correctly if needed, or use string from input
      // The backend expects GymSlot object.
      
      this.ownerService.addSlot(this.newSlot, this.selectedGymId, this.ownerId).subscribe({
        next: (res) => {
          this.snackBar.open('Slot added successfully', 'Close', { duration: 3000 });
          this.showAddForm = false;
          this.loadSlots();
          // Reset form
          this.newSlot = {
            centerId: 0,
            date: '',
            startTime: '',
            endTime: '',
            capacity: 10,
            availableSeats: 0,
            price: 0,
            activity: ''
          };
        },
        error: (err) => {
          console.error('Failed to add slot', err);
          let errorMessage = 'Failed to add slot';
          
          if (err.error) {
            if (typeof err.error === 'object' && err.error.message) {
              errorMessage = err.error.message;
            } else if (typeof err.error === 'string') {
              try {
                const parsed = JSON.parse(err.error);
                errorMessage = parsed.message || err.error;
              } catch (e) {
                errorMessage = err.error;
              }
            }
          } else if (err.message) {
            errorMessage = err.message;
          }
          
          this.snackBar.open(errorMessage, 'Close', { duration: 5000 });
        }
      });
    } else {
      this.snackBar.open('Please select a gym first', 'Close', { duration: 3000 });
    }
  }

  /**
   * @method onCancel
   * @description Cancels the add slot operation.
   */
  onCancel() {
    this.showAddForm = false;
  }
}
