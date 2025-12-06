import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatChipsModule } from '@angular/material/chips';
import { MatCardModule } from '@angular/material/card';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';

import { AdminService } from '../../../services/admin-service/admin.service';
import { GymSlot } from '../../../models/gym-slot/gym-slot.model';
import { LtiFlipFitConfirmDialogComponent } from '../../common/lti-flipfit-confirm-dialog/lti-flipfit-confirm-dialog.component';

/**
 * @author: 
 * @version: 1.0
 * @Component: LtiFlipFitAdminSlotDetailsComponent
 * @description: Component for viewing and managing slots for a specific gym center.
 */
@Component({
  selector: 'app-admin-slot-details',
  standalone: true,
  imports: [
    CommonModule,
    MatTableModule,
    MatButtonModule,
    MatIconModule,
    MatChipsModule,
    MatCardModule,
    MatTooltipModule,
    MatDialogModule
  ],
  templateUrl: './lti-flipfit-admin-slot-details.component.html',
  styleUrl: './lti-flipfit-admin-slot-details.component.scss'
})
export class LtiFlipFitAdminSlotDetailsComponent implements OnInit {
  displayedColumns: string[] = ['capacity', 'time', 'activity', 'price', 'status', 'actions'];
  slots: GymSlot[] = [];
  centerId: number | null = null;

  constructor(
    private adminService: AdminService,
    private route: ActivatedRoute,
    private router: Router,
    private dialog: MatDialog
  ) {}

  /**
   * @methodname: ngOnInit
   * @description: Lifecycle hook. Gets center ID from route and loads slots.
   */
  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        this.centerId = +id;
        this.loadSlots();
      }
    });
  }

  /**
   * @methodname: loadSlots
   * @description: Fetches all slots for the current center ID.
   */
  loadSlots() {
    if (this.centerId) {
      this.adminService.getSlotsByCenterId(this.centerId).subscribe(slots => {
        this.slots = slots;
      });
    }
  }

  /**
   * @methodname: approveSlot
   * @description: Approves a pending slot.
   * @param: slotId - The slot ID.
   */
  approveSlot(slotId: number | undefined) {
    if (slotId) {
      this.adminService.approveSlot(slotId).subscribe(() => {
        this.loadSlots();
      }, error => {
        console.error('Error approving slot', error);
        this.loadSlots();
      });
    }
  }

  /**
   * @methodname: deleteSlot
   * @description: Deletes a slot after confirmation.
   * @param: slotId - The slot ID.
   */
  deleteSlot(slotId: number | undefined) {
    if (slotId) {
      const dialogRef = this.dialog.open(LtiFlipFitConfirmDialogComponent, {
        width: '400px',
        data: {
          title: 'Delete Slot',
          message: 'Are you sure you want to delete this slot? This action cannot be undone.'
        }
      });

      dialogRef.afterClosed().subscribe(result => {
        if (result) {
          this.adminService.deleteSlot(slotId).subscribe(() => {
            this.loadSlots();
          }, error => {
            console.error('Error deleting slot', error);
          });
        }
      });
    }
  }

  /**
   * @methodname: goBack
   * @description: Navigates back to the main slots management page.
   */
  goBack() {
    this.router.navigate(['/admin-dashboard/slots']);
  }
}
