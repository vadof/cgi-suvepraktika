import { Component } from '@angular/core';
import {HeaderComponent} from "../../components/header/header.component";
import {IMovie} from "../../models/IMovie";
import {NgForOf} from "@angular/common";
import {MovieItemComponent} from "../../components/movie-item/movie-item.component";
import {HttpService} from "../../services/http.service";

@Component({
  selector: 'app-main-page',
  standalone: true,
  imports: [
    HeaderComponent,
    NgForOf,
    MovieItemComponent
  ],
  templateUrl: './main-page.component.html',
  styleUrl: './main-page.component.scss'
})
export class MainPageComponent {
  public recommendations: IMovie[] = []

  constructor(private http: HttpService) {
    this.getRecommendations();
  }

  private getRecommendations(): void {
    this.http.sendGetRequest('/api/v1/movies/recommendation?limit=3').subscribe(res => {
      this.recommendations = res as IMovie[];
    })
  }
}
