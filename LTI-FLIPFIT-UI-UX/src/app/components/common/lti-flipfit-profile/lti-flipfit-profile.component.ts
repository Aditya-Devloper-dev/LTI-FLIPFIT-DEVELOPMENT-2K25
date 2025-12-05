import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { UserService } from '../../../services/user-service/user.service';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';

@Component({
  selector: 'app-lti-flipfit-profile',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatSnackBarModule
  ],
  templateUrl: './lti-flipfit-profile.component.html',
  styleUrl: './lti-flipfit-profile.component.scss'
})
export class LtiFlipFitProfileComponent implements OnInit {
  profileForm: FormGroup;
  userId: number | null = null;
  isLoading = false;

  constructor(
    private fb: FormBuilder,
    private userService: UserService,
    private snackBar: MatSnackBar
  ) {
    this.profileForm = this.fb.group({
      fullName: ['', Validators.required],
      email: [{ value: '', disabled: true }, [Validators.required, Validators.email]],
      phoneNumber: ['', Validators.required],
      password: [''] // Optional for update
    });
  }

  ngOnInit(): void {
    const storedUser = localStorage.getItem('user');
    if (storedUser) {
      const user = JSON.parse(storedUser);
      this.userId = user.userId;
      this.loadUserProfile();
    } else {
      this.showNotification('User ID not found. Please login again.');
    }
  }

  loadUserProfile() {
    if (!this.userId) return;
    this.isLoading = true;
    this.userService.getUserById(this.userId).subscribe({
      next: (user) => {
        this.profileForm.patchValue({
          fullName: user.fullName,
          email: user.email,
          phoneNumber: user.phoneNumber
        });
        this.isLoading = false;
      },
      error: (err) => {
        console.error('Error fetching profile', err);
        this.showNotification('Failed to load profile.');
        this.isLoading = false;
      }
    });
  }

  onSubmit() {
    if (this.profileForm.invalid || !this.userId) return;

    this.isLoading = true;
    const updateData = {
      ...this.profileForm.value,
      // Only include password if it's not empty
      password: this.profileForm.value.password || undefined
    };

    this.userService.updateProfile(this.userId, updateData).subscribe({
      next: (response) => {
        this.showNotification('Profile updated successfully!');
        this.isLoading = false;
        // Clear password field after successful update
        this.profileForm.get('password')?.reset();
      },
      error: (err) => {
        console.error('Error updating profile', err);
        this.showNotification('Failed to update profile.');
        this.isLoading = false;
      }
    });
  }

  private showNotification(message: string) {
    this.snackBar.open(message, 'Close', {
      duration: 3000,
      horizontalPosition: 'end',
      verticalPosition: 'top'
    });
  }
}
