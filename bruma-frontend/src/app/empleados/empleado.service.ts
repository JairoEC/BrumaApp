import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EmpleadoService {
  // Asegúrate de que esta sea la ruta exacta de tu Controller en Spring Boot
  private apiUrl = 'http://localhost:8080/api/empleados'; 

  constructor(private http: HttpClient) { }

  getEmpleados(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

  createEmpleado(empleado: any): Observable<any> {
    return this.http.post(this.apiUrl, empleado);
  }

  updateEmpleado(id: number, empleado: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/${id}`, empleado);
  }

  deleteEmpleado(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }
}