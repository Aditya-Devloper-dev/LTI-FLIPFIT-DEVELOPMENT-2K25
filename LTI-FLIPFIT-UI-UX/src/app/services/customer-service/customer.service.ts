import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

/**
 * @author: 
 * @version: 1.0
 * @Service: CustomerService
 * @description: Service to handle customer related operations like fetching availability, profile, bookings etc.
 */
@Injectable({
  providedIn: 'root'
})
export class CustomerService {
  private baseUrl = 'http://localhost:8080/customer';

  constructor(private http: HttpClient) { }

  /**
   * @description: Get customer profile by customer ID.
   * @param customerId: The ID of the customer.
   * @returns: Observable of customer profile data.
   */
  getProfile(customerId: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/profile/${customerId}`);
  }

  /**
   * @description: Get customer bookings by customer ID.
   * @param customerId: The ID of the customer.
   * @returns: Observable of customer bookings data.
   */
  getCustomerBookings(customerId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/bookings/${customerId}`);
  }

  /**
   * @description: View all gyms.
   * @returns: Observable of gym data.
   */
  viewAllGyms(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/view-gyms`);
  }

  /**
   * @description: Get customer by user ID.
   * @param userId: The ID of the user.
   * @returns: Observable of customer data.
   */
  getCustomerByUserId(userId: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/user/${userId}`);
  }

  /**
   * @description: Get all available slots.
   * @returns: Observable of slot data.
   */
  getAllSlots(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/slots`);
  }

  /**
   * @description: Join waitlist for a specific slot.
   * @param customerId: The ID of the customer.
   * @param slotId: The ID of the slot.
   * @returns: Observable of the operation result.
   */
  joinWaitlist(customerId: number, slotId: number): Observable<string> {
    const url = `http://localhost:8080/waitlist/join?customerId=${customerId}&slotId=${slotId}`;
    return this.http.post(url, {}, { responseType: 'text' });
  }

  /**
   * @description: Book a slot for a customer.
   * @param bookingRequest: The booking request payload.
   * @returns: Observable of the booking result.
   */
  bookSlot(bookingRequest: any): Observable<any> {
    // URL changed to Booking Controller
    const url = 'http://localhost:8080/booking/book';
    return this.http.post(url, bookingRequest, { responseType: 'text' });
  }

  /**
   * @description: Cancel a booking.
   * @param bookingId: The ID of the booking to cancel.
   * @returns: Observable of the cancellation result.
   */
  cancelBooking(bookingId: number): Observable<any> {
    return this.http.put(`http://localhost:8080/booking/cancel/${bookingId}`, {}, { responseType: 'text' });
  }
}
