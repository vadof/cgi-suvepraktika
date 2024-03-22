import {Component, OnInit} from '@angular/core';
import {HttpService} from "../../services/http.service";
import {HeaderComponent} from "../../components/header/header.component";
import {ITicket} from "../../models/ITicket";
import {NgClass, NgForOf, NgIf} from "@angular/common";
import {TicketComponent} from "../../components/ticket/ticket.component";

@Component({
  selector: 'app-purchase-history-page',
  standalone: true,
  imports: [
    HeaderComponent,
    NgForOf,
    TicketComponent,
    NgIf,
    NgClass
  ],
  templateUrl: './purchase-history-page.component.html',
  styleUrl: './purchase-history-page.component.scss'
})
export class PurchaseHistoryPageComponent implements OnInit {

  tickets: ITicket[] = []
  totalPages: number = 0;
  pages: number[] = [];
  currentPage = 0;

  constructor(private http: HttpService) {
  }

  ngOnInit(): void {
    this.selectPage(this.currentPage);
  }

  selectPage(page: number) {
    this.http.sendGetRequest(`/api/v1/tickets?sortDirection=DESC&size=10&page=${page}`).subscribe(res => {
      this.tickets = res.data;
      this.totalPages = res.totalPages;
      this.currentPage = res.page;
      this.pages = Array.from({length: this.totalPages}, (_, i) => i);
    });
  }

}
