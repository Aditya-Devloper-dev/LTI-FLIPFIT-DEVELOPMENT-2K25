import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {GymCenter} from "../../models/gym-center/gym-center.model";

/**
 * @author: 
 * @version: 1.0
 * @Service for gym owner operations.
 * @description Handles API interactions for gym owner features.
 */
@Injectable({
  providedIn: 'root'
})
export class OwnerService {
  private apiUrl = 'http://localhost:8080/owner';

  constructor(private http: HttpClient) { }

  /**
   * @description: Adds a new gym center for the owner.
   * @param center: The gym center to be added.
   * @param ownerId: The ID of the owner.
   * @returns: Observable of the added gym center.
   */
  addCenter(center: GymCenter, ownerId: number): Observable<GymCenter> {
    return this.http.post<GymCenter>(`${this.apiUrl}/add-center/${ownerId}`, center);
  }

  /**
   * @description: Adds a new slot for the gym center.
   * @param slot: The slot to be added.
   * @param centerId: The ID of the gym center.
   * @param ownerId: The ID of the owner.
   * @returns: Observable of the added slot.
   */
  addSlot(slot: any, centerId: number, ownerId: number): Observable<any> {
    return this.http.post(`${this.apiUrl}/add-slot/${centerId}/${ownerId}`, slot, { responseType: 'text' });
  }

  /**
   * @description: Gets all gym centers for the owner.
   * @param ownerId: The ID of the owner.
   * @returns: Observable of the gym centers.
   */
  getGymsByOwnerId(ownerId: number): Observable<GymCenter[]> {
    return this.http.get<GymCenter[]>(`http://localhost:8080/gym-center/owner/${ownerId}`);
  }

  /**
   * @description: Gets all bookings for the gym center.
   * @param centerId: The ID of the gym center.
   * @returns: Observable of the bookings.
   */
  viewAllBookings(centerId: number): Observable<any[]> {
    return this.http.get<any[]>(`http://localhost:8080/gym-center/bookings/${centerId}`);
  }

  /**
   * @description: Updates the gym center.
   * @param center: The gym center to be updated.
   * @param ownerId: The ID of the owner.
   * @returns: Observable of the updated gym center.
   */
  updateCenter(center: GymCenter, ownerId: number): Observable<GymCenter> {
    return this.http.put<GymCenter>(`http://localhost:8080/gym-center/update/${center.centerId}/${ownerId}`, center);
  }

  /**
   * @description: Toggles the active status of the gym center.
   * @param centerId: The ID of the gym center.
   * @param ownerId: The ID of the owner.
   * @returns: Observable of the toggle result.
   */
  toggleCenterActive(centerId: number, ownerId: number): Observable<any> {
    return this.http.put(`${this.apiUrl}/toggle-center-active/${centerId}/${ownerId}`, {}, { responseType: 'text' });
  }

  /**
   * @description: Gets the gym center details.
   * @param centerId: The ID of the gym center.
   * @returns: Observable of the gym center details.
   */
  getGymDetails(centerId: number): Observable<GymCenter> {
    return this.http.get<GymCenter>(`http://localhost:8080/gym-center/${centerId}`);
  }

  /**
   * @description: Gets all slots for the gym center.
   * @param centerId: The ID of the gym center.
   * @returns: Observable of the slots.
   */
  getSlotsByCenterId(centerId: number): Observable<any[]> {
    return this.http.get<any[]>(`http://localhost:8080/gym-center/slots/${centerId}`);
  }

  /**
   * @description: Toggles the active status of the slot.
   * @param slotId: The ID of the slot.
   * @param ownerId: The ID of the owner.
   * @returns: Observable of the toggle result.
   */
  toggleSlotActive(slotId: number, ownerId: number): Observable<any> {
    return this.http.put(`${this.apiUrl}/toggle-slot-active/${slotId}/${ownerId}`, {}, { responseType: 'text' });
  }

  /**
   * @description: Gets all bookings for the owner.
   * @param ownerId: The ID of the owner.
   * @returns: Observable of the bookings.
   */
  getAllBookingsByOwner(ownerId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/bookings/${ownerId}`);
  }

  /**
   * @description: Gets owner details by user ID.
   * @param userId: The ID of the user.
   * @returns: Observable of the owner details.
   */
  getOwnerByUserId(userId: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/user/${userId}`);
  }
}
