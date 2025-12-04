import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet, Router } from '@angular/router';
import { HeaderComponent, MenuItem } from '../../../shared/components/header/header.component';
import { UserService } from '../../../services/user-service/user.service';
import { FooterComponent } from '../../../shared/components/footer/footer.component';

@Component({
  selector: 'app-gym-owner-dashboard',
  standalone: true,
  imports: [
    CommonModule,
    RouterOutlet,
    HeaderComponent,
    FooterComponent
  ],
  templateUrl: './lti-flipfit-owner-dashboard.html',
  styleUrl: './lti-flipfit-owner-dashboard.scss'
})
export class LtiFlipFitGymOwnerDashboard implements OnInit {
  userName: string = '';
  userRole: string = '';

  constructor(
    private router: Router,
    private userService: UserService
  ) {}

  ngOnInit() {
    const userStr = localStorage.getItem('user');
    if (userStr) {
      const user = JSON.parse(userStr);
      this.userRole = user.roleName;
      
      if (user.userId) {
        this.userService.getUserById(user.userId).subscribe({
          next: (userData) => {
            this.userName = userData.fullName;
          },
          error: (err) => {
            console.error('Failed to fetch user details', err);
            this.userName = user.email; // Fallback
          }
        });
      }
    }
  }
  menuItems: MenuItem[] = [
    { label: 'Dashboard', route: '/gym-owner-dashboard/overview' },
    { label: 'My Gyms', route: '/gym-owner-dashboard/my-gyms' },
    { label: 'Slots', route: '/gym-owner-dashboard/slots' },
    { label: 'Reports', route: '/gym-owner-dashboard/reports' }
  ];

  onLogout() {
    console.log('Logout clicked');
    localStorage.removeItem('user');
    this.router.navigate(['/login']);
  }

  onProfile() {
    console.log('Profile clicked');
    this.router.navigate(['/gym-owner-dashboard/profile']);
  }
}
