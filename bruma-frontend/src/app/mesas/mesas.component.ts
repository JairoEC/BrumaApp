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
    capacidad: 2,
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
    this.mesaService.createMesa(this.nuevaMesa).subscribe({
      next: (respuesta) => {
        alert('Mesa creada con éxito');
        this.cargarMesas(); // Recargamos la lista para ver la mesa nueva
        // Limpiamos el formulario
        this.nuevaMesa = {
          numeroMesa: '',
          ubicacion: 'Salón Principal',
          capacidad: 2,
          estadoMesa: 1,
          estado: true,
        };
      },
      error: (err) => {
        console.error('Error al guardar la mesa:', err);
        alert('Hubo un error al guardar la mesa.');
      },
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
}
