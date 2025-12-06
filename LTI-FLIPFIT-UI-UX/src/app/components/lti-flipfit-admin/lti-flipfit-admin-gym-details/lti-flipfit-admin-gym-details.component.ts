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

  ngOnInit() {
    this.route.params.subscribe(params => {
      const id = params['id'];
      if (id) {
        this.loadGymDetails(id);
        this.loadSlots(id);
      }
    });
  }

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

  loadSlots(centerId: number) {
    this.adminService.getSlotsByCenterId(centerId).subscribe({
      next: (data) => {
        this.slots = data;
      },
      error: (err) => console.error('Error fetching slots', err)
    });
  }

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

  goBack() {
    this.location.back();
  }
}
