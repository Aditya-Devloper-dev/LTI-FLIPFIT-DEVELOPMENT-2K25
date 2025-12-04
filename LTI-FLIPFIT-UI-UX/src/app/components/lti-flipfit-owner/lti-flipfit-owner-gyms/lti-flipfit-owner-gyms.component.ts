import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatChipsModule } from '@angular/material/chips';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { OwnerService } from '../../../services/owner.service';
import { GymCenter } from '../../../models/gym-center.model';

@Component({
  selector: 'app-lti-flipfit-owner-gyms',
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatChipsModule,
    MatSnackBarModule
  ],
  templateUrl: './lti-flipfit-owner-gyms.component.html',
  styleUrl: './lti-flipfit-owner-gyms.component.scss'
})
export class LtiFlipFitOwnerGymsComponent implements OnInit {
  gyms: GymCenter[] = [];

  constructor(
    private router: Router,
    private ownerService: OwnerService,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit() {
    this.loadGyms();
  }

  loadGyms() {
    const userString = localStorage.getItem('user');
    const user = userString ? JSON.parse(userString) : null;
    const ownerId = user?.ownerId;

    if (ownerId) {
      this.ownerService.getGymsByOwnerId(ownerId).subscribe({
        next: (data) => {
          this.gyms = data;
          console.log('Gyms loaded', this.gyms);
        },
        error: (error) => {
          console.error('Error loading gyms', error);
        }
      });
    } else {
      console.error('Owner ID not found');
    }
  }

  getStatus(gym: GymCenter): string {
    if (gym.isApproved) {
      return gym.isActive ? 'Active' : 'Inactive';
    }
    return 'Pending Approval';
  }

  viewDetails(id: number) {
    console.log('View details', id);
    this.router.navigate(['/gym-owner-dashboard/gym-details', id]);
  }

  toggleStatus(gym: GymCenter) {
    const userString = localStorage.getItem('user');
    const user = userString ? JSON.parse(userString) : null;
    const ownerId = user?.ownerId;

    if (ownerId && gym.centerId) {
        this.ownerService.toggleCenterActive(gym.centerId, ownerId).subscribe({
            next: (response) => {
                console.log('Status toggled', response);
                this.snackBar.open(response, 'Close', {
                    duration: 3000,
                    horizontalPosition: 'center',
                    verticalPosition: 'top'
                });
                this.loadGyms(); // Reload to get updated status
            },
            error: (error) => {
                console.error('Error toggling status', error);
                this.snackBar.open('Error updating status', 'Close', {
                    duration: 3000,
                    horizontalPosition: 'center',
                    verticalPosition: 'top',
                    panelClass: ['error-snackbar']
                });
            }
        });
    }
  }

  editGym(id: number) {
    console.log('Edit gym', id);
  }

  addNewGym() {
    this.router.navigate(['/gym-owner-dashboard/add-gym']);
  }
}
