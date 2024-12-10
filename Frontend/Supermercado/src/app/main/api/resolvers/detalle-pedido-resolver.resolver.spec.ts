import { TestBed } from '@angular/core/testing';
import { ResolveFn } from '@angular/router';

import { detallePedidoResolverResolver } from './detalle-pedido-resolver.resolver';

describe('detallePedidoResolverResolver', () => {
  const executeResolver: ResolveFn<boolean> = (...resolverParameters) => 
      TestBed.runInInjectionContext(() => detallePedidoResolverResolver(...resolverParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeResolver).toBeTruthy();
  });
});
