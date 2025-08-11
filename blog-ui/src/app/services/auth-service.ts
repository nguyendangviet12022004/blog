import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { LoginRequest } from '../models/auth/login.request';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private httpClient = inject(HttpClient)

  get IsAuthenticated(){
    return !!localStorage.getItem("accessToken")
  }

  login(request:LoginRequest): Observable<any> {
      return this.httpClient.post('auth/login', request)
  }
}
