import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BaseChartDirective } from 'ng2-charts';
import { ChartConfiguration, ChartData, ChartType } from 'chart.js';
import { OwnerService } from '../../../services/owner-service/owner.service';

@Component({
  selector: 'app-lti-flipfit-owner-report',
  standalone: true,
  imports: [CommonModule, BaseChartDirective],
  templateUrl: './lti-flipfit-owner-report.component.html',
  styleUrl: './lti-flipfit-owner-report.component.scss'
})
export class LtiFlipFitOwnerReportComponent implements OnInit {
  public barChartOptions: ChartConfiguration['options'] = {
    responsive: true,
    scales: {
      x: {},
      y: {
        min: 0,
        ticks: {
          stepSize: 1
        }
      }
    },
    plugins: {
      legend: {
        display: true,
      },
      title: {
        display: true,
        text: 'Monthly Gym Bookings'
      }
    }
  };
  public barChartType: ChartType = 'bar';

  public barChartData: ChartData<'bar'> = {
    labels: [ 'Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec' ],
    datasets: [
      { data: [], label: 'Total Bookings' }
    ]
  };

  constructor(private ownerService: OwnerService) {}

  ngOnInit() {
    this.fetchData();
  }

  fetchData() {
    const userStr = localStorage.getItem('user');
    if (userStr) {
      const user = JSON.parse(userStr);
      if (user.userId) {
        this.ownerService.getOwnerByUserId(user.userId).subscribe({
            next: (owner) => {
                // Assuming owner object has id or ownerId
                const ownerId = owner.id || owner.ownerId;
                if (ownerId) {
                   this.fetchBookings(ownerId);
                }
            },
            error: (err) => console.error('Failed to fetch owner details', err)
        });
      }
    }
  }

  fetchBookings(ownerId: number) {
    this.ownerService.getAllBookingsByOwner(ownerId).subscribe({
      next: (bookings: any[]) => {
        const bookingsByMonth = new Array(12).fill(0);
        
        bookings.forEach(booking => {
          let dateStr = booking.date || booking.bookingDate;
          if (dateStr) {
             const date = new Date(dateStr);
             const month = date.getMonth();
             if (month >= 0 && month < 12) {
               bookingsByMonth[month]++;
             }
          }
        });

        this.barChartData = {
          ...this.barChartData,
          datasets: [
            { data: bookingsByMonth, label: 'Total Bookings' }
          ]
        };
      },
      error: (err) => console.error('Failed to fetch bookings', err)
    });
  }
}
