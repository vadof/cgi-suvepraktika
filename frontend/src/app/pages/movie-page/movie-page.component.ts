import {Component, OnInit} from '@angular/core';
import {IMovie} from "../../models/IMovie";
import {HttpService} from "../../services/http.service";
import {ActivatedRoute, Router} from "@angular/router";
import {IMovieSession} from "../../models/IMovieSession";
import {HeaderComponent} from "../../components/header/header.component";
import {NgForOf, NgIf, NgOptimizedImage} from "@angular/common";
import {genresToString} from "../../utils/utils";
import {
  MovieSessionSimplifiedComponent
} from "../../components/movie-session-simplified/movie-session-simplified.component";

@Component({
  selector: 'app-movie-page',
  standalone: true,
  imports: [
    HeaderComponent,
    NgOptimizedImage,
    NgIf,
    NgForOf,
    MovieSessionSimplifiedComponent
  ],
  templateUrl: './movie-page.component.html',
  styleUrl: './movie-page.component.scss'
})
export class MoviePageComponent implements OnInit {
  movie!: IMovie;

  movieSessions: IMovieSession[] = [];
  genres: string = '';

  constructor(private http: HttpService,
              private router: Router,
              private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const id = +params.get('id')!;
      this.http.sendGetRequest(`/api/v1/movies/${id}`).subscribe(res => {

        this.movie = res as IMovie;
        this.getAllMovieSessions();
        this.genres = genresToString(this.movie.genres);

      }, err => {
        this.router.navigate(['']);
      })
    })
  }

  private getAllMovieSessions() {
    this.http.sendGetRequest(`/api/v1/movie-sessions/movie/${this.movie.id}`).subscribe(res => {
      this.movieSessions = res as IMovieSession[];
    })
  }
}
