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

  getGymsByOwnerId(ownerId: number): Observable<GymCenter[]> {
    return this.http.get<GymCenter[]>(`${this.apiUrl}/centers/${ownerId}`);
  }

  toggleCenterActive(centerId: number, ownerId: number): Observable<any> {
    return this.http.put(`${this.apiUrl}/toggle-center-active/${centerId}/${ownerId}`, {}, { responseType: 'text' });
  }

  getGymDetails(centerId: number): Observable<GymCenter> {
    return this.http.get<GymCenter>(`http://localhost:8080/gym-center/${centerId}`);
  }

}
