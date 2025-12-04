import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatChipsModule } from '@angular/material/chips';
import { MatDividerModule } from '@angular/material/divider';
import { OwnerService } from '../../../services/owner.service';
import { GymCenter } from '../../../models/gym-center.model';

@Component({
  selector: 'app-lti-flipfit-owner-gym-details',
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatChipsModule,
    MatDividerModule
  ],
  templateUrl: './lti-flipfit-owner-gym-details.component.html',
  styleUrl: './lti-flipfit-owner-gym-details.component.scss'
})
export class LtiFlipFitOwnerGymDetailsComponent implements OnInit {
  gym: GymCenter | null = null;
  isLoading = true;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private ownerService: OwnerService
  ) {}

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.loadGymDetails(Number(id));
    } else {
      this.router.navigate(['/gym-owner-dashboard/my-gyms']);
    }
  }

  loadGymDetails(id: number) {
    this.ownerService.getGymDetails(id).subscribe({
      next: (data) => {
        this.gym = data;
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Error loading gym details', error);
        this.isLoading = false;
        // Handle error (e.g., redirect or show message)
      }
    });
  }

  getStatus(gym: GymCenter): string {
    if (gym.isApproved) {
      return gym.isActive ? 'Active' : 'Inactive';
    }
    return 'Pending Approval';
  }

  goBack() {
    this.router.navigate(['/gym-owner-dashboard/my-gyms']);
  }
}
