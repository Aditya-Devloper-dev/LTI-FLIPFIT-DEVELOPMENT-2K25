import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

/**
 * @author: 
 * @version: 1.0
 * @Service: NotificationService
 * @description: Service to handle notification related operations like fetching notifications, clearing notifications etc.
 */
@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  private apiUrl = 'http://localhost:8080/notification';

  constructor(private http: HttpClient) {}

  /**
   * @description: Get notifications for a specific user.
   * @param userId: The ID of the user.
   * @returns: Observable of notifications data.
   */
  getNotifications(userId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/user/${userId}`);
  }

  /**
   * @description: Clear all notifications for a specific user.
   * @param userId: The ID of the user.
   * @returns: Observable of response data.
   */
  clearAllNotifications(userId: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/user/${userId}`);
  }
}
