import {Component, Input, OnInit} from '@angular/core';
import {IMovieSession} from "../../models/IMovieSession";
import {NgClass, NgIf, NgOptimizedImage} from "@angular/common";
import {dateToHHMM, genresToString} from "../../utils/utils";
import {RouterLink} from "@angular/router";

@Component({
  selector: 'app-movie-session',
  standalone: true,
  imports: [
    NgOptimizedImage,
    NgIf,
    NgClass,
    RouterLink
  ],
  templateUrl: './movie-session.component.html',
  styleUrl: './movie-session.component.scss'
})
export class MovieSessionComponent implements OnInit {
  @Input() movieSession!: IMovieSession;
  time: string = '';
  genres: string = '';

  ngOnInit(): void {
    let description = this.movieSession.movie.overview;
    this.movieSession.movie.overview = description.substring(0, description.indexOf(".") + 1)

    let start = new Date(this.movieSession.startDate);
    let end = new Date(this.movieSession.endDate);

    this.time = `${dateToHHMM(start)}  -  ${dateToHHMM(end)}`
    this.genres = genresToString(this.movieSession.movie.genres);
  }
}
