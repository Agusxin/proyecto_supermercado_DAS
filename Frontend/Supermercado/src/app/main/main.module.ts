import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MainRoutingModule } from './main-routing.module';
import { MainComponent } from './main.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SplashComponent } from './splash/splash.component';
import { LoginComponent } from './pages/login/login.component';
import { PedidosComponent } from './pages/pedidos/pedidos.component';
import { ProveedoresComponent } from './pages/proveedores/proveedores.component';
import { SuperResourcesService } from './api/resources/super-resources.service';
import { NuevoProveedorComponent } from './pages/nuevo-proveedor/nuevo-proveedor.component';
import { DetallePedidoComponent } from './pages/detalle-pedido/detalle-pedido.component';
import { ModalCalificarPedidoComponent } from './pages/modal-calificar-pedido/modal-calificar-pedido.component';
import { PedidosProveedorPipe } from './pipes/pedidos-proveedor.pipe';
import { ProveedorEfectividadComponent } from './pages/proveedor-efectividad/proveedor-efectividad.component';
import { ProductosProveedoresComponent } from './pages/productos-proveedores/productos-proveedores.component';


@NgModule({
  declarations: [
    MainComponent,
    SplashComponent,
    LoginComponent,
    PedidosComponent,
    ProveedoresComponent,
    NuevoProveedorComponent,
    DetallePedidoComponent,
    ModalCalificarPedidoComponent,
    PedidosProveedorPipe,
    ProveedorEfectividadComponent,
    ProductosProveedoresComponent
  ],
  imports: [
    CommonModule,
    MainRoutingModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [
    SuperResourcesService
  ]
})
export class MainModule { }
