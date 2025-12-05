import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LoginResponse, UserLogin, UserRegistration } from '../../models/user/user.model';

/**
 * @author: 
 * @version: 1.0
 * @Service for user operations.
 * @description Handles API interactions for user features.
 */
@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = 'http://localhost:8080/user';

  constructor(private http: HttpClient) { }

  /**
   * @description: Registers a new user.
   * @param user: The user to be registered.
   * @returns: Observable of the registration result.
   */
  register(user: UserRegistration): Observable<any> {
    return this.http.post(`${this.apiUrl}/register`, user, { responseType: 'text' });
  }

  /**
   * @description: Logs in a user.
   * @param credentials: The user credentials.
   * @returns: Observable of the login result.
   */
  login(credentials: UserLogin): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/login`, credentials);
  }

  /**
   * @description: Gets a user by ID.
   * @param userId: The ID of the user.
   * @returns: Observable of the user.
   */
  getUserById(userId: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${userId}`);
  }

  /**
   * @description: Updates a user's profile.
   * @param userId: The ID of the user.
   * @param user: The user to be updated.
   * @returns: Observable of the update result.
   */
  updateProfile(userId: number, user: any): Observable<string> {
    return this.http.put(`${this.apiUrl}/update/${userId}`, user, { responseType: 'text' });
  }

  /**
   * @description: Gets all users.
   * @returns: Observable of the users.
   */
  getAllUsers(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/all`);
  }
}
