import { Routes } from '@angular/router';
import { LtiFlipFitLogin } from './components/lti-flipfit-login/lti-flipfit-login';
import { LtiFlipFitSignUp } from './components/lti-flipfit-signup/lti-flipfit-signup';
import { LtiFlipFitAdminDashboard } from './components/lti-flipfit-admin/lti-flipfit-admin-dashboard/lti-flipfit-admin-dashboard';
import { LtiFlipFitGymOwnerDashboard } from './components/lti-flipfit-owner/lti-flipfit-owner-dashboard/lti-flipfit-owner-dashboard';
import { LtiFlipFitCustomerDashboard } from './components/lti-flipfit-customer/lti-flipfit-customer-dashboard/lti-flipfit-customer-dashboard';

export const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: LtiFlipFitLogin },
  { path: 'register', component: LtiFlipFitSignUp },
  { path: 'admin-dashboard', component: LtiFlipFitAdminDashboard },
  { path: 'gym-owner-dashboard', component: LtiFlipFitGymOwnerDashboard },
  { path: 'customer-dashboard', component: LtiFlipFitCustomerDashboard }
];
