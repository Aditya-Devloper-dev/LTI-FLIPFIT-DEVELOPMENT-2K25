import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatChipsModule } from '@angular/material/chips';
import { UserService } from '../../../services/user-service/user.service';
import { RoleType } from '../../../models/enums/role.type';

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
export class LtiFlipFitAdminUsersComponent implements OnInit {
  displayedColumns: string[] = ['name', 'email', 'role', 'status', 'actions'];
  users: any[] = [];

  constructor(
    private router: Router,
    private userService: UserService
  ) {}

  ngOnInit() {
    this.loadUsers();
  }

  loadUsers() {
    this.userService.getAllUsers().subscribe({
      next: (data) => {
        this.users = data
          .filter(user => user.role?.roleName === RoleType.OWNER || user.role?.roleName === RoleType.CUSTOMER)
          .map(user => ({
            name: user.fullName,
            email: user.email,
            role: user.role?.roleName === RoleType.OWNER ? 'Gym Owner' : 'Customer',
            status: 'Active', // Defaulting to Active as User entity doesn't have status
            id: user.userId
          }));
      },
      error: (err) => console.error('Failed to load users', err)
    });
  }

  viewUser(id: string) {
    this.router.navigate(['/admin-dashboard/users', id]);
  }
}
