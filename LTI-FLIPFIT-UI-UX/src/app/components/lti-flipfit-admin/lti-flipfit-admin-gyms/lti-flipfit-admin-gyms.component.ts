import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatChipsModule } from '@angular/material/chips';
import { AdminService } from '../../../services/admin-service/admin.service';

@Component({
  selector: 'app-lti-flipfit-admin-gyms',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
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
  gyms = new MatTableDataSource<any>([]);
  searchTerm: string = '';
  filterStatus: string = 'all';

  constructor(
    private router: Router,
    private adminService: AdminService
  ) {}

  ngOnInit() {
    this.setupFilterPredicate();
    this.loadGyms();
  }

  setupFilterPredicate() {
    this.gyms.filterPredicate = (data: any, filter: string): boolean => {
      const searchStr = this.searchTerm.toLowerCase();
      const statusFilter = this.filterStatus.toLowerCase();
      
      const matchesSearch = data.name.toLowerCase().includes(searchStr);
      const matchesStatus = statusFilter === 'all' || data.rawStatus === statusFilter;

      return matchesSearch && matchesStatus;
    };
  }

  applyFilter() {
    // Trigger filter update. The value passed to filter doesn't matter 
    // because we use custom predicate that reads from class properties
    this.gyms.filter = 'trigger'; 
  }

  loadGyms() {
    this.adminService.getAllCenters().subscribe(centers => {
      this.gyms.data = centers.map(center => ({
        id: center.centerId,
        name: center.centerName,
        owner: center.owner?.user?.fullName || 'Unknown',
        location: center.city,
        status: center.isApproved ? 'Active' : 'Pending',
        rawStatus: center.isApproved ? 'active' : 'pending' // For styling and filtering
      }));
      this.applyFilter(); // Re-apply filter in case inputs are set
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
