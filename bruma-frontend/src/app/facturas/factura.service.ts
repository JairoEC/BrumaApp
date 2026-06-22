import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FacturaService {
  private apiUrl = 'http://localhost:8085/factura';

  constructor(private http: HttpClient) {}

  getFacturas(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }
}
