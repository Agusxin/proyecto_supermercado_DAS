import { Component, OnInit } from '@angular/core';
import { IProveedor } from '../../api/models/iproveedor';
import { SuperResourcesService } from '../../api/resources/super-resources.service';
import { ActivatedRoute, Router } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-proveedores',
  templateUrl: './proveedores.component.html',
  styleUrls: ['./proveedores.component.css']
})
export class ProveedoresComponent implements OnInit {

  proveedores: IProveedor[] = [];

  constructor(private superResourceService: SuperResourcesService, private router: Router, private route: ActivatedRoute){}




  ngOnInit(): void {
    this.listarProveedores();
  }

  listarProveedores(): void {
    this.proveedores = this.route.snapshot.data['proveedores'];
  }


  bajaProveedor(id_proveedor: number): void {
    console.log(id_proveedor);
     Swal.fire({
       title: '¿Estás seguro?',
       text: 'Estás a punto de dar de baja a este proveedor. Esta acción no se puede deshacer.',
       icon: 'warning',
       showCancelButton: true,
       confirmButtonColor: '#3085d6',
       cancelButtonColor: '#d33',
       confirmButtonText: 'Sí, dar de baja',
       cancelButtonText: 'Cancelar'
     }).then( (result) => {
      if(result.isConfirmed) {
         console.log(id_proveedor);
        this.superResourceService.bajaProveedor({ id_proveedor }).subscribe ({
          next: (response) => {
            console.log(response)
            Swal.fire(
              'Proveedor dado de baja',
              'El proveedor ha sido dado de baja exitosamente.',
              'success'
            );
            this.superResourceService.listarProveedores().subscribe( (data) => {
              this.proveedores = data;
            });
          },
          error: (error) => {
           console.log(error);   
             Swal.fire(
              'Error',
              'No se puede dar de baja debido a que los pedidos de este proveedor no están en estado CALIFICADO',
              'error'
            );
          }
        });
      }
     });
  }

  productosProveedor(id_proveedor: number): void {
    this.router.navigate(['/main/productos-proveedor', id_proveedor]);    
  }

}




