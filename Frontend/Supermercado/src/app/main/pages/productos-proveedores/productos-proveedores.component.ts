import { Component, OnInit } from '@angular/core';
import { Iproducto } from '../../api/models/iproducto';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-productos-proveedores',
  templateUrl: './productos-proveedores.component.html',
  styleUrls: ['./productos-proveedores.component.css']
})
export class ProductosProveedoresComponent implements OnInit {

  productos: Iproducto[] = [];

  constructor(private route: ActivatedRoute){}

  ngOnInit(): void {
    
    this.productos = this.route.snapshot.data['productosProveedor'];
  }

}
