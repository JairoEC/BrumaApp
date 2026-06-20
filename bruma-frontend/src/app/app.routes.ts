import { Routes } from '@angular/router';
import { MesasComponent } from './mesas/mesas.component';
import { ProductosComponent } from './productos/productos.component';
import { EmpleadosComponent } from './empleados/empleados.component';

export const routes: Routes = [
  { path: 'mesas', component: MesasComponent },
  { path: 'productos', component: ProductosComponent },
  { path: 'empleados', component: EmpleadosComponent },
  // Si la ruta está vacía, redirecciona a mesas por defecto
  { path: '', redirectTo: '/mesas', pathMatch: 'full' }
];