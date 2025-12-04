import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { OwnerService } from '../../../services/owner.service';
import { GymCenter } from '../../../models/gym-center.model';

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
export class LtiFlipFitOwnerAddGymComponent {
  gymCenter: GymCenter = {
    centerName: '',
    city: '',
    contactNumber: ''
  };

  constructor(
    private router: Router,
    private ownerService: OwnerService,
    private snackBar: MatSnackBar
  ) {}

  onCancel() {
    this.router.navigate(['/gym-owner-dashboard/my-gyms']);
  }

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
      error: (error) => {
        console.error('Error saving gym', error);
        let errorMessage = 'Error adding gym. Please try again.';
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
    });
  }
}
