import { ResolveFn } from '@angular/router';
import { IdetallePedido } from '../models/idetalle-pedido';
import { SuperResourcesService } from '../resources/super-resources.service';
import { inject } from '@angular/core';

export const detallePedidoResolverResolver: ResolveFn<IdetallePedido[]> = (route, state) => {
  const idPedido = route.params['id_pedido'];
  const service = inject(SuperResourcesService);
  return service.detallePedido({id_pedido: idPedido});
};