import {Component, Input, OnInit} from '@angular/core';
import {IMovie} from "../../models/IMovie";
import {Router} from "@angular/router";
import {NgOptimizedImage} from "@angular/common";
import {genresToString} from "../../utrils/utils";

@Component({
  selector: 'app-movie-item',
  standalone: true,
  imports: [
    NgOptimizedImage
  ],
  templateUrl: './movie-item.component.html',
  styleUrl: './movie-item.component.scss'
})
export class MovieItemComponent implements OnInit {
  @Input() movie!: IMovie;

  genres: string = '';

  constructor(private router: Router) {
  }

  ngOnInit(): void {
    this.genres = genresToString(this.movie.genres);
  }

  redirect() {
    this.router.navigate([`/movies/${this.movie.id}`]);
  }
}
