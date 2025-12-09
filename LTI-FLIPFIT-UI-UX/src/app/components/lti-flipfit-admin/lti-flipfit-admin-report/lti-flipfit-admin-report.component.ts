import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BaseChartDirective } from 'ng2-charts';
import { ChartConfiguration, ChartData, ChartType } from 'chart.js';
import { AdminService } from '../../../services/admin-service/admin.service';

@Component({
  selector: 'app-lti-flipfit-admin-report',
  standalone: true,
  imports: [CommonModule, BaseChartDirective],
  templateUrl: './lti-flipfit-admin-report.component.html',
  styleUrl: './lti-flipfit-admin-report.component.scss'
})
export class LtiFlipFitAdminReportComponent implements OnInit {
  public barChartOptions: ChartConfiguration['options'] = {
    responsive: true,
    scales: {
      x: {},
      y: {
        min: 0
      }
    },
    plugins: {
      legend: {
        display: true,
      },
      tooltip: {
        enabled: true
      }
    }
  };
  public barChartType: ChartType = 'bar';

  public barChartData: ChartData<'bar'> = {
    labels: [ 'Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec' ],
    datasets: [
      { data: [], label: 'Monthly Revenue (₹)' },
    ]
  };

  constructor(private adminService: AdminService) {}

  ngOnInit() {
    this.fetchData();
  }

  fetchData() {
    this.adminService.viewPayments('ALL').subscribe({
      next: (payments: any[]) => {
        const revenueByMonth = new Array(12).fill(0);
        console.log('Payments data:', payments);

        payments.forEach(payment => {
          let dateStr = payment.booking?.bookingDate || payment.paymentDate;
          
          if (dateStr) {
             const date = new Date(dateStr);
             const month = date.getMonth(); // 0-11
             if (month >= 0 && month < 12) {
               revenueByMonth[month] += payment.amount;
             }
          }
        });

        this.barChartData = {
          ...this.barChartData,
          datasets: [
            { data: revenueByMonth, label: 'Monthly Revenue (₹)' }
          ]
        };
      },
      error: (err) => {
        console.error('Failed to fetch payments', err);
      }
    });
  }
}
