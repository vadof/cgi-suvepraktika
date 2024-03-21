import {IMovie} from "./IMovie";

export interface IMovieSession {
  id: number,
  movie: IMovie,
  seatsAvailable: number,
  startDate: Date,
  endDate: Date
}
