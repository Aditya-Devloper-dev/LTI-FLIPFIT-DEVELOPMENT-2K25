import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { GymCenter } from '../../models/gym-center/gym-center.model';

/**
 * @author: 
 * @version: 1.0
 * @Service: AdminService
 * @description: Service to handle admin related operations like fetching centers, approving centers, owners etc.
 */
@Injectable({
  providedIn: 'root'
})
export class AdminService {
  private baseUrl = 'http://localhost:8080/admin';

  constructor(private http: HttpClient) { }

  /**
   * @description: Fetches all gym centers.
   * @returns: Observable of GymCenter array.
   */
  getAllCenters(): Observable<GymCenter[]> {
    return this.http.get<GymCenter[]>(`${this.baseUrl}/centers`);
  }

  /**
   * @description: Fetches all pending gym centers.
   * @returns: Observable of GymCenter array.
   */
  getPendingCenters(): Observable<GymCenter[]> {
    return this.http.get<GymCenter[]>(`${this.baseUrl}/pending-centers`);
  }

  /**
   * @description: Approves a gym center by its ID.
   * @param centerId: The ID of the center to approve.
   * @returns: Observable of string response.
   */
  approveCenter(centerId: number): Observable<string> {
    return this.http.put(`${this.baseUrl}/approve-center/${centerId}`, {}, { responseType: 'text' });
  }

  /**
   * @description: Fetches a single gym center by its ID.
   * @param centerId: The ID of the center to fetch.
   * @returns: Observable of GymCenter object.
   */
  getCenterById(centerId: number): Observable<GymCenter> {
    return this.http.get<GymCenter>(`${this.baseUrl}/center/${centerId}`);
  }

  /**
   * @description: Deletes a gym center by its ID.
   * @param centerId: The ID of the center to delete.
   * @returns: Observable of string response.
   */
  deleteCenter(centerId: number): Observable<string> {
    return this.http.delete(`${this.baseUrl}/delete-center/${centerId}`, { responseType: 'text' });
  }

  /**
   * @description: Approves a gym owner by their ID.
   * @param ownerId: The ID of the owner to approve.
   * @returns: Observable of string response.
   */
  approveOwner(ownerId: number): Observable<string> {
    return this.http.put(`${this.baseUrl}/approve-owner/${ownerId}`, {}, { responseType: 'text' });
  }
}
