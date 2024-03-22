import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {API_URL} from "../config/constants";
import {TokenStorageService} from "./token-storage.service";
import {Observable} from "rxjs";
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class HttpService {

  constructor(
    private http: HttpClient,
    private storage: TokenStorageService,
    private router: Router
  ) {}

  private API_URL = API_URL
  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${this.storage.getToken()}`
    })
  }

  public sendPostRequest(url: string, body: any): Observable<any> {
    this.validateToken();
    return this.http.post(this.API_URL + url, body, this.httpOptions)
  }

  public sendGetRequest(url: string): Observable<any> {
    this.validateToken();
    return this.http.get(this.API_URL + url, this.httpOptions);
  }

  public sendDeleteRequest(url: string): Observable<any> {
    this.validateToken();
    return this.http.delete(this.API_URL + url, this.httpOptions);
  }

  public sendPutRequest(url: string, body: any): Observable<any> {
    this.validateToken();
    return this.http.put(this.API_URL + url, body, this.httpOptions)
  }

  private validateToken(): void {
    let token = this.storage.getToken();
    if (!token || this.tokenIsExpired(token)) {
      this.storage.signOut();
      this.router.navigate(['login']).then(() => location.reload());
    }
  }

  private tokenIsExpired(token: string): boolean {
    const expiry = (JSON.parse(atob(token.split('.')[1]))).exp;
    return (Math.floor((new Date).getTime() / 1000)) >= expiry;
  }

}
