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
  totalGyms: number = 0;
  totalBookings: number = 0;

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

  loadOwnerStats(ownerId: number) {
    this.ownerService.getGymsByOwnerId(ownerId).subscribe(gyms => {
      this.totalGyms = gyms.length;
    });

    this.ownerService.getAllBookingsByOwner(ownerId).subscribe(bookings => {
      this.totalBookings = bookings.length;
    });
  }

  goBack() {
    this.location.back();
  }
}
