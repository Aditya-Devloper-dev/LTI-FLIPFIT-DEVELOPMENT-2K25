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
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { AdminService } from '../../../services/admin-service/admin.service';
import { LtiFlipFitConfirmDialogComponent } from '../../common/lti-flipfit-confirm-dialog/lti-flipfit-confirm-dialog.component';

/**
 * @author: 
 * @version: 1.0
 * @Component: LtiFlipFitAdminGymsComponent
 * @description: Component for managing and viewing all gym centers in the system.
 */
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
    MatChipsModule,
    MatTooltipModule,
    MatDialogModule
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
    private adminService: AdminService,
    private dialog: MatDialog
  ) {}

  /**
   * @methodname: ngOnInit
   * @description: Lifecycle hook. Sets up filtering predicate and loads gym centers.
   */
  ngOnInit() {
    this.setupFilterPredicate();
    this.loadGyms();
  }

  /**
   * @methodname: setupFilterPredicate
   * @description: Configures proper filtering logic for the data table (search + status filter).
   */
  setupFilterPredicate() {
    this.gyms.filterPredicate = (data: any, filter: string): boolean => {
      const searchStr = this.searchTerm.toLowerCase();
      const statusFilter = this.filterStatus.toLowerCase();
      
      const matchesSearch = data.name.toLowerCase().includes(searchStr);
      const matchesStatus = statusFilter === 'all' || data.rawStatus === statusFilter;

      return matchesSearch && matchesStatus;
    };
  }

  /**
   * @methodname: applyFilter
   * @description: Triggers the data table filter update.
   */
  applyFilter() {
    // Trigger filter update. The value passed to filter doesn't matter 
    // because we use custom predicate that reads from class properties
    this.gyms.filter = 'trigger'; 
  }

  /**
   * @methodname: loadGyms
   * @description: Fetches all gym centers from the backend and populates the table.
   */
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

  /**
   * @methodname: viewGym
   * @description: Navigates to the detailed view of a specific gym center.
   * @param: id - The gym center ID.
   */
  viewGym(id: string) {
    this.router.navigate(['/admin-dashboard/gyms', id]);
  }

  /**
   * @methodname: deleteGym
   * @description: Deletes a gym center after confirmation.
   * @param: id - The gym center ID.
   */
  deleteGym(id: number) {
    const dialogRef = this.dialog.open(LtiFlipFitConfirmDialogComponent, {
      width: '400px',
      data: {
        title: 'Delete Gym Center',
        message: 'Are you sure you want to delete this gym center? This action cannot be undone.'
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.adminService.deleteCenter(id).subscribe(() => {
          this.loadGyms();
        });
      }
    });
  }

}
