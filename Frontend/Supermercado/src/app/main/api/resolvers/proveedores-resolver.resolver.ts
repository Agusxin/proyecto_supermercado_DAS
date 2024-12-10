import { ResolveFn } from '@angular/router';
import { SuperResourcesService } from '../resources/super-resources.service';
import { inject } from '@angular/core';
import { IProveedor } from '../models/iproveedor';

export const proveedoresResolverResolver: ResolveFn<IProveedor[]> = (route, state) => {
  const service = inject(SuperResourcesService);
  return service.listarProveedores();
};
