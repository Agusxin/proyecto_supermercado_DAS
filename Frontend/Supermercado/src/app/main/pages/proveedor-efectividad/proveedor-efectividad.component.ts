import { Component } from '@angular/core';
import { IproveedorEfectividad } from '../../api/models/iproveedor-efectividad';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-proveedor-efectividad',
  templateUrl: './proveedor-efectividad.component.html',
  styleUrls: ['./proveedor-efectividad.component.css']
})
export class ProveedorEfectividadComponent {

  
  efectividadProveedor: IproveedorEfectividad[] = [];

  constructor(private route: ActivatedRoute){}


  ngOnInit(): void {
    this.efectividadProveedor = this.route.snapshot.data['efectividadProveedor'];
  }

}
