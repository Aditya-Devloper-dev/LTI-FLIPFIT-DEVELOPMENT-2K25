import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {
  private baseUrl = 'http://localhost:8080/customer';

  constructor(private http: HttpClient) { }

  viewAvailability(centerId: string, date: string): Observable<any> {
    return this.http.get(`${this.baseUrl}/availability?centerId=${centerId}&date=${date}`);
  }

  getProfile(customerId: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/profile/${customerId}`);
  }

  getCustomerBookings(customerId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/bookings/${customerId}`);
  }

  viewAllGyms(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/view-gyms`);
  }

  getCustomerByUserId(userId: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/user/${userId}`);
  }
}
