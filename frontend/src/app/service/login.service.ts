import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../interfaces/user';

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  public connectedUser!: User;

  constructor(private http: HttpClient) {}

  /**
   * login method
   * @returns
   */
  login(username: string): Observable<any> {
    return this.http.post(
      'http://localhost:8080/user/connect?username=' + username,
      {}
    );
  }

  /**
   * logout method
   * @returns
   */
  logout(): Observable<any> {
    return this.http.post(
      'http://localhost:8080/user/disconnect?username=' +
        this.connectedUser.username,
      {}
    );
  }
}
