import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { SuperResourcesService } from '../../api/resources/super-resources.service';
import { IProveedor } from '../../api/models/iproveedor';
import { Router } from '@angular/router';
import { Conditional } from '@angular/compiler';
import { IproveedorEfectividad } from '../../api/models/iproveedor-efectividad';
import { IlistarvaloresEscalaProveedor } from '../../api/models/ilistarvalores-escala-proveedor';
import { ImostrarPonderaciones } from '../../api/models/imostrar-ponderaciones';

@Component({
  selector: 'app-nuevo-proveedor',
  templateUrl: './nuevo-proveedor.component.html',
  styleUrls: ['./nuevo-proveedor.component.css']
})
export class NuevoProveedorComponent implements OnInit {

  proveedorForm!: FormGroup;
  proveedorData!: IProveedor;
  isLoading: boolean = false;
  hasError: boolean = false;
  valoresEscala: any[] = [];
  valoresPonderados: ImostrarPonderaciones[] = [];

 


  
  constructor( private fb: FormBuilder, private superResourceService: SuperResourcesService, private router: Router) { }
 

  ngOnInit(): void {
    this.proveedorForm = this.fb.group({
      url: ['', Validators.required]
    });
  }

  onSubmit(): void {
    if(this.proveedorForm.valid){
      const { url } = this.proveedorForm.value;
      this.isLoading = true;
      this.hasError = false;


      this.superResourceService.insertarProveedor( {url }).subscribe({
        next: (response: IProveedor) => {
          this.isLoading = false;
          this.proveedorData = response;
          console.log('Datos del proveedor: ', this.proveedorData);
          this.listarValoresEscala();
          this.obtenerValoresPonderados();
        },
        error: (error) => {
          console.log("Error al obtener el proveedor", error);
          this.isLoading = false;
          this.hasError = true;
        }
      });
    }
  }

  listarValoresEscala(): void {
     this.superResourceService.listarValorEscalaProveedor( { id_proveedor:  this.proveedorData.id_proveedor } ).subscribe({
      next: (response: IlistarvaloresEscalaProveedor[]) => {
         this.valoresEscala = response.map( (item: IlistarvaloresEscalaProveedor) => ({ ...item, ponderacionSeleccionada: this.valoresPonderados[0] }));
      },
      error: (error) => {
        console.log("Error al listar los valores de escala", error);
      }
    });
  }

  obtenerValoresPonderados(): void {
    this.superResourceService.listarValorPonderadoMuestra().subscribe({
      next: (response: ImostrarPonderaciones[]) => {
        this.valoresPonderados = response;
      },
      error: (error) => {
        console.log("Error al obtener los valores ponderados", error);
      }
    });
  }

 
  guardarPonderacion(): void {

    const valoresInvalidos = this.valoresEscala.filter( (valor) => !valor.ponderacionSeleccionada);

    console.log(valoresInvalidos);

    if(valoresInvalidos.length > 0){
      alert( $localize `Por favor, selecciones un valor ponderado para todos los elementos antes de guardar.`);
    }

    this.valoresEscala.forEach( valor => {
      const jsonRequest = {
        id_proveedor : this.proveedorData.id_proveedor,
        valor_escala : valor.valor_escala,
        descripcion_valor : valor.descripcion_valor,
        valor_ponderado: valor.ponderacionSeleccionada
      };
      
      this.superResourceService.actualizarPonderacion(jsonRequest).subscribe({
        next: (response: any) => {
           console.log("Ponderacion actualizada: ", response);
           this.router.navigate(['/main/listarProveedores']);
        },
        error: (error) => {
          console.log("Error al actualizar la ponderacion ", error);
        }
      });
    })
  }

  
  volverAlListado(): void {
    this.router.navigate(['/main/listarProveedores']);
  }
}
