import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {API_URL} from "../config/constants";
import {AuthResponse} from "../models/AuthResponse";

const LOGIN_URL = `${API_URL}/auth/login`
const REGISTER_URL = `${API_URL}/auth/register`

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json'
  })
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private http: HttpClient) {
  }

  public login(request: any): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(LOGIN_URL, request, httpOptions);
  }

  public register(request: any): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(REGISTER_URL, request, httpOptions);
  }
}
