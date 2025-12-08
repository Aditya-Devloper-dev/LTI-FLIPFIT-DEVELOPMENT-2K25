import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, Router } from '@angular/router';
import { UserRegistration } from '../../models/user/user.model';
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { ReactiveFormsModule, FormGroup, FormControl, Validators, AbstractControl, ValidationErrors } from '@angular/forms';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { RoleType } from '../../models/enums/role.type';
import { UserService } from '../../services/user-service/user.service';

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
    ReactiveFormsModule,
    MatSnackBarModule
  ],
  templateUrl: './lti-flipfit-signup.html',
  styleUrl: './lti-flipfit-signup.scss',
})
export class LtiFlipFitSignUp {
  hidePassword = true;
  hideConfirmPassword = true;
  signupForm: FormGroup;
  
  constructor(
    private userService: UserService, 
    private router: Router,
    private snackBar: MatSnackBar
  ) {
    this.signupForm = new FormGroup({
      fullName: new FormControl('', [Validators.required]),
      email: new FormControl('', [Validators.required, Validators.email]),
      phoneNumber: new FormControl('', [Validators.required, Validators.pattern('^[0-9]{10}$')]),
      role: new FormControl('CUSTOMER', [Validators.required]),
      password: new FormControl('', [Validators.required, Validators.minLength(6)]),
      confirmPassword: new FormControl('', [Validators.required]),
      businessName: new FormControl(''),
      gstNumber: new FormControl(''),
      panNumber: new FormControl('')
    }, { validators: this.passwordMatchValidator });
  }

  passwordMatchValidator(form: AbstractControl): ValidationErrors | null {
    const password = form.get('password')?.value;
    const confirmPassword = form.get('confirmPassword')?.value;
    return password === confirmPassword ? null : { mismatch: true };
  }

  onRoleChange(role: string) {
    const businessNameControl = this.signupForm.get('businessName');
    const gstNumberControl = this.signupForm.get('gstNumber');
    const panNumberControl = this.signupForm.get('panNumber');

    if (role === 'OWNER') {
      businessNameControl?.setValidators([Validators.required]);
      gstNumberControl?.setValidators([Validators.required]);
      panNumberControl?.setValidators([Validators.required]);
    } else {
      businessNameControl?.clearValidators();
      gstNumberControl?.clearValidators();
      panNumberControl?.clearValidators();
      
      // Reset values when switching back to customer
      businessNameControl?.setValue('');
      gstNumberControl?.setValue('');
      panNumberControl?.setValue('');
    }

    businessNameControl?.updateValueAndValidity();
    gstNumberControl?.updateValueAndValidity();
    panNumberControl?.updateValueAndValidity();
  }

  onSubmit() {
    if (this.signupForm.valid) {
      const formValue = this.signupForm.value;
      console.log('Registering user:', formValue);

      // Create payload matching UserRegistration interface
      const userPayload: UserRegistration = {
        email: formValue.email,
        password: formValue.password,
        fullName: formValue.fullName,
        phoneNumber: formValue.phoneNumber,
        role: formValue.role,
        // Optional fields will be included if they have values
        ...(formValue.role === 'OWNER' && {
          businessName: formValue.businessName,
          gstNumber: formValue.gstNumber,
          panNumber: formValue.panNumber
        })
      };

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
    } else {
      this.snackBar.open('Please fill all required fields correctly', 'Close', {
        duration: 3000,
        panelClass: ['error-snackbar']
      });
    }
  }
}
