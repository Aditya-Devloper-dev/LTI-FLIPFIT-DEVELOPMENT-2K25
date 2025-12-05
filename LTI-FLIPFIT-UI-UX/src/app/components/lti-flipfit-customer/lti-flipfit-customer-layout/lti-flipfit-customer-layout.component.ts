import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet, Router } from '@angular/router';
import { UserService } from '../../../services/user-service/user.service';
import { HeaderComponent, MenuItem } from '../../../shared/components/header/header.component';
import { FooterComponent } from '../../../shared/components/footer/footer.component';

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
    { label: 'Blog', route: '/customer-dashboard/blog' },
    { label: 'Profile', route: '/customer-dashboard/profile' }
  ];

  onLogout() {
    console.log('Logout clicked');
    localStorage.removeItem('user');
    this.router.navigate(['/login']);
  }

  onProfile() {
    console.log('Profile clicked');
    this.router.navigate(['/customer-dashboard/profile']);
  }
}
