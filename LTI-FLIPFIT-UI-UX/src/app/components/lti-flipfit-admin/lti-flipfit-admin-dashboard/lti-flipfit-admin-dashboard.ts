import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet, Router } from '@angular/router';
import { HeaderComponent, MenuItem } from '../../../shared/components/header/header.component';
import { FooterComponent } from '../../../shared/components/footer/footer.component';

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [
    CommonModule,
    RouterOutlet,
    HeaderComponent,
    FooterComponent
  ],
  templateUrl: './lti-flipfit-admin-dashboard.html',
  styleUrl: './lti-flipfit-admin-dashboard.scss'
})
export class LtiFlipFitAdminDashboard implements OnInit {
  userName: string = '';
  userRole: string = '';

  constructor(private router: Router) {}

  ngOnInit() {
    const userStr = localStorage.getItem('user');
    if (userStr) {
      const user = JSON.parse(userStr);
      this.userName = user.email.split('@')[0];
      this.userName = user.email;
      this.userRole = user.roleName;
    }
  }
  menuItems: MenuItem[] = [
    { label: 'Dashboard', route: '/admin-dashboard/overview' },
    { label: 'Gyms', route: '/admin-dashboard/gyms' },
    { label: 'Users', route: '/admin-dashboard/users' },
    { label: 'Slots', route: '/admin-dashboard/slots' }
  ];

  onLogout() {
    console.log('Logout clicked');
    localStorage.removeItem('user');
    this.router.navigate(['/login']);
  }

  onProfile() {
    console.log('Profile clicked');
    this.router.navigate(['/admin-dashboard/profile']);
  }
}
