import { ResolveFn } from '@angular/router';
import { Iproducto } from '../models/iproducto';
import { SuperResourcesService } from '../resources/super-resources.service';
import { inject } from '@angular/core';

export const productoProveedorResolverResolver: ResolveFn<Iproducto[]> = (route, state) => {
  const idProveedor = route.params["id_proveedor"];
  const service = inject(SuperResourcesService);
  return service.listarProductosProveedor( {id_proveedor: idProveedor});

};
