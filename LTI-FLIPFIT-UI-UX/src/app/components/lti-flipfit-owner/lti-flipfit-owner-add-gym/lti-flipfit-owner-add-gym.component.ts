import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, ActivatedRoute } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { GymCenter } from '../../../models/gym-center/gym-center.model';
import { OwnerService } from '../../../services/owner-service/owner.service';

/*
* @author: 
* @version: 1.0
* @description: This component is used to add or edit a gym center.
*/
@Component({
  selector: 'app-lti-flipfit-owner-add-gym',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatCardModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    MatSnackBarModule
  ],
  templateUrl: './lti-flipfit-owner-add-gym.component.html',
  styleUrl: './lti-flipfit-owner-add-gym.component.scss'
})
export class LtiFlipFitOwnerAddGymComponent implements OnInit {
  gymCenter: GymCenter = {
    centerName: '',
    city: '',
    contactNumber: ''
  };
  isEditMode = false;
  gymId: number | null = null;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private ownerService: OwnerService,
    private snackBar: MatSnackBar
  ) {}

  /**
   * @method ngOnInit
   * @description Initializes the component and checks for edit mode.
   */
  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        this.isEditMode = true;
        this.gymId = +id;
        this.loadGymDetails(this.gymId);
      }
    });
  }

  /**
   * @method loadGymDetails
   * @description Loads the details of a gym for editing.
   * @param id - The ID of the gym to load.
   */
  loadGymDetails(id: number) {
    this.ownerService.getGymDetails(id).subscribe({
      next: (data) => {
        this.gymCenter = data;
      },
      error: (err) => {
        console.error('Failed to load gym details', err);
        this.snackBar.open('Failed to load gym details', 'Close', { duration: 3000 });
      }
    });
  }

  /*
  *@method onCancel
  *@description: This method is used to navigate to the gym owner dashboard.
  */
  onCancel() {
    this.router.navigate(['/gym-owner-dashboard/my-gyms']);
  }

  /*
  *@method onSave
  *@description: This method is used to save or update the gym center.
  */

  onSave() {
    const userString = localStorage.getItem('user');
    const user = userString ? JSON.parse(userString) : null;
    const ownerId = user?.ownerId;

    if (!ownerId) {
      console.error('Owner ID not found');
      this.snackBar.open('Owner ID not found. Please login again.', 'Close', {
        duration: 3000,
        horizontalPosition: 'center',
        verticalPosition: 'top',
        panelClass: ['error-snackbar']
      });
      return;
    }
    
    if (this.isEditMode && this.gymId) {
      // Update existing gym
      this.ownerService.updateCenter(this.gymCenter, ownerId).subscribe({
        next: (response) => {
          console.log('Gym updated', response);
          this.snackBar.open('Gym updated successfully!', 'Close', {
            duration: 3000,
            horizontalPosition: 'center',
            verticalPosition: 'top'
          });
          this.router.navigate(['/gym-owner-dashboard/my-gyms']);
        },
        error: (error) => this.handleError(error)
      });
    } else {
      // Add new gym
      this.ownerService.addCenter(this.gymCenter, ownerId).subscribe({
        next: (response) => {
          console.log('Gym saved', response);
          this.snackBar.open('Gym added successfully!', 'Close', {
            duration: 3000,
            horizontalPosition: 'center',
            verticalPosition: 'top'
          });
          this.router.navigate(['/gym-owner-dashboard/my-gyms']);
        },
        error: (error) => this.handleError(error)
      });
    }
  }

  /**
   * @method handleError
   * @param error - The error object.
   * @description Handles errors that occur during gym saving.
   */

  handleError(error: any) {
    console.error('Error saving gym', error);
    let errorMessage = 'Error saving gym. Please try again.';
    if (error.error) {
         if (typeof error.error === 'string') {
            try {
                const parsedError = JSON.parse(error.error);
                if (parsedError.message) {
                    errorMessage = parsedError.message;
                } else {
                    errorMessage = error.error;
                }
            } catch (e) {
                errorMessage = error.error;
            }
        } else if (error.error.message) {
            errorMessage = error.error.message;
        }
    } else if (error.message) {
        errorMessage = error.message;
    }

    this.snackBar.open(errorMessage, 'Close', {
      duration: 5000,
      horizontalPosition: 'center',
      verticalPosition: 'top',
      panelClass: ['error-snackbar']
    });
  }
}
