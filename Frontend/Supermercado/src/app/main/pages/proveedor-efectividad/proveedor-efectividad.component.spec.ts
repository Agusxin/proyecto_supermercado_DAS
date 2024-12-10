import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProveedorEfectividadComponent } from './proveedor-efectividad.component';

describe('ProveedorEfectividadComponent', () => {
  let component: ProveedorEfectividadComponent;
  let fixture: ComponentFixture<ProveedorEfectividadComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProveedorEfectividadComponent]
    });
    fixture = TestBed.createComponent(ProveedorEfectividadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
