import {Component, Input, OnInit} from '@angular/core';
import {IMovieSession} from "../../models/IMovieSession";
import {dateToDDMMYYYY, dateToHHMM} from "../../utils/utils";
import {NgClass} from "@angular/common";

@Component({
  selector: 'app-movie-session-simplified',
  standalone: true,
  imports: [
    NgClass
  ],
  templateUrl: './movie-session-simplified.component.html',
  styleUrl: './movie-session-simplified.component.scss'
})
export class MovieSessionSimplifiedComponent implements OnInit {
  @Input() movieSession!: IMovieSession;

  time: string = '';
  date: string = '';

  ngOnInit() {
    let start = new Date(this.movieSession.startDate);
    let end = new Date(this.movieSession.endDate);
    this.time = `${dateToHHMM(start)} - ${dateToHHMM(end)}`;

    this.date = dateToDDMMYYYY(new Date(this.movieSession.startDate));
  }
}
