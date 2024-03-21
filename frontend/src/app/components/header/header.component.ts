import {Component, OnInit} from '@angular/core';
import {TokenStorageService} from "../../services/token-storage.service";
import {Router, RouterLink} from "@angular/router";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [
    RouterLink,
    NgIf
  ],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent implements OnInit {
  email: string | null = null;

  constructor(
    private tokenStorage: TokenStorageService,
    private router: Router
  ) {
  }

  logout() {
    this.tokenStorage.signOut();
    this.router.navigate(['/login']);
  }

  ngOnInit(): void {
    this.email = this.tokenStorage.getEmail();
  }

}
