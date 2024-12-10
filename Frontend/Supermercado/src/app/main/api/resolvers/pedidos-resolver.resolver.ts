import { inject } from '@angular/core';
import { ResolveFn } from '@angular/router';
import { SuperResourcesService } from '../resources/super-resources.service';
import { IPedido } from '../models/ipedido';

export const pedidosResolverResolver: ResolveFn<IPedido[]> = (route, state) => {
  const service = inject(SuperResourcesService);
  return service.listarPedidos();
};
