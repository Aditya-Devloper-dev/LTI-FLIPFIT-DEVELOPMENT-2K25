import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { MatTableModule, MatTableDataSource } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';

import { AdminService } from '../../../services/admin-service/admin.service';
import { GymCenter } from '../../../models/gym-center/gym-center.model';

interface GymWithSlotStats extends GymCenter {
  totalSlots: number;
  approvedSlots: number;
  pendingSlots: number;
}

/**
 * @author: 
 * @version: 1.0
 * @Component: LtiFlipFitAdminSlotsComponent
 * @description: Component for listing gym centers with their slot statistics for admin approval/management.
 */
@Component({
  selector: 'app-admin-slots',
  standalone: true,
  imports: [
    CommonModule, 
    FormsModule,
    MatTableModule,
    MatButtonModule,
    MatIconModule,
    MatInputModule,
    MatFormFieldModule
  ],
  templateUrl: './lti-flipfit-admin-slots.component.html',
  styleUrl: './lti-flipfit-admin-slots.component.scss'
})
export class LtiFlipFitAdminSlotsComponent implements OnInit {
  displayedColumns: string[] = ['name', 'total', 'approved', 'pending', 'actions'];
  gyms: GymWithSlotStats[] = [];
  filteredGyms = new MatTableDataSource<GymWithSlotStats>([]);
  searchTerm: string = '';

  constructor(
    private adminService: AdminService,
    private router: Router
  ) {}

  /**
   * @methodname: ngOnInit
   * @description: Lifecycle hook. Triggers loading of gym and slot data.
   */
  ngOnInit() {
    this.loadGyms();
  }

  /**
   * @methodname: loadGyms
   * @description: Fetches all centers and allows calculation of total, approved, and pending slots for each.
   */
  loadGyms() {
    this.adminService.getAllCenters().subscribe(centers => {
      this.gyms = centers.map(center => ({
        ...center,
        totalSlots: 0,
        approvedSlots: 0,
        pendingSlots: 0
      }));
      
      this.filteredGyms.data = this.gyms;

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

  /**
   * @methodname: applyFilter
   * @description: Filters the gym list based on the search term.
   */
  applyFilter() {
    this.filteredGyms.filter = this.searchTerm.trim().toLowerCase();
  }

  /**
   * @methodname: viewSlots
   * @description: Navigates to the detailed slot management view for a specific center.
   * @param: centerId - The center ID.
   */
  viewSlots(centerId: number | undefined) {
    if (centerId) {
      this.router.navigate(['/admin-dashboard/slots', centerId]);
    }
  }
}
