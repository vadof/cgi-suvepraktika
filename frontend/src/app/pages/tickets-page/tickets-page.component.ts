import {Component, OnInit} from '@angular/core';
import {HeaderComponent} from "../../components/header/header.component";
import {ActivatedRoute, Router} from "@angular/router";
import {HttpService} from "../../services/http.service";
import {IMovieSession} from "../../models/IMovieSession";
import {dateToDDMMYYYY, dateToHHMM} from "../../utils/utils";
import {NgClass, NgForOf, NgIf} from "@angular/common";

@Component({
  selector: 'app-tickets-page',
  standalone: true,
  imports: [
    HeaderComponent,
    NgForOf,
    NgClass,
    NgIf
  ],
  templateUrl: './tickets-page.component.html',
  styleUrl: './tickets-page.component.scss'
})
export class TicketsPageComponent implements OnInit {

  movieSession!: IMovieSession;
  ticketAmount: number = 0;
  time: string = '';
  date: string = '';

  showSeats: boolean = false;
  seats: number[][] = [];
  selectedSeats = new Map<string, number>();

  errorMessage = '';

  constructor(private router: Router,
              private http: HttpService,
              private route: ActivatedRoute)
  {}


  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const id = +params.get('id')!;
      this.http.sendGetRequest(`/api/v1/movie-sessions/${id}`).subscribe(res => {
        this.movieSession = res as IMovieSession

        let startDate = new Date(this.movieSession.startDate);
        let endDate = new Date(this.movieSession.endDate);
        this.time = `${dateToHHMM(startDate)} - ${dateToHHMM(endDate)}`;
        this.date = dateToDDMMYYYY(startDate);
      }, err => {
        this.router.navigate(['']);
      })
    })
  }

  addTickets(amount: number) {
    this.showSeats = false;
    let value = this.ticketAmount + amount;
    if (value >= 0 || value <= this.movieSession.seatsAvailable) {
      this.ticketAmount = value;
    }
  }

  getSeats() {
    let url = `/api/v1/movie-sessions/seats?movieSessionId=${this.movieSession.id}&people=${this.ticketAmount}`;
    this.http.sendGetRequest(url).subscribe(res => {
      this.seats = res.seats;
      this.selectedSeats.clear();
      this.showSeats = true;
    }, err => {
      console.log(err)
    })
  }

  chooseSeat(row: number, seat: number) {
    let value = this.seats[row][seat];
    let key = `${row}-${seat}`;
    if ((value === 0 || value === 2) && this.selectedSeats.size < this.ticketAmount) {
      this.selectedSeats.set(key, value);
      this.seats[row][seat] = 3;
    } else if (value === 3) {
      let was = this.selectedSeats.get(key);
      this.seats[row][seat] = was!;
      this.selectedSeats.delete(key);
    }
  }

  confirm() {
    let url = `/api/v1/movie-sessions/${this.movieSession.id}/buy-tickets`;
    this.http.sendPostRequest(url, this.getTickets()).subscribe(res => {
      this.router.navigate(['my-tickets']);
    }, err => {
      this.errorMessage = err.error;
    });
  }

  private getTickets() {
    let tickets = [];
    for (let key of this.selectedSeats.keys()) {
      let rowSeat = key.split('-');
      tickets.push({
        rowNumber: +rowSeat[0] + 1,
        seatNumber: +rowSeat[1] + 1
      })
    }

    return tickets;
  }
}
