import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatChipsModule } from '@angular/material/chips';

@Component({
  selector: 'app-lti-flipfit-admin-users',
  standalone: true,
  imports: [
    CommonModule,
    MatTableModule,
    MatButtonModule,
    MatIconModule,
    MatInputModule,
    MatFormFieldModule,
    MatChipsModule
  ],
  templateUrl: './lti-flipfit-admin-users.component.html',
  styleUrl: './lti-flipfit-admin-users.component.scss'
})
export class LtiFlipFitAdminUsersComponent {
  constructor(private router: Router) {}

  viewUser(id: string) {
    this.router.navigate(['/admin-dashboard/users', id]);
  }
  displayedColumns: string[] = ['name', 'email', 'role', 'status', 'actions'];
  users = [
    { name: 'John Doe', email: 'john@example.com', role: 'Gym Owner', status: 'Active' },
    { name: 'Alice Smith', email: 'alice@example.com', role: 'Customer', status: 'Active' },
    { name: 'Bob Wilson', email: 'bob@example.com', role: 'Customer', status: 'Active' },
    { name: 'Sarah Jones', email: 'sarah@example.com', role: 'Gym Owner', status: 'Active' },
    { name: 'Mike Brown', email: 'mike@example.com', role: 'Customer', status: 'Active' }
  ];
}
