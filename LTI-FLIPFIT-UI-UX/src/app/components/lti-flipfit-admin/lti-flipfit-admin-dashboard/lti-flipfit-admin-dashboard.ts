import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
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
export class LtiFlipFitAdminDashboard {
  menuItems: MenuItem[] = [
    { label: 'Dashboard', route: '/admin-dashboard/overview' },
    { label: 'Gyms', route: '/admin-dashboard/gyms' },
    { label: 'Users', route: '/admin-dashboard/users' },
    { label: 'Approvals', route: '/admin-dashboard/approvals', badge: '3' },
    { label: 'Reports', route: '/admin-dashboard/reports' }
  ];

  onLogout() {
    console.log('Logout clicked');
    // Implement logout logic
  }

  onProfile() {
    console.log('Profile clicked');
    // Implement profile navigation
  }
}
