import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet, Router } from '@angular/router';
import { HeaderComponent, MenuItem } from '../../../shared/components/header/header.component';
import { UserService } from '../../../services/user-service/user.service';
import { FooterComponent } from '../../../shared/components/footer/footer.component';

/**
 * @author: 
 * @version: 1.0
 * @description: Dashboard component for Gym Owners.
 */
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

  /**
   * @method ngOnInit
   * @description Initializes the dashboard and fetches user details.
   */
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
    { label: 'Reports', route: '/gym-owner-dashboard/report' },
    { label: 'My Gyms', route: '/gym-owner-dashboard/my-gyms' },
    { label: 'Slots', route: '/gym-owner-dashboard/slots' }
  ];

  /**
   * @method onLogout
   * @description Logs out the user and navigates to login page.
   */
  onLogout() {
    localStorage.removeItem('user');
    this.router.navigate(['/login']);
  }

  /**
   * @method onProfile
   * @description Navigates to the user profile page.
   */
  onProfile() {
    this.router.navigate(['/gym-owner-dashboard/profile']);
  }
}
