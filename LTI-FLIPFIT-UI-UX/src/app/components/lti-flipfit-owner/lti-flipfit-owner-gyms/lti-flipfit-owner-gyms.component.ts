import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatChipsModule } from '@angular/material/chips';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { OwnerService } from '../../../services/owner-service/owner.service';
import { GymCenter } from '../../../models/gym-center/gym-center.model';

/*
* @author: 
* @version: 1.0
* @description: This component is used to display the list of gyms.
*/
/**
 * @author: 
 * @version: 1.0
 * @description: This component is used to display the list of gyms.
 */
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

  /**
   * @method ngOnInit
   * @description Initializes the component and loads the list of gyms.
   */
  ngOnInit() {
    this.loadGyms();
  }

  /**
   * @method loadGyms
   * @description Loads the gyms owned by the current user.
   */
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

  /**
   * @method getStatus
   * @param gym - The gym center object.
   * @description Returns the display status of the gym.
   * @returns The status string.
   */
  getStatus(gym: GymCenter): string {
    if (gym.isApproved) {
      return gym.isActive ? 'Active' : 'Inactive';
    }
    return 'Pending Approval';
  }

  /**
   * @method viewDetails
   * @param id - The ID of the gym.
   * @description Navigates to the gym details page.
   */
  viewDetails(id: number) {
    console.log('View details', id);
    this.router.navigate(['/gym-owner-dashboard/gym-details', id]);
  }

  /**
   * @method toggleStatus
   * @param gym - The gym center object.
   * @description Toggles the active status of the gym.
   */
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

  /**
   * @method editGym
   * @param id - The ID of the gym to edit.
   * @description Navigates to the edit gym page.
   */
  editGym(id: number) {
    console.log('Edit gym', id);
    this.router.navigate(['/gym-owner-dashboard/edit-gym', id]);
  }

  /**
   * @method addNewGym
   * @description Navigates to the add gym page.
   */
  addNewGym() {
    this.router.navigate(['/gym-owner-dashboard/add-gym']);
  }
}
