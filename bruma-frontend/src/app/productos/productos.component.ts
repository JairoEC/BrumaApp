import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ProductoService } from './producto.service';

@Component({
  selector: 'app-productos',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './productos.component.html',
  styleUrl: './productos.component.css'
})
export class ProductosComponent implements OnInit {
  listaProductos: any[] = [];
  productoSeleccionado: any = null;
  
  // Estructura exacta basada en tu base de datos
  nuevoProducto: any = {
    nombre: '',
    categoria: 'Bebidas Calientes', // Valor por defecto
    descripcion: '',
    precio: null,
    stock: null,
    estado: true
  };

  constructor(private productoService: ProductoService) {}

  ngOnInit(): void {
    this.cargarProductos();
  }

  cargarProductos(): void {
    this.productoService.getProductos().subscribe({
      next: (data) => {
        // Usamos la misma lógica infalible de borrado lógico
        this.listaProductos = data.filter(prod => prod.estado === true || prod.estado === 1);
      },
      error: (err) => console.error('Error al cargar productos:', err)
    });
  }

  guardarProducto(): void {
    this.productoService.createProducto(this.nuevoProducto).subscribe({
      next: () => {
        alert('Producto registrado con éxito');
        this.cargarProductos();
        // Limpiamos el formulario
        this.nuevoProducto = { nombre: '', categoria: 'Bebidas Calientes', descripcion: '', precio: null, stock: null, estado: true };
      },
      error: (err) => alert('Error al guardar el producto. Verifica los datos.')
    });
  }

  eliminarProducto(id: number): void {
    if (confirm('¿Estás seguro de eliminar este producto del menú?')) {
      this.productoService.deleteProducto(id).subscribe({
        next: () => this.cargarProductos(),
        error: (err) => console.error('Error al eliminar producto:', err)
      });
    }
  }

  prepararEditar(producto: any): void {
    this.productoSeleccionado = { ...producto };
  }

  guardarEdicion(): void {
    if (this.productoSeleccionado) {
      this.productoService.updateProducto(this.productoSeleccionado.id, this.productoSeleccionado).subscribe({
        next: () => {
          alert('Producto actualizado con éxito');
          this.cargarProductos();
          this.productoSeleccionado = null;
        },
        error: (err) => alert('Error al actualizar el producto.')
      });
    }
  }
}