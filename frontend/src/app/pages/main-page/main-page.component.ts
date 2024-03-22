import {Component, OnInit} from '@angular/core';
import {HeaderComponent} from "../../components/header/header.component";
import {IMovie} from "../../models/IMovie";
import {NgForOf, NgIf} from "@angular/common";
import {MovieItemComponent} from "../../components/movie-item/movie-item.component";
import {HttpService} from "../../services/http.service";
import {IMovieSession} from "../../models/IMovieSession";
import {MovieSessionComponent} from "../../components/movie-session/movie-session.component";

@Component({
  selector: 'app-main-page',
  standalone: true,
  imports: [
    HeaderComponent,
    NgForOf,
    MovieItemComponent,
    NgIf,
    MovieSessionComponent
  ],
  templateUrl: './main-page.component.html',
  styleUrl: './main-page.component.scss'
})
export class MainPageComponent implements OnInit {
  recommendations: IMovie[] = []
  dates: Date[] = [];
  selectedDate: Date | undefined;
  movieSessions: IMovieSession[] = [];

  months = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"]

  constructor(private http: HttpService) {
  }

  ngOnInit(): void {
    this.getRecommendations();
    this.getMovieSessionDates();
  }

  private getRecommendations(): void {
    this.http.sendGetRequest('/api/v1/movies/recommendation?limit=3').subscribe(res => {
      this.recommendations = res as IMovie[];
    })
  }

  private getMovieSessionDates() {
    this.http.sendGetRequest('/api/v1/movie-sessions/dates').subscribe(res => {
      this.dates = res.map((dateStr: string) => new Date(dateStr));
      this.selectDate(this.dates[0]);
    })
  }

  selectDate(date: Date) {
    this.selectedDate = date;
    this.http.sendGetRequest(`/api/v1/movie-sessions/dates/${this.dateToStr(date)}`).subscribe(res => {
      this.movieSessions = res as IMovieSession[];
    })
  }

  private dateToStr(date: Date) {
    let year = date.getFullYear() + "";
    let month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1 + "";
    let day = date.getDate() + "";

    return `${year}-${month}-${day}`;
  }
}
