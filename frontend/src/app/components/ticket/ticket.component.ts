import {Component, Input, OnInit} from '@angular/core';
import {ITicket} from "../../models/ITicket";
import {dateToDDMMYYYY, dateToHHMM} from "../../utils/utils";
import {RouterLink} from "@angular/router";

@Component({
  selector: 'app-ticket',
  standalone: true,
  imports: [
    RouterLink
  ],
  templateUrl: './ticket.component.html',
  styleUrl: './ticket.component.scss'
})
export class TicketComponent implements OnInit {
  @Input() ticket!: ITicket

  time: string = '';
  date: string = '';

  ngOnInit() {
    let start = new Date(this.ticket.movieSession.startDate);
    let end = new Date(this.ticket.movieSession.endDate);
    this.time = `${dateToHHMM(start)} - ${dateToHHMM(end)}`;

    this.date = dateToDDMMYYYY(new Date(this.ticket.movieSession.startDate));
  }
}
