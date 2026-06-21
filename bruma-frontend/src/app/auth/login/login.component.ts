import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  credentials = { username: '', password: '' };
  errorMsg = '';

  constructor(private http: HttpClient, private router: Router) {}

  login(): void {
    this.http.post<any>('http://localhost:8080/api/auth/login', this.credentials).subscribe({
      next: (res) => {
        localStorage.setItem('token', res.token);
        this.router.navigate(['/mesas']);
      },
      error: () => { this.errorMsg = 'Usuario o contraseña incorrectos'; }
    });
  }
}
