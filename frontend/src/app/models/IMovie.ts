import {IGenre} from "./IGenre";

export interface IMovie {
  id: number,
  adult: boolean,
  title: string,
  genres: IGenre[],
  overview: string,
  original_language: string,
  release_date: Date
  rating: number,
  poster_path: string,
  runtime: number
}
