import {IMovieSession} from "./IMovieSession";

export interface ITicket {
  id: number,
  movieSession: IMovieSession
  rowNumber: number,
  seatNumber: number
}
