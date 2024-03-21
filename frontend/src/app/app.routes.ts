import { Routes } from '@angular/router';
import {LoginPageComponent} from "./pages/login-page/login-page.component";
import {RegisterPageComponent} from "./pages/register-page/register-page.component";
import {MainPageComponent} from "./pages/main-page/main-page.component";
import {MoviePageComponent} from "./pages/movie-page/movie-page.component";

export const routes: Routes = [
  {path: 'login', component: LoginPageComponent},
  {path: 'register', component: RegisterPageComponent},
  {path: '', component: MainPageComponent},
  {path: 'movies/:id', component: MoviePageComponent}
];
