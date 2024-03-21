import {Injectable} from '@angular/core';

const TOKEN_KEY = 'AuthToken'
const EMAIL_KEY = 'AuthEmail'

@Injectable({
  providedIn: 'root'
})
export class TokenStorageService {

  public saveToken(token: string) {
    localStorage.removeItem(TOKEN_KEY);
    localStorage.setItem(TOKEN_KEY, token);
  }

  public getToken(): string | null {
    return localStorage.getItem(TOKEN_KEY);
  }

  public saveEmail(email: string) {
    localStorage.removeItem(EMAIL_KEY);
    localStorage.setItem(EMAIL_KEY, email);
  }

  public getEmail(): string | null {
    return localStorage.getItem(EMAIL_KEY);
  }

  public signOut() {
    localStorage.clear();
  }

  public tokenIsValid(): boolean {
    let token = this.getToken();
    return token === null ? false : this.tokenIsExpired(token);
  }

  private tokenIsExpired(token: string): boolean {
    const expiry = (JSON.parse(atob(token.split('.')[1]))).exp;
    return (Math.floor((new Date).getTime() / 1000)) >= expiry;
  }
}
