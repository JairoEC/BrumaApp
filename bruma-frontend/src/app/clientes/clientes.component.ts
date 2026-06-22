import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ClienteService } from './cliente.service';

@Component({
  selector: 'app-clientes',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './clientes.component.html',
  styleUrl: './clientes.component.css'
})
export class ClientesComponent implements OnInit {
  listaClientes: any[] = [];
  clienteSeleccionado: any = null;

  nuevoCliente: any = {
    nombre: '',
    apellido: '',
    email: '',
    telefono: '',
    direccion: ''
  };

  constructor(private clienteService: ClienteService) {}

  ngOnInit(): void {
    this.cargarClientes();
  }

  cargarClientes(): void {
    this.clienteService.getClientes().subscribe({
      next: (data) => this.listaClientes = data,
      error: (err) => console.error('Error al cargar clientes:', err)
    });
  }

  guardarCliente(): void {
    this.clienteService.createCliente(this.nuevoCliente).subscribe({
      next: () => {
        this.cargarClientes();
        this.nuevoCliente = { nombre: '', apellido: '', email: '', telefono: '', direccion: '' };
      },
      error: () => alert('Error al guardar el cliente.')
    });
  }

  prepararEditar(cliente: any): void {
    this.clienteSeleccionado = { ...cliente };
    console.log('Cliente cargado en el modal de edición:', this.clienteSeleccionado);
  }

  guardarEdicion(): void {
    if (this.clienteSeleccionado) {
      const idAEnviar = this.clienteSeleccionado.id || this.clienteSeleccionado.idCliente;

      if (!idAEnviar) {
        alert('Error interno: No se pudo recuperar el ID del cliente seleccionado.');
        console.error('El objeto clienteSeleccionado no contiene un ID válido:', this.clienteSeleccionado);
        return;
      }

      this.clienteService.updateCliente(idAEnviar, this.clienteSeleccionado).subscribe({
        next: () => {
          alert('Cliente actualizado con éxito!');
          this.cargarClientes();
          this.clienteSeleccionado = null;
        },
        error: (err) => {
          console.error('Error del servidor al intentar actualizar:', err);
          alert('Error al actualizar el cliente. Revisa la consola para más detalles.');
        }
      });
    }
  }

  eliminarCliente(id: number): void {
    if (confirm('¿Estás seguro de eliminar este cliente?')) {
      this.clienteService.deleteCliente(id).subscribe({
        next: () => this.cargarClientes(),
        error: (err) => console.error('Error al eliminar:', err)
      });
    }
  }
}
