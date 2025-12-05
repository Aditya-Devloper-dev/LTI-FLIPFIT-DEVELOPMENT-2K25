import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatChipsModule } from '@angular/material/chips';
import { MatDividerModule } from '@angular/material/divider';
import { GymCenter } from '../../../models/gym-center/gym-center.model';
import { OwnerService } from '../../../services/owner-service/owner.service';

/**
 * @author: 
 * @version: 1.0
 * @description: Component to display details of a specific gym center.
 */
@Component({
  selector: 'app-lti-flipfit-owner-gym-details',
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatChipsModule,
    MatDividerModule
  ],
  templateUrl: './lti-flipfit-owner-gym-details.component.html',
  styleUrl: './lti-flipfit-owner-gym-details.component.scss'
})
export class LtiFlipFitOwnerGymDetailsComponent implements OnInit {
  gym: GymCenter | null = null;
  isLoading = true;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private ownerService: OwnerService
  ) {}

  /**
   * @method ngOnInit
   * @description Initializes the component and loads gym details.
   */
  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.loadGymDetails(Number(id));
    } else {
      this.router.navigate(['/gym-owner-dashboard/my-gyms']);
    }
  }

  /**
   * @method loadGymDetails
   * @description Loads the details of the gym.
   * @param id - The ID of the gym.
   */
  loadGymDetails(id: number) {
    this.ownerService.getGymDetails(id).subscribe({
      next: (data) => {
        this.gym = data;
        this.isLoading = false;
        this.loadSlots(id);
        this.loadStats(id);
      },
      error: (error) => {
        console.error('Error loading gym details', error);
        this.isLoading = false;
      }
    });
  }

  slots: any[] = [];
  stats = {
    totalBookings: 0,
    revenue: 0,
    activeSlots: 0
  };

  /**
   * @method loadSlots
   * @description Loads the slots for the gym.
   * @param centerId - The ID of the gym center.
   */
  loadSlots(centerId: number) {
    this.ownerService.getSlotsByCenterId(centerId).subscribe({
      next: (data) => {
        this.slots = data;
        this.stats.activeSlots = data.filter(s => s.isActive).length;
      },
      error: (err) => console.error('Error loading slots', err)
    });
  }

  /**
   * @method loadStats
   * @description Loads the statistics for the gym.
   * @param centerId - The ID of the gym center.
   */
  loadStats(centerId: number) {
    this.ownerService.viewAllBookings(centerId).subscribe({
      next: (data) => {
        this.stats.totalBookings = data.length;
        this.stats.revenue = data.reduce((sum, booking) => sum + (booking.slot.price || 0), 0);
      },
      error: (err) => console.error('Error loading stats', err)
    });
  }

  /**
   * @method getStatus
   * @description Returns the display status of the gym.
   * @param gym - The gym center object.
   * @returns The status string.
   */
  getStatus(gym: GymCenter): string {
    if (gym.isApproved) {
      return gym.isActive ? 'Active' : 'Inactive';
    }
    return 'Pending Approval';
  }

  /**
   * @method goBack
   * @description Navigates back to the gym list.
   */
  goBack() {
    this.router.navigate(['/gym-owner-dashboard/my-gyms']);
  }
}
