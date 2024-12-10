import { ResolveFn } from '@angular/router';
import { IproveedorEfectividad } from '../models/iproveedor-efectividad';
import { inject } from '@angular/core';
import { SuperResourcesService } from '../resources/super-resources.service';

export const proveedorEfectividadResolverResolver: ResolveFn<IproveedorEfectividad[]> = (route, state) => {
  const service = inject(SuperResourcesService);
  return service.efectividadProveedor();
};

