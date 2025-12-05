import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { GymCenter } from '../../models/gym-center/gym-center.model';

@Injectable({
  providedIn: 'root'
})
export class AdminService {
  private baseUrl = 'http://localhost:8080/admin';

  constructor(private http: HttpClient) { }

  getAllCenters(): Observable<GymCenter[]> {
    return this.http.get<GymCenter[]>(`${this.baseUrl}/centers`);
  }

  getPendingCenters(): Observable<GymCenter[]> {
    return this.http.get<GymCenter[]>(`${this.baseUrl}/pending-centers`);
  }

  approveCenter(centerId: number): Observable<string> {
    return this.http.put(`${this.baseUrl}/approve-center/${centerId}`, {}, { responseType: 'text' });
  }

  deleteCenter(centerId: number): Observable<string> {
    return this.http.delete(`${this.baseUrl}/delete-center/${centerId}`, { responseType: 'text' });
  }
}
