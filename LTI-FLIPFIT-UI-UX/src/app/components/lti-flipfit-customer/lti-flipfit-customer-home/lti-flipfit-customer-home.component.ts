import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { Router } from '@angular/router';

@Component({
  selector: 'app-lti-flipfit-customer-home',
  standalone: true,
  imports: [
    CommonModule,
    MatButtonModule,
    MatCardModule,
    MatIconModule
  ],
  templateUrl: './lti-flipfit-customer-home.component.html',
  styleUrl: './lti-flipfit-customer-home.component.scss'
})
export class LtiFlipFitCustomerHomeComponent {
  popularClasses = [
    { title: 'Sunrise Yoga', location: 'Bangalore, 18 Ar 202K', image: 'assets/yoga.jpg' },
    { title: 'HIIT Blast', location: 'Bangalore, 18 Ar 202K', image: 'assets/hiit.jpg' },
    { title: 'Spin Cycle', location: 'Bangalore, 18 Ar 201X', image: 'assets/spin.jpg' }
  ];

  constructor(private router: Router) {}

  onFindGym() {
    this.router.navigate(['/customer-dashboard/workouts']);
  }
}
