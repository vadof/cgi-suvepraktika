import {Component, OnInit} from '@angular/core';
import {HttpService} from "../../services/http.service";

@Component({
  selector: 'app-purchase-history-page',
  standalone: true,
  imports: [],
  templateUrl: './purchase-history-page.component.html',
  styleUrl: './purchase-history-page.component.scss'
})
export class PurchaseHistoryPageComponent implements OnInit {



    constructor(private http: HttpService) {
    }

    ngOnInit(): void {

    }

}
