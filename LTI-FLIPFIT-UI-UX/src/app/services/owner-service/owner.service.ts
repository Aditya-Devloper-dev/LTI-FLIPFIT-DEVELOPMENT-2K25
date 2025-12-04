import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {GymCenter} from "../../models/gym-center/gym-center.model";

@Injectable({
  providedIn: 'root'
})
export class OwnerService {
  private apiUrl = 'http://localhost:8080/owner';

  constructor(private http: HttpClient) { }

  addCenter(center: GymCenter, ownerId: number): Observable<GymCenter> {
    return this.http.post<GymCenter>(`${this.apiUrl}/add-center/${ownerId}`, center);
  }

  addSlot(slot: any, centerId: number, ownerId: number): Observable<any> {
    return this.http.post(`${this.apiUrl}/add-slot/${centerId}/${ownerId}`, slot, { responseType: 'text' });
  }

  getGymsByOwnerId(ownerId: number): Observable<GymCenter[]> {
    return this.http.get<GymCenter[]>(`http://localhost:8080/gym-center/owner/${ownerId}`);
  }

  viewAllBookings(centerId: number): Observable<any[]> {
    return this.http.get<any[]>(`http://localhost:8080/gym-center/bookings/${centerId}`);
  }

  updateCenter(center: GymCenter, ownerId: number): Observable<GymCenter> {
    return this.http.put<GymCenter>(`http://localhost:8080/gym-center/update/${center.centerId}/${ownerId}`, center);
  }

  toggleCenterActive(centerId: number, ownerId: number): Observable<any> {
    return this.http.put(`${this.apiUrl}/toggle-center-active/${centerId}/${ownerId}`, {}, { responseType: 'text' });
  }

  getGymDetails(centerId: number): Observable<GymCenter> {
    return this.http.get<GymCenter>(`http://localhost:8080/gym-center/${centerId}`);
  }

  getSlotsByCenterId(centerId: number): Observable<any[]> {
    return this.http.get<any[]>(`http://localhost:8080/gym-center/slots/${centerId}`);
  }

  toggleSlotActive(slotId: number, ownerId: number): Observable<any> {
    return this.http.put(`${this.apiUrl}/toggle-slot-active/${slotId}/${ownerId}`, {}, { responseType: 'text' });
  }

  getAllBookingsByOwner(ownerId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/bookings/${ownerId}`);
  }

}
