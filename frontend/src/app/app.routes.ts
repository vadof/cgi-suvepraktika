import { Routes } from '@angular/router';
import {LoginPageComponent} from "./pages/login-page/login-page.component";
import {RegisterPageComponent} from "./pages/register-page/register-page.component";
import {MainPageComponent} from "./pages/main-page/main-page.component";
import {MoviePageComponent} from "./pages/movie-page/movie-page.component";
import {TicketsPageComponent} from "./pages/tickets-page/tickets-page.component";
import {PurchaseHistoryPageComponent} from "./pages/purchase-history-page/purchase-history-page.component";

export const routes: Routes = [
  {path: 'login', component: LoginPageComponent},
  {path: 'register', component: RegisterPageComponent},
  {path: '', component: MainPageComponent},
  {path: 'movies/:id', component: MoviePageComponent},
  {path: 'movieSession/:id/tickets', component: TicketsPageComponent},
  {path: 'my-tickets', component: PurchaseHistoryPageComponent},
];
