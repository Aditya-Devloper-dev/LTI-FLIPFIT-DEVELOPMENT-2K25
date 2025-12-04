import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-lti-flipfit-owner-profile',
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule
  ],
  templateUrl: './lti-flipfit-owner-profile.component.html',
  styleUrl: './lti-flipfit-owner-profile.component.scss'
})
export class LtiFlipFitOwnerProfileComponent {
  // Mock user data
  user = {
    fullName: 'John Doe',
    email: 'john.doe@example.com',
    phone: '+1 234 567 890'
  };

  onSaveProfile() {
    console.log('Profile saved', this.user);
  }

  onUpdatePassword() {
    console.log('Password updated');
  }
}
