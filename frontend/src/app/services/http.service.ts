import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {API_URL} from "../config/constants";
import {TokenStorageService} from "./token-storage.service";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class HttpService {

  constructor(
    private http: HttpClient,
    private storage: TokenStorageService
  ) {}

  private API_URL = API_URL
  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${this.storage.getToken()}`
    })
  }

  public sendPostRequest(url: string, body: any): Observable<any> {
    return this.http.post(this.API_URL + url, body, this.httpOptions)
  }

  public sendGetRequest(url: string): Observable<any> {
    return this.http.get(this.API_URL + url, this.httpOptions);
  }

  public sendDeleteRequest(url: string): Observable<any> {
    return this.http.delete(this.API_URL + url, this.httpOptions);
  }

  public sendPutRequest(url: string, body: any): Observable<any> {
    return this.http.put(this.API_URL + url, body, this.httpOptions)
  }
}
