import { Routes } from '@angular/router';
import { LtiFlipFitLogin } from './components/lti-flipfit-login/lti-flipfit-login';
import { LtiFlipFitSignUp } from './components/lti-flipfit-signup/lti-flipfit-signup';
import { LtiFlipFitAdminDashboard } from './components/lti-flipfit-admin/lti-flipfit-admin-dashboard/lti-flipfit-admin-dashboard';
import { LtiFlipFitGymOwnerDashboard } from './components/lti-flipfit-owner/lti-flipfit-owner-dashboard/lti-flipfit-owner-dashboard';
import { LtiFlipFitAdminOverviewComponent } from './components/lti-flipfit-admin/lti-flipfit-admin-overview/lti-flipfit-admin-overview.component';
import { LtiFlipFitAdminGymsComponent } from './components/lti-flipfit-admin/lti-flipfit-admin-gyms/lti-flipfit-admin-gyms.component';
import { LtiFlipFitAdminGymDetailsComponent } from './components/lti-flipfit-admin/lti-flipfit-admin-gym-details/lti-flipfit-admin-gym-details.component';
import { LtiFlipFitAdminUsersComponent } from './components/lti-flipfit-admin/lti-flipfit-admin-users/lti-flipfit-admin-users.component';
import { LtiFlipFitAdminUserDetailsComponent } from './components/lti-flipfit-admin/lti-flipfit-admin-user-details/lti-flipfit-admin-user-details.component';
import { LtiFlipFitAdminSlotsComponent } from './components/lti-flipfit-admin/lti-flipfit-admin-slots/lti-flipfit-admin-slots.component';
import { LtiFlipFitAdminSlotDetailsComponent } from './components/lti-flipfit-admin/lti-flipfit-admin-slot-details/lti-flipfit-admin-slot-details.component';
import { LtiFlipFitOwnerGymsComponent } from './components/lti-flipfit-owner/lti-flipfit-owner-gyms/lti-flipfit-owner-gyms.component';
import { LtiFlipFitOwnerSlotsComponent } from './components/lti-flipfit-owner/lti-flipfit-owner-slots/lti-flipfit-owner-slots.component';
import { LtiFlipFitProfileComponent } from './components/common/lti-flipfit-profile/lti-flipfit-profile.component';
import { LtiFlipFitOwnerAddGymComponent } from './components/lti-flipfit-owner/lti-flipfit-owner-add-gym/lti-flipfit-owner-add-gym.component';
import { LtiFlipFitOwnerOverviewComponent } from './components/lti-flipfit-owner/lti-flipfit-owner-overview/lti-flipfit-owner-overview.component';
import { LtiFlipFitOwnerGymDetailsComponent } from './components/lti-flipfit-owner/lti-flipfit-owner-gym-details/lti-flipfit-owner-gym-details.component';
import { LtiFlipFitCustomerLayoutComponent } from './components/lti-flipfit-customer/lti-flipfit-customer-layout/lti-flipfit-customer-layout.component';
import { LtiFlipFitCustomerHomeComponent } from './components/lti-flipfit-customer/lti-flipfit-customer-home/lti-flipfit-customer-home.component';
import { LtiFlipFitCustomerBookingComponent } from './components/lti-flipfit-customer/lti-flipfit-customer-booking/lti-flipfit-customer-booking.component';
import { LtiFlipfitCustomerProfileComponent } from './components/lti-flipfit-customer/lti-flipfit-customer-profile/lti-flipfit-customer-profile.component';

import { LtiFlipfitLandingComponent } from './components/lti-flipfit-landing/lti-flipfit-landing.component';

import { LtiFlipFitAdminReportComponent } from './components/lti-flipfit-admin/lti-flipfit-admin-report/lti-flipfit-admin-report.component';
import { LtiFlipFitOwnerReportComponent } from './components/lti-flipfit-owner/lti-flipfit-owner-report/lti-flipfit-owner-report.component';

export const routes: Routes = [
  { path: '', redirectTo: '/flipfit', pathMatch: 'full' },
  { path: 'flipfit', component: LtiFlipfitLandingComponent },
  { path: 'login', component: LtiFlipFitLogin },
  { path: 'register', component: LtiFlipFitSignUp },
  {
    path: 'admin-dashboard',
    component: LtiFlipFitAdminDashboard,
    children: [
      { path: '', redirectTo: 'overview', pathMatch: 'full' },
      { path: 'overview', component: LtiFlipFitAdminOverviewComponent },
      { path: 'report', component: LtiFlipFitAdminReportComponent },
      { path: 'gyms', component: LtiFlipFitAdminGymsComponent },
      { path: 'gyms/:id', component: LtiFlipFitAdminGymDetailsComponent },
      { path: 'users', component: LtiFlipFitAdminUsersComponent },
      { path: 'users/:id', component: LtiFlipFitAdminUserDetailsComponent },
      { path: 'slots', component: LtiFlipFitAdminSlotsComponent },
      { path: 'slots/:id', component: LtiFlipFitAdminSlotDetailsComponent },
      { path: 'profile', component: LtiFlipFitProfileComponent }
    ]
  },
  {
    path: 'gym-owner-dashboard',
    component: LtiFlipFitGymOwnerDashboard,
    children: [
      { path: '', redirectTo: 'overview', pathMatch: 'full' },
      { path: 'overview', component: LtiFlipFitOwnerOverviewComponent },
      { path: 'report', component: LtiFlipFitOwnerReportComponent },
      { path: 'my-gyms', component: LtiFlipFitOwnerGymsComponent },
      { path: 'slots', component: LtiFlipFitOwnerSlotsComponent },
      { path: 'profile', component: LtiFlipFitProfileComponent },
      { path: 'add-gym', component: LtiFlipFitOwnerAddGymComponent },
      { path: 'edit-gym/:id', component: LtiFlipFitOwnerAddGymComponent },
      { path: 'gym-details/:id', component: LtiFlipFitOwnerGymDetailsComponent }
    ]
  },
  {
    path: 'customer-dashboard',
    component: LtiFlipFitCustomerLayoutComponent,
    children: [
      { path: '', redirectTo: 'home', pathMatch: 'full' },
      { path: 'home', component: LtiFlipFitCustomerHomeComponent },
      { path: 'workouts', component: LtiFlipFitCustomerBookingComponent },
      { path: 'profile', component: LtiFlipfitCustomerProfileComponent },
      { path: 'manage-profile', component: LtiFlipFitProfileComponent }
    ]
  }
];
