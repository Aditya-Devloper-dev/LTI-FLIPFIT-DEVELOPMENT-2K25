import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import { HeaderComponent, MenuItem } from '../../../shared/components/header/header.component';
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
export class LtiFlipFitGymOwnerDashboard {
  menuItems: MenuItem[] = [
    { label: 'Dashboard', route: '/gym-owner-dashboard/overview' },
    { label: 'My Gyms', route: '/gym-owner-dashboard/my-gyms' },
    { label: 'Slots', route: '/gym-owner-dashboard/slots' },
    { label: 'Reports', route: '/gym-owner-dashboard/reports' }
  ];

  onLogout() {
    console.log('Logout clicked');
  }

  onProfile() {
    console.log('Profile clicked');
  }
}
