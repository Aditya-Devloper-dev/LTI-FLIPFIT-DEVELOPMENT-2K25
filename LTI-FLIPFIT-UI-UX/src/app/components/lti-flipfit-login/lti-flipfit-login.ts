import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, Router } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { FormsModule } from '@angular/forms';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { UserService } from '../../services/user.service';
import { RoleType } from '../../models/role.type';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink,
    MatCardModule,
    MatInputModule,
    MatButtonModule,
    MatCheckboxModule,
    MatFormFieldModule,
    MatIconModule,
    FormsModule,
    MatSnackBarModule
  ],
  templateUrl: './lti-flipfit-login.html',
  styleUrl: './lti-flipfit-login.scss'
})
export class LtiFlipFitLogin {
  hidePassword = true;
  email = '';
  password = '';

  constructor(
    private userService: UserService,
    private router: Router,
    private snackBar: MatSnackBar
  ) {}

  onSubmit() {
    console.log('Logging in user:', this.email);
    const credentials = { email: this.email, password: this.password };
    this.userService.login(credentials).subscribe({
      next: (response) => {
        console.log('Login successful', response);
        this.snackBar.open('Login successful!', 'Close', {
          duration: 3000,
          horizontalPosition: 'center',
          verticalPosition: 'top'
        });
        
        // Store user details
        localStorage.setItem('user', JSON.stringify(response));

        // Redirect based on role
        const role = response.roleName;
        if (role === RoleType.ADMIN) {
            this.router.navigate(['/admin-dashboard']);
        } else if (role === RoleType.OWNER) {
            this.router.navigate(['/gym-owner-dashboard']);
        } else if (role === RoleType.CUSTOMER) {
            this.router.navigate(['/customer-dashboard']);
        } else {
            this.router.navigate(['/dashboard']); // Fallback
        }
      },
      error: (error) => {
        console.error('Login failed', error);
        let errorMessage = 'Login failed';
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
