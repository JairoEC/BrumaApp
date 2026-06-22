import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class MesaService {
  // Apuntamos al API Gateway (reemplaza el puerto si tu gateway usa otro, ej: 8090 u 8080)
  // Y la ruta exacta que configuraron en el Gateway para el microservicio de atención
  private apiUrl = 'http://localhost:8083/api/mesas';

  constructor(private http: HttpClient) {}

  // Método para obtener todas las mesas
  getMesas(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

  // Eliminar mesa
  deleteMesa(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }

  // Crear mesa
  createMesa(mesaData: any): Observable<any> {
    return this.http.post(this.apiUrl, mesaData);
  }

  // Editar mesa
  // Método para actualizar una mesa existente (PUT)
  actualizarMesa(id: number, mesa: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/${id}`, mesa);
  }
}
