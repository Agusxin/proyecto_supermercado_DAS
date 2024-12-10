import { TestBed } from '@angular/core/testing';

import { SuperResourcesService } from './super-resources.service';

describe('SuperResourcesService', () => {
  let service: SuperResourcesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SuperResourcesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
