import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatChipsModule } from '@angular/material/chips';
import { MatSelectModule } from '@angular/material/select';
import { UserService } from '../../../services/user-service/user.service';
import { AdminService } from '../../../services/admin-service/admin.service';
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
    MatChipsModule,
    MatSelectModule
  ],
  templateUrl: './lti-flipfit-admin-users.component.html',
  styleUrl: './lti-flipfit-admin-users.component.scss'
})
export class LtiFlipFitAdminUsersComponent implements OnInit {
  displayedColumns: string[] = ['name', 'email', 'role', 'status', 'actions'];
  users: any[] = [];
  allUsers: any[] = [];
  selectedRole: string = 'ALL';

  constructor(
    private router: Router,
    private userService: UserService,
    private adminService: AdminService
  ) {}

  ngOnInit() {
    this.loadUsers();
  }

  loadUsers() {
    // Determine status by checking pending owners
    this.adminService.getPendingOwners().subscribe(pendingOwners => {
      const pendingUserIds = new Set(pendingOwners.map(o => o.user?.userId));

      this.userService.getAllUsers().subscribe({
        next: (data) => {
          this.allUsers = data
            .filter(user => user.role?.roleName === RoleType.OWNER || user.role?.roleName === RoleType.CUSTOMER)
            .map(user => {
              let status = 'Active';
              if (user.role?.roleName === RoleType.OWNER) {
                status = pendingUserIds.has(user.userId) ? 'Pending' : 'Approved';
              }
              
              return {
                name: user.fullName,
                email: user.email,
                role: user.role?.roleName === RoleType.OWNER ? 'Gym Owner' : 'Customer',
                rawRole: user.role?.roleName,
                status: status,
                id: user.userId
              };
            });
          this.applyFilter();
        },
        error: (err) => console.error('Failed to load users', err)
      });
    });
  }

  onRoleFilterChange(event: any) {
    this.selectedRole = event.value;
    this.applyFilter();
  }

  applyFilter() {
    if (this.selectedRole === 'ALL') {
      this.users = [...this.allUsers];
    } else {
      this.users = this.allUsers.filter(user => 
        (this.selectedRole === 'OWNER' && user.rawRole === RoleType.OWNER) ||
        (this.selectedRole === 'CUSTOMER' && user.rawRole === RoleType.CUSTOMER)
      );
    }
  }

  viewUser(id: string) {
    this.router.navigate(['/admin-dashboard/users', id]);
  }
}
