import { Pipe, PipeTransform } from '@angular/core';
import { IPedido } from '../api/models/ipedido';

@Pipe({
  name: 'pedidosProveedor'
})
export class PedidosProveedorPipe implements PipeTransform {

  transform(pedidos: IPedido[], nombreProveedorFiltro: string): IPedido[] {
    
    if(!pedidos) return [];
    if(!nombreProveedorFiltro) return pedidos;

    nombreProveedorFiltro = nombreProveedorFiltro.toLowerCase();

    return pedidos.filter( pedido =>
      pedido.nombre_proveedor.toLowerCase().includes(nombreProveedorFiltro)
    );
  }
}
