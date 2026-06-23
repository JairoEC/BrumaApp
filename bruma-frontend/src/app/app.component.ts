import { Component } from '@angular/core';
import { Router, RouterOutlet, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, RouterModule, CommonModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'bruma-frontend';

  // Inyectamos el servicio Router en el constructor para escuchar las rutas
  constructor(private router: Router) {}

  // Función que verifica si el usuario está en la pantalla de login
  esRutaLogin(): boolean {
    return this.router.url.includes('/login');
  }
}
