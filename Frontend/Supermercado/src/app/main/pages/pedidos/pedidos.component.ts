import { Component, OnInit } from '@angular/core';
import { IPedido } from '../../api/models/ipedido';
import { SuperResourcesService } from '../../api/resources/super-resources.service';
import { interval, Subscription } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ModalCalificarPedidoComponent } from '../modal-calificar-pedido/modal-calificar-pedido.component';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-pedidos',
  templateUrl: './pedidos.component.html',
  styleUrls: ['./pedidos.component.css']
})
export class PedidosComponent implements OnInit {

  pedidos: IPedido[] = [];
  nombreProveedorFiltro: string = '';

  constructor(private superResourceService: SuperResourcesService, private route: ActivatedRoute, private modalService: NgbModal){}

  ngOnInit(): void {
    this.listarPedidos();
  }


  listarPedidos(): void {
    this.pedidos = this.route.snapshot.data['pedidos'];
  }

  getEstadoClass(estado: string): string {
    switch (estado) {
      case 'Pendiente': return $localize `pendiente`;
      case 'En proceso': return $localize `en-proceso`;
      case 'Enviado': return $localize `enviado`;
      case 'Entregado': return $localize `entregado`;
      case 'Calificado': return $localize `calificado`;
      default: return '';
    }
  }

  cancelarPedido(idPedido: number): void{
    const pedidoCancelado = { id_pedido: idPedido};
  
    Swal.fire({
      title: '¿Estás seguro?',
      text: 'Estás a punto de cancelar el pedido. Esta acción no se puede deshacer.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Sí, dar de baja',
      cancelButtonText: 'Cancelar'
    }).then( (result) => {
       console.log(pedidoCancelado);
       if(result.isConfirmed) {
        this.superResourceService.cancelarPedido(pedidoCancelado).subscribe({
          next: () => {
            console.log(`Pedido ${idPedido} cancelado exitosamente.`);
            Swal.fire(
              'Pedido Cancelado',
              'El pedido se cancelo exitosamente.',
              'success'
            );
            this.refreshPedidos();
          },
          error: (error) => {
            console.error('Error al cancelar el pedido:', error);
            Swal.fire(
              'Error',
              'No se pudo cancelar el pedido',
              'error'
            );
          }
        });
       }
    });
  }


  evaluarPedido(idPedido: number, idProveedor:number): void{
      console.log("idPedido:" + idPedido);
      console.log("idProveedor:" + idProveedor);
      const modalRef = this.modalService.open(ModalCalificarPedidoComponent);
      modalRef.componentInstance.id_pedido = idPedido;
      modalRef.componentInstance.id_proveedor = idProveedor.toString();
      console.log('ID Proveedor pasado al modal:', modalRef.componentInstance.id_proveedor); 

  }


  
  refreshPedidos(): void {
    this.superResourceService.listarPedidos().subscribe({
      next: (data) => {
        this.pedidos = data;
      },
      error: (error) => {
        console.error("Error al refrescar los pedidos", error);
      }
    });
  }

  

}
