import {Component, Input, OnInit} from '@angular/core';
import {IMovie} from "../../models/IMovie";
import {Router} from "@angular/router";

@Component({
  selector: 'app-movie-item',
  standalone: true,
  imports: [],
  templateUrl: './movie-item.component.html',
  styleUrl: './movie-item.component.scss'
})
export class MovieItemComponent implements OnInit {
  @Input() movie!: IMovie;

  genres: string = '';

  constructor(private router: Router) {
  }

  ngOnInit(): void {
    const totalGenres = this.movie.genres.length;
    this.movie.genres.forEach((g, index) => {
      this.genres += g.name;
      if (index < totalGenres - 1) {
        this.genres += ', ';
      }
    });
  }

  redirect() {
    this.router.navigate([`/movies/${this.movie.id}`]);
  }
}
