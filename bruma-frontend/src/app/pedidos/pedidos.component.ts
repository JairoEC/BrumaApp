import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { HttpParams } from '@angular/common/http';

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
  listaClientes: any[] = []; // 🔥 Agregado para almacenar los clientes
  pedidoSeleccionado: any = null;
  listaDetalles: any[]=[];
  listaMesas: any[]=[];
  listaMeseros: any[]=[];

  nuevoPedido: any = {
    clienteId: null,
    estado: 'PENDIENTE',
    mesaId:null,
    meseroId:null,
    detalles: []
  };

  nuevoDetalle: any = {
    pedidoId:null,
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
    this.cargarClientes(); // 🔥 Cargamos los clientes al iniciar el componente
    this.cargarMesas();
    this.cargarMeseros();
  }

  cargarPedidos(): void {
    this.http.get<any[]>('http://localhost:8084/api/pedidos').subscribe({
      next: (data) => this.listaPedidos = data,
      error: (err) => console.error('Error al cargar pedidos:', err)
    });
  }

  cargarProductos(): void {
    this.http.get<any[]>('http://localhost:8083/api/productos').subscribe({
      next: (data) => this.listaProductos = data.filter(p => p.estado === true),
      error: (err) => console.error('Error al cargar productos:', err)
    });
  }

  // 🔥 Nuevo método para traer los clientes desde el backend
  cargarClientes(): void {
    this.http.get<any[]>('http://localhost:8084/api/clientes').subscribe({
      next: (data) => this.listaClientes = data,
      error: (err) => console.error('Error al cargar clientes:', err)
    });
  }
  cargarMesas(): void {
    this.http.get<any[]>('http://localhost:8083/api/mesas')
      .subscribe({
        next: (data) => {
          this.listaMesas = data;
        },
        error: (err) => console.error('Error cargando mesas', err)
      });
  }

  cargarMeseros(): void {
    this.http.get<any[]>('http://localhost:8083/api/empleados')
      .subscribe({
        next: (data) => {
          this.listaMeseros = data;
        },
        error: (err) => console.error('Error cargando meseros', err)
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

  generarPedidoDetallePedido():void{
    this.guardarPedido()
    this.guardarDetallePedido()
  }

  guardarPedido(): void {
    if (!this.nuevoPedido.clienteId) {
      alert('Por favor, selecciona un cliente de la lista.');
      return;
    }
    if (this.nuevoPedido.detalles.length === 0) {
      alert('Agrega al menos un producto al pedido.');
      return;
    }

    // 1. Guardamos el pedido principal
    this.http.post<any>('http://localhost:8084/api/pedidos', this.nuevoPedido).subscribe({
      next: (pedidoCreado) => {
        // pedidoCreado debería ser el objeto que viene del backend, ej: { id: 10, ... }
        const idDelPedido = pedidoCreado.id;

        // 2. Asignamos el ID a los detalles antes de enviarlos
        const listaParaEnviar = this.nuevoPedido.detalles.map((d: any) => ({
          ...d,
          pedidoId: idDelPedido
        }));

        // 3. Enviamos los detalles
        this.http.post<any[]>('http://localhost:8084/api/detalle-pedido', listaParaEnviar).subscribe({
          next: () => {
            alert('Pedido y detalles guardados con éxito!');
            this.cargarPedidos();
            this.nuevoPedido = { clienteId: null, estado: 'PENDIENTE', detalles: [] };
          },
          error: (err) => {
            console.error('Error al guardar detalles:', err);
            alert('Error al guardar los detalles del pedido.');
          }
        });
      },
      error: (err) => {
        console.error('Error al guardar pedido:', err);
        alert('Error al crear el pedido principal.');
      }
    });
  }

  guardarDetallePedido():void{
    // Asegúrate de enviar solo la lista, no el objeto que contiene la lista
    const listaParaEnviar = this.nuevoPedido.detalles;
    this.http.post<any[]>('http://localhost:8084/api/detalle-pedido', listaParaEnviar)
    .subscribe({
      next: (response) => {
        alert('Pedido generado con éxito!');
        this.cargarPedidos();
        // Limpiamos el objeto principal y su lista de detalles
        this.nuevoPedido.detalles = [];
      },
      error: (err) => {
        console.error('Error detectado:', err);
        alert('Error al guardar: verifica que los datos sean correctos.');
      }
    });
  }

  prepararEditar(pedido: any): void {
    this.pedidoSeleccionado = { ...pedido };
  }

  actualizarEstado(): void {
    if (this.pedidoSeleccionado) {
      const params = new HttpParams().set('estado', this.pedidoSeleccionado.estado);

      this.http.patch<any>(
        `http://localhost:8084/api/pedidos/${this.pedidoSeleccionado.id}/estado`,
        null,
        { params }
      ).subscribe({
        next: () => {
          alert('Estado actualizado con éxito!');
          this.cargarPedidos();
          this.pedidoSeleccionado = null;
        },
        error: (err) => {
          console.error('Error detallado de la actualización:', err);
          alert('Error al actualizar el estado.');
        }
      });
    }
  }

  eliminarPedido(id: number): void {
    if (confirm('¿Estás seguro de eliminar este pedido?')) {
      this.http.delete(`http://localhost:8084/api/pedidos/${id}`).subscribe({
        next: () => this.cargarPedidos(),
        error: (err) => console.error('Error al eliminar:', err)
      });
    }
  }

  enviarFactura(id: number): void {
    this.http.get(`http://localhost:8084/api/pedidos/enviar-comprobante/${id}`).subscribe({
      next: () => {
        alert('Factura enviada al correo con éxito');
        this.cargarPedidos();
      },
      error: (err) => {
        console.error('Error al enviar correo:', err);
        alert('No se pudo enviar la factura al correo');
      }
    });
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
