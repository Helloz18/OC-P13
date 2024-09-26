import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  public username: string = "";

  constructor(private http: HttpClient) { }

  
  /**
   * login method
   * @returns
   */
  login(username: string): Observable<any> {
    return this.http.post('http://localhost:8080/user/connect?username='+username, {});
  }
}
