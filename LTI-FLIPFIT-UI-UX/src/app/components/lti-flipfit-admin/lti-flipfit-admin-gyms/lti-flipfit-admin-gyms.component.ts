import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatChipsModule } from '@angular/material/chips';
import { AdminService } from '../../../services/admin-service/admin.service';
import { GymCenter } from '../../../models/gym-center/gym-center.model';

@Component({
  selector: 'app-lti-flipfit-admin-gyms',
  standalone: true,
  imports: [
    CommonModule,
    MatTableModule,
    MatButtonModule,
    MatIconModule,
    MatInputModule,
    MatSelectModule,
    MatFormFieldModule,
    MatChipsModule
  ],
  templateUrl: './lti-flipfit-admin-gyms.component.html',
  styleUrl: './lti-flipfit-admin-gyms.component.scss'
})
export class LtiFlipFitAdminGymsComponent implements OnInit {
  displayedColumns: string[] = ['name', 'owner', 'location', 'status', 'actions'];
  gyms: any[] = [];

  constructor(
    private router: Router,
    private adminService: AdminService
  ) {}

  ngOnInit() {
    this.loadGyms();
  }

  loadGyms() {
    this.adminService.getAllCenters().subscribe(centers => {
      this.gyms = centers.map(center => ({
        id: center.centerId,
        name: center.centerName,
        owner: center.owner?.user?.fullName || 'Unknown', // Access nested owner name
        location: center.city,
        status: center.isApproved ? 'Active' : 'Pending', // Map status
        rawStatus: center.isApproved ? 'active' : 'pending' // For styling
      }));
    });
  }

  viewGym(id: string) {
    this.router.navigate(['/admin-dashboard/gyms', id]);
  }

  approveGym(id: number) {
    this.adminService.approveCenter(id).subscribe(() => {
      this.loadGyms(); // Reload to update status
    });
  }

  rejectGym(id: number) {
    if (confirm('Are you sure you want to reject (delete) this gym?')) {
      this.adminService.deleteCenter(id).subscribe(() => {
        this.loadGyms(); // Reload to remove from list
      });
    }
  }
}
