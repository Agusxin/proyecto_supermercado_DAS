import { TestBed } from '@angular/core/testing';
import { ResolveFn } from '@angular/router';

import { productoProveedorResolverResolver } from './producto-proveedor-resolver.resolver';
import { Iproducto } from '../models/iproducto';

describe('productoProveedorResolverResolver', () => {
  const executeResolver: ResolveFn<Iproducto[]> = (...resolverParameters) => 
      TestBed.runInInjectionContext(() => productoProveedorResolverResolver(...resolverParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeResolver).toBeTruthy();
  });
});
