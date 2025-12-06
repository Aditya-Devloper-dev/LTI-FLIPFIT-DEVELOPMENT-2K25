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
import { CustomerService } from '../../../services/customer-service/customer.service';
import { BookingDialogComponent } from '../../common/booking-dialog/booking-dialog.component';
import { GymSlotsDialogComponent } from './gym-slots-dialog/gym-slots-dialog.component';

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
    private customerService: CustomerService
  ) {}

  ngOnInit() {
    this.loadData();
  }

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

  onSearch() {
    console.log('Searching with filters:', this.searchFilters);
    this.filterData();
  }

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

  openGymSlotsDialog(gym: any) {
    this.dialog.open(GymSlotsDialogComponent, {
      width: '600px',
      data: {
        gym: gym,
        slots: gym.slots
      }
    });
  }

  onJoinWaitlist(gym: any) {
     // Assuming we have customerId (hardcoded 112 as per existing code)
    const customerId = 112; 
    
    if(confirm(`Do you want to join the waitlist for ${gym.name} - ${gym.activity}?`)) {
      this.customerService.joinWaitlist(customerId, gym.slotId).subscribe({
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



  bookSlot(slot: any) {
    const bookingRequest = {
        slotId: slot.slotId,
        customerId: 112, // TODO: Get from AuthService/UserContext
        bookingDate: new Date().toISOString().split('T')[0]
    };

    this.customerService.bookSlot(bookingRequest).subscribe({
        next: (response) => {
            console.log('Booking successful', response);
            alert('Booking Successful!');
        },
        error: (err) => {
            console.error('Booking failed', err);
            alert('Booking Failed: ' + (err.error?.message || 'Server error'));
        }
    });
  }
}
