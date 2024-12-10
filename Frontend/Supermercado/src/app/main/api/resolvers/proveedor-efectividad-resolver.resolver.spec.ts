import { TestBed } from '@angular/core/testing';
import { ResolveFn } from '@angular/router';

import { proveedorEfectividadResolverResolver } from './proveedor-efectividad-resolver.resolver';

describe('proveedorEfectividadResolverResolver', () => {
  const executeResolver: ResolveFn<boolean> = (...resolverParameters) => 
      TestBed.runInInjectionContext(() => proveedorEfectividadResolverResolver(...resolverParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeResolver).toBeTruthy();
  });
});
