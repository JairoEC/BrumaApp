import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-pedidos',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './pedidos.component.html',
  styleUrl: './pedidos.component.css'
})
export class PedidosComponent implements OnInit {
  listaPedidos: any[] = [];
  listaProductos: any[] = [];
  pedidoSeleccionado: any = null;

  nuevoPedido: any = {
    clienteId: null,
    estado: 'PENDIENTE',
    detalles: []
  };

  nuevoDetalle: any = {
    productoId: null,
    nombreProducto: '',
    cantidad: 1,
    precioUnitario: 0,
    subtotal: 0
  };

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.cargarPedidos();
    this.cargarProductos();
  }

  cargarPedidos(): void {
    this.http.get<any[]>('http://localhost:8080/api/pedidos').subscribe({
      next: (data) => this.listaPedidos = data,
      error: (err) => console.error('Error al cargar pedidos:', err)
    });
  }

  cargarProductos(): void {
    this.http.get<any[]>('http://localhost:8080/api/productos').subscribe({
      next: (data) => this.listaProductos = data.filter(p => p.estado === true),
      error: (err) => console.error('Error al cargar productos:', err)
    });
  }

  onProductoSeleccionado(): void {
    const producto = this.listaProductos.find(p => p.id == this.nuevoDetalle.productoId);
    if (producto) {
      this.nuevoDetalle.nombreProducto = producto.nombre;
      this.nuevoDetalle.precioUnitario = producto.precio;
      this.nuevoDetalle.subtotal = producto.precio * this.nuevoDetalle.cantidad;
    }
  }

  onCantidadCambiada(): void {
    this.nuevoDetalle.subtotal = this.nuevoDetalle.precioUnitario * this.nuevoDetalle.cantidad;
  }

  agregarDetalle(): void {
    if (!this.nuevoDetalle.productoId || this.nuevoDetalle.cantidad < 1) {
      alert('Selecciona un producto y una cantidad válida.');
      return;
    }
    this.nuevoPedido.detalles.push({ ...this.nuevoDetalle });
    this.nuevoDetalle = { productoId: null, nombreProducto: '', cantidad: 1, precioUnitario: 0, subtotal: 0 };
  }

  quitarDetalle(index: number): void {
    this.nuevoPedido.detalles.splice(index, 1);
  }

  getTotalPedido(): number {
    return this.nuevoPedido.detalles.reduce((acc: number, d: any) => acc + d.subtotal, 0);
  }

  guardarPedido(): void {
    if (!this.nuevoPedido.clienteId) {
      alert('Ingresa el ID del cliente.');
      return;
    }
    if (this.nuevoPedido.detalles.length === 0) {
      alert('Agrega al menos un producto al pedido.');
      return;
    }
    this.http.post<any>('http://localhost:8080/api/pedidos', this.nuevoPedido).subscribe({
      next: () => {
        alert('Pedido generado con éxito!');
        this.cargarPedidos();
        this.nuevoPedido = { clienteId: null, estado: 'PENDIENTE', detalles: [] };
      },
      error: () => alert('Error al guardar el pedido.')
    });
  }

  prepararEditar(pedido: any): void {
    this.pedidoSeleccionado = { ...pedido };
  }

  actualizarEstado(): void {
    if (this.pedidoSeleccionado) {
      this.http.patch(`http://localhost:8080/api/pedidos/${this.pedidoSeleccionado.id}/estado?estado=${this.pedidoSeleccionado.estado}`, {}).subscribe({
        next: () => {
          this.cargarPedidos();
          this.pedidoSeleccionado = null;
        },
        error: () => alert('Error al actualizar el estado.')
      });
    }
  }

  eliminarPedido(id: number): void {
    if (confirm('¿Estás seguro de eliminar este pedido?')) {
      this.http.delete(`http://localhost:8080/api/pedidos/${id}`).subscribe({
        next: () => this.cargarPedidos(),
        error: (err) => console.error('Error al eliminar:', err)
      });
    }
  }

  getBadgeClass(estado: string): string {
    switch(estado) {
      case 'PENDIENTE': return 'bg-warning text-dark';
      case 'EN_PROCESO': return 'bg-info text-dark';
      case 'ENTREGADO': return 'bg-success';
      case 'CANCELADO': return 'bg-danger';
      default: return 'bg-secondary';
    }
  }
}
