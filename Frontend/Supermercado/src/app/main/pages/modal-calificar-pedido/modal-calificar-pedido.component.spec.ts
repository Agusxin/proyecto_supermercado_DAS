import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalCalificarPedidoComponent } from './modal-calificar-pedido.component';

describe('ModalCalificarPedidoComponent', () => {
  let component: ModalCalificarPedidoComponent;
  let fixture: ComponentFixture<ModalCalificarPedidoComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ModalCalificarPedidoComponent]
    });
    fixture = TestBed.createComponent(ModalCalificarPedidoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
