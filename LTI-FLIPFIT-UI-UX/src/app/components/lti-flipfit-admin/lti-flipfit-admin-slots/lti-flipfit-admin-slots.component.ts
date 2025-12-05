import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AdminService } from '../../../services/admin-service/admin.service';
import { GymCenter } from '../../../models/gym-center/gym-center.model';

interface GymWithSlotStats extends GymCenter {
  totalSlots: number;
  approvedSlots: number;
  pendingSlots: number;
}

@Component({
  selector: 'app-admin-slots',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './lti-flipfit-admin-slots.component.html',
  styleUrl: './lti-flipfit-admin-slots.component.scss'
})
export class LtiFlipFitAdminSlotsComponent implements OnInit {
  gyms: GymWithSlotStats[] = [];
  filteredGyms: GymWithSlotStats[] = [];
  searchTerm: string = '';

  constructor(
    private adminService: AdminService,
    private router: Router
  ) {}

  ngOnInit() {
    this.loadGyms();
  }

  loadGyms() {
    this.adminService.getAllCenters().subscribe(centers => {
      this.gyms = centers.map(center => ({
        ...center,
        totalSlots: 0,
        approvedSlots: 0,
        pendingSlots: 0
      }));
      
      this.filteredGyms = [...this.gyms];

      // Fetch slots for each gym to calculate stats
      this.gyms.forEach(gym => {
        if (gym.centerId) {
          this.adminService.getSlotsByCenterId(gym.centerId).subscribe(slots => {
            gym.totalSlots = slots.length;
            gym.approvedSlots = slots.filter(s => s.isApproved).length;
            gym.pendingSlots = slots.filter(s => !s.isApproved).length;
          });
        }
      });
    });
  }

  filterGyms() {
    if (!this.searchTerm) {
      this.filteredGyms = [...this.gyms];
    } else {
      const term = this.searchTerm.toLowerCase();
      this.filteredGyms = this.gyms.filter(gym => 
        gym.centerName.toLowerCase().includes(term)
      );
    }
  }

  viewSlots(centerId: number | undefined) {
    if (centerId) {
      this.router.navigate(['/admin-dashboard/slots', centerId]);
    }
  }
}
