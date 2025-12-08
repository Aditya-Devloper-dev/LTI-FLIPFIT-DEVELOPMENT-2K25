import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { HeaderComponent, MenuItem } from '../../shared/components/header/header.component';
import { FooterComponent } from '../../shared/components/footer/footer.component';

/**
 * @author: 
 * @version: 1.0
 * @Component: LtiFlipfitLandingComponent
 * @description: Landing page component for FlipFit application.
 */
@Component({
  selector: 'app-lti-flipfit-landing',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink,
    MatButtonModule,
    MatIconModule,
    HeaderComponent,
    FooterComponent
  ],
  templateUrl: './lti-flipfit-landing.component.html',
  styleUrl: './lti-flipfit-landing.component.scss'
})
export class LtiFlipfitLandingComponent {
  menuItems: MenuItem[] = [
    { label: 'Home', route: '/' },
    { label: 'How it Works', route: '/how-it-works' }
  ];
}