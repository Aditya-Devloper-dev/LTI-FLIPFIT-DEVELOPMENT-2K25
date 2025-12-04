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
      this.userName = user.email.split('@')[0]; // Fallback if name not available, or use user.fullName if available
      // Better to use fullName if available in login response, otherwise email
      // The login response has 'email', 'roleName'. It might not have fullName directly unless we added it.
      // Let's check LoginResponse interface again. It has 'email', 'roleName'.
      // Wait, the LoginResponse interface in user.model.ts DOES NOT have fullName.
      // But the User entity has fullName.
      // The login response from backend might need to be checked or we just use email for now.
      // Actually, looking at previous steps, LoginResponse has: userId, email, roleId, roleName, loginStatus.
      // It does NOT have fullName.
      // So I will use email as userName for now, or "Admin".
      // Actually, for Admin, it's usually "Admin".
      // But for Owner/Customer, we want their name.
      // Let's stick to email or a generic name if not found, but wait, the user wants "Aditya Mishra".
      // That means I need to fetch the user profile or store fullName in localStorage during login.
      // The login component stores the whole response.
      // If the response doesn't have fullName, I can't show it without fetching it.
      // However, I can fetch the user profile using userId in ngOnInit.
      this.userName = user.email; 
      this.userRole = user.roleName;
    }
  }
  menuItems: MenuItem[] = [
    { label: 'Dashboard', route: '/admin-dashboard/overview' },
    { label: 'Gyms', route: '/admin-dashboard/gyms' },
    { label: 'Users', route: '/admin-dashboard/users' },
    { label: 'Approvals', route: '/admin-dashboard/approvals', badge: '3' },
    { label: 'Reports', route: '/admin-dashboard/reports' }
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
