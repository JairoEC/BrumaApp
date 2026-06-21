import { Routes } from '@angular/router';
import { MesasComponent } from './mesas/mesas.component';
import { ProductosComponent } from './productos/productos.component';
import { EmpleadosComponent } from './empleados/empleados.component';
import { LoginComponent } from './auth/login/login.component';
import { ClientesComponent } from './clientes/clientes.component';
import { PedidosComponent } from './pedidos/pedidos.component';
import { FacturasComponent } from './facturas/facturas.component';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'mesas', component: MesasComponent },
  { path: 'productos', component: ProductosComponent },
  { path: 'empleados', component: EmpleadosComponent },
  { path: 'clientes', component: ClientesComponent },
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'pedidos', component: PedidosComponent },
  { path: 'facturas', component: FacturasComponent }
];
