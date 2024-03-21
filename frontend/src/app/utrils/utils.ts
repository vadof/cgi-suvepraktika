import {IGenre} from "../models/IGenre";

export function genresToString(genres: IGenre[]): string {
  const totalGenres = genres.length;
  let str = '';
  genres.forEach((g, index) => {
    str += g.name;
    if (index < totalGenres - 1) {
      str += ', ';
    }
  });
  return str;
}

export function dateToHHMM(date: Date): string {
  let startHours = ('0' + date.getHours()).slice(-2);
  let startMinutes = ('0' + date.getMinutes()).slice(-2);
  return `${startHours}:${startMinutes}`;
}
