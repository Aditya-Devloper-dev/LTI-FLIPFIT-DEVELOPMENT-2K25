import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet, Router } from '@angular/router';
import { UserService } from '../../../services/user-service/user.service';
import { HeaderComponent, MenuItem } from '../../../shared/components/header/header.component';
import { FooterComponent } from '../../../shared/components/footer/footer.component';

/**
 * @author: 
 * @version: 1.0
 * @Component: LtiFlipFitCustomerLayoutComponent
 * @description: Main layout component for the customer dashboard, including header and footer.
 */
@Component({
  selector: 'app-lti-flipfit-customer-layout',
  standalone: true,
  imports: [
    CommonModule,
    RouterOutlet,
    HeaderComponent,
    FooterComponent
  ],
  templateUrl: './lti-flipfit-customer-layout.component.html',
  styleUrl: './lti-flipfit-customer-layout.component.scss'
})
export class LtiFlipFitCustomerLayoutComponent implements OnInit {
  userName: string = '';
  userRole: string = '';

  constructor(
    private router: Router,
    private userService: UserService
  ) {}

  /**
   * @methodname: ngOnInit
   * @description: Lifecycle hook. Initializes user information from local storage.
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
    { label: 'Home', route: '/customer-dashboard/home' },
    { label: 'Workouts', route: '/customer-dashboard/workouts' },
    { label: 'Profile', route: '/customer-dashboard/profile' }
  ];

  /**
   * @methodname: onLogout
   * @description: Logs out the current user and redirects to login page.
   */
  onLogout() {
    console.log('Logout clicked');
    localStorage.removeItem('user');
    this.router.navigate(['/login']);
  }

  /**
   * @methodname: onProfile
   * @description: Navigates to the user profile management page.
   */
  onProfile() {
    console.log('Profile clicked');
    this.router.navigate(['/customer-dashboard/manage-profile']);
  }
}
