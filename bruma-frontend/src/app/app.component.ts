import { Component } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, CommonModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'bruma-frontend';

  // 1. Inyectamos el Router en el constructor
  constructor(private router: Router) {}

  // 2. Creamos la función que valida si estamos en el Login
  esRutaLogin(): boolean {
    return this.router.url === '/login' || this.router.url === '/';
  }
}
