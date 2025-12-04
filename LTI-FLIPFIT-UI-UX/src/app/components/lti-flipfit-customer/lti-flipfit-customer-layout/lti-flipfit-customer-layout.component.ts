import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
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
export class LtiFlipFitCustomerLayoutComponent {
  menuItems: MenuItem[] = [
    { label: 'Home', route: '/customer-dashboard/home' },
    { label: 'Workouts', route: '/customer-dashboard/workouts' },
    { label: 'Blog', route: '/customer-dashboard/blog' },
    { label: 'Profile', route: '/customer-dashboard/profile' }
  ];

  onLogout() {
    console.log('Logout clicked');
  }

  onProfile() {
    console.log('Profile clicked');
  }
}
