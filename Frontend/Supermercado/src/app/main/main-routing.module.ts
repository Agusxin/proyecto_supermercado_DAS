import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MainComponent } from './main.component';
import { LoginComponent } from './pages/login/login.component';
import { PedidosComponent } from './pages/pedidos/pedidos.component';
import { pedidosResolverResolver } from './api/resolvers/pedidos-resolver.resolver';
import { ProveedoresComponent } from './pages/proveedores/proveedores.component';
import { proveedoresResolverResolver } from './api/resolvers/proveedores-resolver.resolver';
import { NuevoProveedorComponent } from './pages/nuevo-proveedor/nuevo-proveedor.component';
import { DetallePedidoComponent } from './pages/detalle-pedido/detalle-pedido.component';
import { detallePedidoResolverResolver } from './api/resolvers/detalle-pedido-resolver.resolver';
import { proveedorEfectividadResolverResolver } from './api/resolvers/proveedor-efectividad-resolver.resolver';
import { ProveedorEfectividadComponent } from './pages/proveedor-efectividad/proveedor-efectividad.component';
import { productoProveedorResolverResolver } from './api/resolvers/producto-proveedor-resolver.resolver';
import { ProductosProveedoresComponent } from './pages/productos-proveedores/productos-proveedores.component';

const routes: Routes = [
  { path: '', component: MainComponent, children: [
     {path: 'login', component: LoginComponent},
     {path: 'listarPedidos', resolve: { pedidos: pedidosResolverResolver} ,component: PedidosComponent},
     {path: 'listarProveedores', resolve: { proveedores: proveedoresResolverResolver}, component: ProveedoresComponent},
     {path: 'registrarProveedor', component: NuevoProveedorComponent},
     {path: 'detallePedido/:id_pedido', resolve: { detallePedido: detallePedidoResolverResolver}, component: DetallePedidoComponent},
     {path: 'proveedor-efectividad', resolve: { efectividadProveedor: proveedorEfectividadResolverResolver}, component: ProveedorEfectividadComponent },
     {path: 'productos-proveedor/:id_proveedor', resolve: { productosProveedor: productoProveedorResolverResolver}, component: ProductosProveedoresComponent}
  ] }
];


@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MainRoutingModule { }
