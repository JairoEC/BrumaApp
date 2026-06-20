import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { EmpleadoService } from './empleado.service';

@Component({
  selector: 'app-empleados',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './empleados.component.html',
  styleUrl: './empleados.component.css',
})
export class EmpleadosComponent implements OnInit {
  listaEmpleados: any[] = [];
  empleadoSeleccionado: any = null;

  // Objeto temporal adaptado a tus columnas de Spring Boot
  nuevoEmpleado: any = {
    nombre: '',
    apellidoPaterno: '',
    apellidoMaterno: '',
    dni: '',
    cargo: 'Mozo', // Valor por defecto
    email: '',
    fechaIngreso: '',
    estado: true,
  };

  constructor(private empleadoService: EmpleadoService) {}

  ngOnInit(): void {
    this.cargarEmpleados();
  }

  cargarEmpleados(): void {
    this.empleadoService.getEmpleados().subscribe({
      next: (data) => {
        // Ahora sí filtrará correctamente los empleados activos
        this.listaEmpleados = data.filter((emp) => emp.estado === true);
      },
      error: (err) => console.error('Error al cargar empleados:', err),
    });
  }

  guardarEmpleado(): void {
    this.empleadoService.createEmpleado(this.nuevoEmpleado).subscribe({
      next: () => {
        alert('Empleado registrado con éxito');
        this.cargarEmpleados();
        // Limpiamos el formulario
        this.nuevoEmpleado = {
          nombre: '',
          apellidoPaterno: '',
          apellidoMaterno: '',
          dni: '',
          cargo: 'Mozo',
          email: '',
          fechaIngreso: '',
          estado: true,
        };
      },
      error: (err) => alert('Hubo un error al guardar el empleado.'),
    });
  }

  eliminarEmpleado(id: number): void {
    if (confirm('¿Estás seguro de eliminar a este empleado?')) {
      this.empleadoService.deleteEmpleado(id).subscribe({
        next: () => this.cargarEmpleados(),
        error: (err) => console.error('Error al eliminar:', err),
      });
    }
  }

  prepararEditar(empleado: any): void {
    this.empleadoSeleccionado = { ...empleado };
  }

  guardarEdicion(): void {
    if (this.empleadoSeleccionado) {
      this.empleadoService
        .updateEmpleado(this.empleadoSeleccionado.id, this.empleadoSeleccionado)
        .subscribe({
          next: () => {
            alert('Empleado actualizado con éxito');
            this.cargarEmpleados();
            this.empleadoSeleccionado = null;
          },
          error: (err) => alert('Hubo un error al actualizar el empleado.'),
        });
    }
  }
}
