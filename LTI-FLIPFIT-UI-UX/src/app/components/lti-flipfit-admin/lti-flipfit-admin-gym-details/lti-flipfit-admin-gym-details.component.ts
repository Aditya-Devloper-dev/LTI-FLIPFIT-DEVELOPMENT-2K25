import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Location } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatTableModule } from '@angular/material/table';
import { MatChipsModule } from '@angular/material/chips';
import { AdminService } from '../../../services/admin-service/admin.service';
import { OwnerService } from '../../../services/owner-service/owner.service';
import { GymCenter } from '../../../models/gym-center/gym-center.model';

/**
 * @author: 
 * @version: 1.0
 * @Component: LtiFlipFitAdminGymDetailsComponent
 * @description: Component for displaying details of a specific gym center in the admin panel, including slots and owner stats.
 */
@Component({
  selector: 'app-lti-flipfit-admin-gym-details',
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatTableModule,
    MatChipsModule
  ],
  templateUrl: './lti-flipfit-admin-gym-details.component.html',
  styleUrl: './lti-flipfit-admin-gym-details.component.scss'
})
export class LtiFlipFitAdminGymDetailsComponent implements OnInit {
  gym: GymCenter | undefined;
  slots: any[] = [];
  slotColumns: string[] = ['time', 'capacity', 'price', 'status', 'actions'];
  totalGyms: number = 0;
  totalBookings: number = 0;
  activeGyms: number = 0;
  totalRevenue: number = 0;

  constructor(
    private location: Location,
    private route: ActivatedRoute,
    private adminService: AdminService,
    private ownerService: OwnerService
  ) {}

  /**
   * @methodname: ngOnInit
   * @description: Lifecycle hook. Reads the gym ID from route params and loads details.
   */
  ngOnInit() {
    this.route.params.subscribe(params => {
      const id = params['id'];
      if (id) {
        this.loadGymDetails(id);
        this.loadSlots(id);
      }
    });
  }

  /**
   * @methodname: loadGymDetails
   * @description: Fetches full details of a gym center by ID.
   * @param: id - Center ID.
   */
  loadGymDetails(id: number) {
    this.adminService.getCenterById(id).subscribe({
      next: (data) => {
        this.gym = data;
        if (this.gym?.owner?.ownerId) {
          this.loadOwnerStats(this.gym.owner.ownerId);
        }
      },
      error: (err) => {
        console.error('Error fetching gym details', err);
      }
    });
  }

  /**
   * @methodname: loadSlots
   * @description: Fetches all slots associated with the gym center.
   * @param: centerId - The center ID.
   */
  loadSlots(centerId: number) {
    this.adminService.getSlotsByCenterId(centerId).subscribe({
      next: (data) => {
        this.slots = data;
      },
      error: (err) => console.error('Error fetching slots', err)
    });
  }

  /**
   * @methodname: loadOwnerStats
   * @description: Loads statistics for the gym owner (total gyms, bookings, revenue).
   * @param: ownerId - The owner's ID.
   */
  loadOwnerStats(ownerId: number) {
    this.ownerService.getGymsByOwnerId(ownerId).subscribe(gyms => {
      this.totalGyms = gyms.length;
      this.activeGyms = gyms.filter(g => g.isApproved).length;
    });

    this.ownerService.getAllBookingsByOwner(ownerId).subscribe(bookings => {
      this.totalBookings = bookings.length;
      this.totalRevenue = bookings.reduce((sum, booking) => sum + (booking.slot?.price || 0), 0);
    });
  }

  /**
   * @methodname: approveGym
   * @description: Approves the current gym center.
   */
  approveGym() {
    if (this.gym?.centerId) {
      this.adminService.approveCenter(this.gym.centerId).subscribe({
        next: () => {
          this.loadGymDetails(this.gym!.centerId!);
        },
        error: (err) => console.error('Error approving gym', err)
      });
    }
  }

  /**
   * @methodname: rejectGym
   * @description: Rejects/Deletes the current gym center.
   */
  rejectGym() {
    if (this.gym?.centerId) {
      this.adminService.deleteCenter(this.gym.centerId).subscribe({
        next: () => {
          this.goBack();
        },
        error: (err) => console.error('Error rejecting gym', err)
      });
    }
  }

  /**
   * @methodname: approveSlot
   * @description: Approves a specific slot.
   * @param: slotId - The slot ID.
   */
  approveSlot(slotId: number) {
    this.adminService.approveSlot(slotId).subscribe({
      next: () => {
        if (this.gym?.centerId) {
          this.loadSlots(this.gym.centerId);
        }
      },
      error: (err) => console.error('Error approving slot', err)
    });
  }

  /**
   * @methodname: rejectSlot
   * @description: Rejects a specific slot.
   * @param: slotId - The slot ID.
   */
  rejectSlot(slotId: number) {
    this.adminService.rejectSlot(slotId).subscribe({
      next: () => {
        if (this.gym?.centerId) {
          this.loadSlots(this.gym.centerId);
        }
      },
      error: (err) => console.error('Error rejecting slot', err)
    });
  }

  /**
   * @methodname: goBack
   * @description: Navigates back to the previous location.
   */
  goBack() {
    this.location.back();
  }
}
