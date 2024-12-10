import { TestBed } from '@angular/core/testing';
import { ResolveFn } from '@angular/router';

import { proveedoresResolverResolver } from './proveedores-resolver.resolver';

describe('proveedoresResolverResolver', () => {
  const executeResolver: ResolveFn<boolean> = (...resolverParameters) => 
      TestBed.runInInjectionContext(() => proveedoresResolverResolver(...resolverParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeResolver).toBeTruthy();
  });
});
