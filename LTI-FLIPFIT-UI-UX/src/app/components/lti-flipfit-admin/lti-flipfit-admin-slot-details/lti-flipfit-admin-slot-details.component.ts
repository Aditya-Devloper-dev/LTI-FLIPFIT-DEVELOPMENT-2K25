import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { AdminService } from '../../../services/admin-service/admin.service';
import { GymSlot } from '../../../models/gym-slot/gym-slot.model';

@Component({
  selector: 'app-admin-slot-details',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './lti-flipfit-admin-slot-details.component.html',
  styleUrl: './lti-flipfit-admin-slot-details.component.scss'
})
export class LtiFlipFitAdminSlotDetailsComponent implements OnInit {
  slots: GymSlot[] = [];
  centerId: number | null = null;

  constructor(
    private adminService: AdminService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        this.centerId = +id;
        this.loadSlots();
      }
    });
  }

  loadSlots() {
    if (this.centerId) {
      this.adminService.getSlotsByCenterId(this.centerId).subscribe(slots => {
        this.slots = slots;
      });
    }
  }

  approveSlot(slotId: number | undefined) {
    if (slotId) {
      this.adminService.approveSlot(slotId).subscribe(() => {
        // Refresh list
        this.loadSlots();
      }, error => {
        console.error('Error approving slot', error);
        // Optimistic refresh anyway or show notification?
        // Basic handling for now
        this.loadSlots();
      });
    }
  }

  rejectSlot(slotId: number | undefined) {
    if (slotId) {
      this.adminService.rejectSlot(slotId).subscribe(() => {
        this.loadSlots();
      }, error => {
        console.error('Error rejecting slot', error);
        this.loadSlots();
      });
    }
  }

  goBack() {
    this.router.navigate(['/admin-dashboard/slots']);
  }
}
