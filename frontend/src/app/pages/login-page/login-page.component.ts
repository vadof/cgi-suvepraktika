import { Component } from '@angular/core';
import {HeaderComponent} from "../../components/header/header.component";
import {AuthService} from "../../services/auth.service";
import {Router, RouterLink} from "@angular/router";
import {TokenStorageService} from "../../services/token-storage.service";
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {AuthResponse} from "../../models/AuthResponse";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-login-page',
  standalone: true,
  imports: [
    HeaderComponent,
    ReactiveFormsModule,
    NgIf,
    RouterLink
  ],
  templateUrl: './login-page.component.html',
  styleUrl: './login-page.component.scss'
})
export class LoginPageComponent {
  constructor(
    private authService: AuthService,
    private router: Router,
    private storage: TokenStorageService
  ) {
  }

  public errorMessage = ''

  loginForm = new FormGroup({
    email: new FormControl<string>('', Validators.required),
    password: new FormControl<string>('', Validators.required)
  })

  login() {
    if (this.loginForm.valid) {
      const email: string = this.loginForm.value.email as string;
      const password: string = this.loginForm.value.password as string;

      const loginRequest = {
        email,
        password
      }

      this.authService.login(loginRequest).subscribe(
        res => {
          let response = res as AuthResponse;
          this.storage.saveToken(response.token);
          this.storage.saveEmail(res.user.email);
          this.router.navigate(['']);
        },
        error => {
          this.errorMessage = 'Invalid Email or Password!';
          this.loginForm.reset();
        }
      )
    } else {
      this.errorMessage = 'Fill in the empty fields!'
    }
  }
}
