import { Component, OnInit } from '@angular/core';
import { IdetallePedido } from '../../api/models/idetalle-pedido';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-detalle-pedido',
  templateUrl: './detalle-pedido.component.html',
  styleUrls: ['./detalle-pedido.component.css']
})
export class DetallePedidoComponent implements OnInit{

  detallePedido: IdetallePedido[] = [];

  constructor(private route: ActivatedRoute){}


  ngOnInit(): void {
    this.detallePedido = this.route.snapshot.data['detallePedido'];
    console.log(this.detallePedido);
  }
}
