import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms'; // 1. Importamos la herramienta de formularios
import { MesaService } from './mesa.service';

@Component({
  selector: 'app-mesas',
  standalone: true,
  imports: [CommonModule, FormsModule], // 2. La activamos aquí
  templateUrl: './mesas.component.html',
  styleUrl: './mesas.component.css',
})
export class MesasComponent implements OnInit {
  listaMesas: any[] = [];

  // 3. Objeto temporal para guardar los datos del formulario
  nuevaMesa: any = {
    numeroMesa: '',
    ubicacion: 'Salón Principal',
    capacidad: '',
    estadoMesa: 1, // 1 = Libre por defecto
    estado: true,
  };

  constructor(private mesaService: MesaService) {}

  ngOnInit(): void {
    this.cargarMesas(); // Separamos la carga en un método para reutilizarlo
  }

  cargarMesas(): void {
    this.mesaService.getMesas().subscribe({
      next: (data) => {
        // Magia aquí: Solo guardamos en la lista las mesas con estado true (1)
        this.listaMesas = data.filter((mesa) => mesa.estado === true);
      },
      error: (err) => console.error('Error al cargar:', err),
    });
  }

  eliminarMesa(id: number): void {
    if (confirm('¿Estás seguro de que deseas eliminar esta mesa?')) {
      this.mesaService.deleteMesa(id).subscribe({
        next: () =>
          (this.listaMesas = this.listaMesas.filter((mesa) => mesa.id !== id)),
        error: (err) => console.error('Error al eliminar:', err),
      });
    }
  }

  // 4. Método para enviar el formulario al backend
  guardarMesa(): void {
    // 1. Validación de Mesa Duplicada
    const numeroExiste = this.listaMesas.some(
      (mesa) => mesa.numeroMesa === this.nuevaMesa.numeroMesa,
    );

    if (numeroExiste) {
      alert(
        `La Mesa ${this.nuevaMesa.numeroMesa} ya se encuentra registrada en el local. Por favor, asigna un número diferente.`,
      );
      return;
    }

    // 2. Si el número está libre, procedemos a guardar en el backend
    this.mesaService.createMesa(this.nuevaMesa).subscribe({
      next: () => {
        this.cargarMesas();
        // Limpiamos el formulario para la siguiente mesa
        this.nuevaMesa = {
          numeroMesa: null,
          capacidad: null,
          ubicacion: 'Salón Principal',
          estadoMesa: 1,
          estado: true,
        };
      },
      error: (err) => alert('Error al guardar la mesa. Verifica la conexión.'),
    });
  }

  // 1. Variable para almacenar la mesa que se va a editar
  mesaSeleccionada: any = null;

  // 2. Copia los datos de la mesa de la tarjeta al formulario de edición
  prepararEditar(mesa: any): void {
    // Usamos { ...mesa } para clonar el objeto.
    // Así, si el usuario escribe algo y luego cancela, la tarjeta original no se modificará por error.
    this.mesaSeleccionada = { ...mesa };
  }

  // 3. Envía los cambios al backend
  guardarEdicion(): void {
    if (this.mesaSeleccionada) {
      this.mesaService
        .actualizarMesa(this.mesaSeleccionada.id, this.mesaSeleccionada)
        .subscribe({
          next: () => {
            alert('Mesa actualizada con éxito');
            this.cargarMesas(); // Recargamos la lista para ver los cambios reflejados
            this.mesaSeleccionada = null; // Limpiamos la variable
          },
          error: (err) => {
            console.error('Error al actualizar la mesa:', err);
            alert('Hubo un error al actualizar la mesa.');
          },
        });
    }
  }

  obtenerTextoEstado(estadoMesa: number): string {
    switch (estadoMesa) {
      case 1:
        return 'Disponible';
      case 2:
        return 'Ocupada';
      case 3:
        return 'Mantenimiento';
      default:
        return 'Desconocido';
    }
  }

  obtenerColorEstado(estadoMesa: number): string {
    switch (estadoMesa) {
      case 1:
        return 'bg-success text-white border-success';
      case 2:
        return 'bg-danger text-white border-danger';
      case 3:
        return 'bg-warning text-dark border-warning';
      default:
        return 'bg-secondary text-white';
    }
  }
}
