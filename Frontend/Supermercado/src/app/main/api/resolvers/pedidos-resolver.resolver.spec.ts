import { TestBed } from '@angular/core/testing';
import { ResolveFn } from '@angular/router';

import { pedidosResolverResolver } from './pedidos-resolver.resolver';

describe('pedidosResolverResolver', () => {
  const executeResolver: ResolveFn<boolean> = (...resolverParameters) => 
      TestBed.runInInjectionContext(() => pedidosResolverResolver(...resolverParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeResolver).toBeTruthy();
  });
});
