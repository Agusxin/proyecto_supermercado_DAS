import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { SuperResourcesService } from '../../api/resources/super-resources.service';
import { IlistarvaloresEscalaProveedor } from '../../api/models/ilistarvalores-escala-proveedor';

@Component({
  selector: 'app-modal-calificar-pedido',
  templateUrl: './modal-calificar-pedido.component.html',
  styleUrls: ['./modal-calificar-pedido.component.css']
})
export class ModalCalificarPedidoComponent implements OnInit {

  @Input() id_pedido!: number;
  @Input() id_proveedor!: number;
 

  listarValoresPonderadosProveedor: IlistarvaloresEscalaProveedor[] = [];
  calificacionSeleccionada!: IlistarvaloresEscalaProveedor;

  
  constructor( private activeModal: NgbActiveModal, private superResourcesService: SuperResourcesService) {}

  ngOnInit(): void {
    console.log('ID Pedido:', this.id_pedido);  // Verificar
    console.log('ID Proveedor:', this.id_proveedor); // Verificar
    this.cargarValoresEscalaProveedor();
  }

  cargarValoresEscalaProveedor(): void {

    this.superResourcesService.listarValorPonderadoProveedor( { id_proveedor: this.id_proveedor} ).subscribe({
      next: (data: IlistarvaloresEscalaProveedor[]) => {
        this.listarValoresPonderadosProveedor = data
        console.log(this.listarValoresPonderadosProveedor);
      },
      error: (err) => {
        console.error('Error al cargar las opciones de calificación', err);
      }
    });
  }

  setCalificacion(opcion: IlistarvaloresEscalaProveedor): void {
    this.calificacionSeleccionada = opcion;
  }

  enviarCalificacion(): void {
    if (this.calificacionSeleccionada) {
      const calificacionRequest = {
        id_pedido: this.id_pedido,
        valor_escala: this.calificacionSeleccionada.valor_escala,
        descripcion_valor: this.calificacionSeleccionada.descripcion_valor,
        valor_ponderado: this.calificacionSeleccionada.valor_ponderado
      };

      console.log(calificacionRequest);
      this.superResourcesService.calificarPedido(calificacionRequest).subscribe({
        next: (response) => {
          console.log('Calificación enviada correctamente', response);
          this.superResourcesService.estadoPostCalificacion( { id_pedido: this.id_pedido } ).subscribe({
            next: (estadoResponse) => {
              console.log("Cambio de estado hecho correctamente", response);
              alert(estadoResponse);
              this.activeModal.close("Calificación completada");
            },
            error: (error) => {
              console.log("Error al cambiar de estado", error);
            }
          })
        },
        error: (err) => {
          console.error('Error al enviar la calificación', err);
        }
      });
    } else {
      alert( $localize `Debe seleccionar una opción para calificar el pedido.`);
    }
  }

  closeModal(): void {
        this.activeModal.dismiss('cancelar');
  }

}
