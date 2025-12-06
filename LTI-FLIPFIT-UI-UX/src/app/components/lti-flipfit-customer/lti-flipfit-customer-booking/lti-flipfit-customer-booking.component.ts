import { Component, OnInit } from '@angular/core';
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
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { CustomerService } from '../../../services/customer-service/customer.service';
import { BookingDialogComponent } from '../../common/booking-dialog/booking-dialog.component';
import { GymSlotsDialogComponent } from './gym-slots-dialog/gym-slots-dialog.component';
import { LtiFlipFitConfirmDialogComponent } from '../../common/lti-flipfit-confirm-dialog/lti-flipfit-confirm-dialog.component';

/**
 * @author: 
 * @version: 1.0
 * @Component: LtiFlipFitCustomerBookingComponent
 * @description: Component for handling customer booking flow, including viewing gyms, filtering slots, and managing booking actions.
 */
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
export class LtiFlipFitCustomerBookingComponent implements OnInit {
  customerId: number | null = null;
  searchFilters = {
    location: '',
    time: '',
    activity: ''
  };

  stockImages = [
    'https://images.unsplash.com/photo-1534438327276-14e5300c3a48?q=80&w=600&auto=format&fit=crop',
    'https://images.unsplash.com/photo-1517836357463-d25dfeac3438?q=80&w=600&auto=format&fit=crop',
    'https://images.unsplash.com/photo-1571019614242-c5c5dee9f50b?q=80&w=600&auto=format&fit=crop', 
    'https://images.unsplash.com/photo-1581009146145-b5ef050c2e1e?q=80&w=600&auto=format&fit=crop',
    'https://images.unsplash.com/photo-1599058945522-28d584b6f0ff?q=80&w=600&auto=format&fit=crop',
    'https://images.unsplash.com/photo-1576678927484-cc907957088c?q=80&w=600&auto=format&fit=crop',
    'https://images.unsplash.com/photo-1574680096141-1cddd32e04ca?q=80&w=600&auto=format&fit=crop'
  ];

  gyms: any[] = [];
  allGyms: any[] = [];
  allSlots: any[] = [];
  isLoading = false;

  constructor(
    private dialog: MatDialog,
    private customerService: CustomerService,
    private snackBar: MatSnackBar
  ) {}

  /**
   * @methodname: ngOnInit
   * @description: Lifecycle hook that initializes the component. Loads user data and initial gym data.
   */
  ngOnInit() {
    const userStr = localStorage.getItem('user');
    if (userStr) {
      const user = JSON.parse(userStr);
      this.customerId = user.customerId;
    }
    this.loadData();
  }

  /**
   * @methodname: loadData
   * @description: Fetches all gyms and slots from the backend.
   */
  loadData() {
    this.isLoading = true;
    this.customerService.viewAllGyms().subscribe({
      next: (gymsData) => {
        this.allGyms = gymsData;
        this.customerService.getAllSlots().subscribe({
          next: (slotsData) => {
            this.allSlots = slotsData;
            this.filterData(); 
            this.isLoading = false;
          },
          error: (err) => {
            console.error('Error loading slots', err);
            this.isLoading = false;
          }
        });
      },
      error: (err) => {
        console.error('Error loading gyms', err);
        this.isLoading = false;
      }
    });
  }

  /**
   * @methodname: onSearch
   * @description: Triggered when search filters are applied.
   */
  onSearch() {
    console.log('Searching with filters:', this.searchFilters);
    this.filterData();
  }

  /**
   * @methodname: filterData
   * @description: Filters the loaded slots based on search criteria and groups them by gym.
   */
  filterData() {
    // Debugging logs
    console.log('All Gyms:', this.allGyms);
    console.log('All Slots:', this.allSlots);

    const gymMap = new Map(this.allGyms.map(gym => [String(gym.centerId), gym])); // Normalize to string for map keys

    const filteredSlots = this.allSlots.filter(slot => {
      // Robust get: try direct ID or stringified ID
      // Use slot.center directly
      let gym = slot.center;
      
      if (!gym) {
          console.warn(`Gym not found for slot centerId: ${slot.centerId}`, slot);
          return false;
      }

      // Filter by Location
      if (this.searchFilters.location && !gym.city.toLowerCase().includes(this.searchFilters.location.toLowerCase())) {
        return false;
      }

      // Filter by Activity
      if (this.searchFilters.activity && (!slot.activity || !slot.activity.toLowerCase().includes(this.searchFilters.activity.toLowerCase()))) {
        return false;
      }


      // Filter by Time
      if (this.searchFilters.time && !slot.startTime.includes(this.searchFilters.time)) {
        return false;
      }
      
      return true;
    });

    // Group slots by Gym
    const gymSlotsMap = new Map<number, any>();

    filteredSlots.forEach(slot => {
        const gym = slot.center;
        if (!gymSlotsMap.has(gym.centerId)) {
            // Assign a random or round-robin image based on centerId
            const imageIndex = gym.centerId % this.stockImages.length;
            
            gymSlotsMap.set(gym.centerId, {
                id: gym.centerId,
                name: gym.centerName,
                location: gym.city,
                image: this.stockImages[imageIndex],
                tags: [gym.city],
                slots: []
            });
        }
        
        const gymEntry = gymSlotsMap.get(gym.centerId);
        gymEntry.slots.push({
            slotId: slot.slotId,
            activity: slot.activity,
            startTime: slot.startTime,
            endTime: slot.endTime,
            price: slot.price,
            date: slot.date,
            availableSeats: slot.availableSeats
        });
        
        // Add activity to tags if not present
        if(slot.activity && !gymEntry.tags.includes(slot.activity)) {
            gymEntry.tags.push(slot.activity);
        }
    });

    this.gyms = Array.from(gymSlotsMap.values());
  }

  /**
   * @methodname: openGymSlotsDialog
   * @description: Opens a dialog showing detailed slots for a specific gym.
   * @param: gym - The gym object containing slots.
   */
  openGymSlotsDialog(gym: any) {
    this.dialog.open(GymSlotsDialogComponent, {
      width: '600px',
      data: {
        gym: gym,
        slots: gym.slots
      }
    });
  }

  /**
   * @methodname: onJoinWaitlist
   * @description: Handles the action of joining a waitlist for a full slot.
   * @param: gym - The gym object involved.
   */
  onJoinWaitlist(gym: any) {
    if (!this.customerId) {
        this.snackBar.open('Please login to join waitlist', 'Close', { duration: 3000 });
        return;
    }
    const customerId = this.customerId;
    
    const dialogRef = this.dialog.open(LtiFlipFitConfirmDialogComponent, {
      width: '400px',
      data: {
        title: 'Join Waitlist',
        message: `Do you want to join the waitlist for ${gym.name || 'this gym'}?` // Fixed message access
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.customerService.joinWaitlist(customerId, gym.slotId).subscribe({
          next: (res) => {
            console.log('Joined waitlist:', res);
            this.snackBar.open('Successfully joined the waitlist!', 'Close', { duration: 3000 });
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
   * @methodname: bookSlot
   * @description: Handles the booking of a slot.
   * @param: slot - The slot to book.
   */
  bookSlot(slot: any) {
    if (!this.customerId) {
         this.snackBar.open('Please login to book a slot', 'Close', { duration: 3000 });
         return;
    }
    const bookingRequest = {
        // Fix Payload structure here as well for consistency
        customer: { customerId: this.customerId },
        slot: { slotId: slot.slotId },
        center: { centerId: slot.center ? slot.center.centerId : slot.centerId },
        bookingDate: new Date().toISOString().split('T')[0]
    };

    this.customerService.bookSlot(bookingRequest).subscribe({
        next: (response) => {
            console.log('Booking successful', response);
            this.snackBar.open('Booking Successful!', 'Close', { duration: 3000 });
        },
        error: (err) => {
            console.error('Booking failed', err);
            const errorMsg = this.getErrorMessage(err);
            this.snackBar.open(errorMsg, 'Close', { duration: 3000 });
        }
    });
  }

  /**
   * @methodname: getErrorMessage
   * @description: Helper method to extract error message from API response.
   * @param: err - The error object.
   * @return: Extracted error message string.
   */
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
