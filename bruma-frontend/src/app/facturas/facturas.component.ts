import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FacturaService } from './factura.service';

@Component({
  selector: 'app-facturas',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './facturas.component.html',
  styleUrl: './facturas.component.css'
})
export class FacturasComponent implements OnInit {
  listaFacturas: any[] = [];

  constructor(private facturaService: FacturaService) {}

  ngOnInit(): void {
    this.cargarFacturas();
  }

  cargarFacturas(): void {
    this.facturaService.getFacturas().subscribe({
      next: (data) => this.listaFacturas = Array.isArray(data) ? data : [data],
      error: (err) => console.error('Error al cargar facturas:', err)
    });
  }
}
