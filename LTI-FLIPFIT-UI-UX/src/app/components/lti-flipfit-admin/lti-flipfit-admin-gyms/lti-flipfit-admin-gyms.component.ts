import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatChipsModule } from '@angular/material/chips';

@Component({
  selector: 'app-lti-flipfit-admin-gyms',
  standalone: true,
  imports: [
    CommonModule,
    MatTableModule,
    MatButtonModule,
    MatIconModule,
    MatInputModule,
    MatSelectModule,
    MatFormFieldModule,
    MatChipsModule
  ],
  templateUrl: './lti-flipfit-admin-gyms.component.html',
  styleUrl: './lti-flipfit-admin-gyms.component.scss'
})
export class LtiFlipFitAdminGymsComponent {
  displayedColumns: string[] = ['name', 'owner', 'location', 'status', 'actions'];
  gyms = [
    { id: 'GYM001', name: 'FitZone Indiranagar', owner: 'John Doe', location: 'Indiranagar, Bangalore', status: 'Active' },
    { id: 'GYM002', name: 'Muscle Mania', owner: 'Jane Smith', location: 'Koramangala, Bangalore', status: 'Pending' },
    { id: 'GYM003', name: 'Cardio Center', owner: 'Mike Johnson', location: 'Whitefield, Bangalore', status: 'Rejected' },
    { id: 'GYM004', name: 'Yoga Studio', owner: 'Sarah Lee', location: 'Jayanagar, Bangalore', status: 'Active' },
    { id: 'GYM005', name: 'CrossFit Box', owner: 'Tom Wilson', location: 'HSR Layout, Bangalore', status: 'Pending' }
  ];

  constructor(private router: Router) {}

  viewGym(id: string) {
    this.router.navigate(['/admin-dashboard/gyms', id]);
  }
}
