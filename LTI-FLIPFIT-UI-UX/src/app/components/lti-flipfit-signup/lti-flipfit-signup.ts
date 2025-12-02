import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, Router } from '@angular/router';
import { UserService } from '../../services/user.service';
import { UserRegistration } from '../../models/user.model';
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { FormsModule } from '@angular/forms';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { RoleType } from '../../models/role.type';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink,
    MatCardModule,
    MatInputModule,
    MatButtonModule,
    MatSelectModule,
    MatFormFieldModule,
    MatIconModule,
    FormsModule,
    MatSnackBarModule
  ],
  templateUrl: './lti-flipfit-signup.html',
  styleUrl: './lti-flipfit-signup.scss',
})
export class LtiFlipFitSignUp {
  hidePassword = true;
  hideConfirmPassword = true;
  selectedRole: string = 'CUSTOMER';
  confirmPassword = '';
  
  user: UserRegistration = {
    email: '',
    password: '',
    fullName: '',
    phoneNumber: '',
    role: 'CUSTOMER',
    businessName: '',
    gstNumber: '',
    panNumber: ''
  };

  constructor(
    private userService: UserService, 
    private router: Router,
    private snackBar: MatSnackBar
  ) {}

  onRoleChange(role: string) {
    this.selectedRole = role;
    this.user.role = role;
  }

  onSubmit() {
    console.log('Registering user:', this.user);

    if (this.user.password !== this.confirmPassword) {
      this.snackBar.open('Passwords do not match!', 'Close', {
        duration: 3000,
        horizontalPosition: 'center',
        verticalPosition: 'top',
        panelClass: ['error-snackbar']
      });
      return;
    }
    
    // Create a copy of the user object to avoid modifying the form model directly
    const userPayload = { ...this.user };

    // Remove unnecessary fields for CUSTOMER role
    if (this.user.role === RoleType.CUSTOMER) {
      delete userPayload.businessName;
      delete userPayload.gstNumber;
      delete userPayload.panNumber;
    }

    this.userService.register(userPayload).subscribe({
      next: (response) => {
        console.log('Registration successful', response);
        this.snackBar.open('Registration successful! Please login.', 'Close', {
          duration: 3000,
          horizontalPosition: 'center',
          verticalPosition: 'top'
        });
        this.router.navigate(['/login']);
      },
      error: (error) => {
        console.error('Registration failed', error);
        let errorMessage = 'Registration failed';
        
        if (error.error) {
            // Check if error.error is a string (sometimes happens with API Gateway)
            if (typeof error.error === 'string') {
                try {
                    const parsedError = JSON.parse(error.error);
                    if (parsedError.message) {
                        errorMessage = parsedError.message;
                    } else {
                        errorMessage = error.error;
                    }
                } catch (e) {
                    // If parsing fails, use the string directly
                    errorMessage = error.error;
                }
            } else if (error.error.message) {
                // Standard JSON error object
                errorMessage = error.error.message;
            }
        } else if (error.message) {
            // Fallback to generic HTTP error message
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
