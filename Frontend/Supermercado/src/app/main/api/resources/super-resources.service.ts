import { Injectable } from '@angular/core';
import { IResourceMethodObservable, Resource, ResourceAction, ResourceHandler, ResourceParams, ResourceRequestBodyType, ResourceRequestMethod, ResourceResponseBodyType } from '@ngx-resource/core';
import { environment } from 'src/environments/environment';
import { IUsuarioLogin } from '../models/iusuario-login';
import { IPedido } from '../models/ipedido';
import { IProveedor } from '../models/iproveedor';
import { IdetallePedido } from '../models/idetalle-pedido';
import { IlistarvaloresEscalaProveedor } from '../models/ilistarvalores-escala-proveedor';
import { IproveedorEfectividad } from '../models/iproveedor-efectividad';
import { ImostrarPonderaciones } from '../models/imostrar-ponderaciones';
import { Iproducto } from '../models/iproducto';

@Injectable({
  providedIn: 'root'
})
@ResourceParams({
  pathPrefix: `${environment.apiUrl}/supermercado`
})
export class SuperResourcesService extends Resource {

  constructor(handler?: ResourceHandler) {
    super(handler);
   }


  @ResourceAction({
    path: '/login',
    method: ResourceRequestMethod.Post,
    requestBodyType: ResourceRequestBodyType.JSON
  })
  login!: IResourceMethodObservable< IUsuarioLogin , void>;

  @ResourceAction({
    path: '/listarPedidos',
    method: ResourceRequestMethod.Get
  })
  listarPedidos!:  IResourceMethodObservable<IPedido[], IPedido[]>;

  @ResourceAction({
    path: '/cancelarPedido',
    method: ResourceRequestMethod.Post,
    requestBodyType: ResourceRequestBodyType.JSON
  })
  cancelarPedido!:  IResourceMethodObservable<any, { id_pedido: number }>;

  
  @ResourceAction({
    path: '/listarProveedores',
    method: ResourceRequestMethod.Get
  })
  listarProveedores!:  IResourceMethodObservable<IProveedor[], IProveedor[]>;

  @ResourceAction({
    path: '/insertarProveedor',
    method: ResourceRequestMethod.Get
  })
  insertarProveedor!: IResourceMethodObservable<{url: string}, IProveedor >;

  @ResourceAction({
    path: '/listarProductosProveedor',
    method: ResourceRequestMethod.Post,
    requestBodyType: ResourceRequestBodyType.JSON
  })
  listarProductosProveedor!:  IResourceMethodObservable<{ id_proveedor: number }, Iproducto[]>;


  @ResourceAction({
    path: '/listarDetallePedido',
    method: ResourceRequestMethod.Post,
    requestBodyType: ResourceRequestBodyType.JSON
  })
  detallePedido!: IResourceMethodObservable< {id_pedido: number}, IdetallePedido[] >;

  
  @ResourceAction({
    path: '/listarValorEscalaProveedor',
    method: ResourceRequestMethod.Post,
    requestBodyType: ResourceRequestBodyType.JSON
  })
  listarValorEscalaProveedor!: IResourceMethodObservable< { id_proveedor: number }, IlistarvaloresEscalaProveedor[]  >;


  @ResourceAction({
    path: '/listarValorPonderadoProveedor',
    method: ResourceRequestMethod.Post,
    requestBodyType: ResourceRequestBodyType.JSON
  })
  listarValorPonderadoProveedor!: IResourceMethodObservable< { id_proveedor: number }, IlistarvaloresEscalaProveedor[]  >;


  @ResourceAction({
    path: '/listarValoresPonderadosMuestra',
    method: ResourceRequestMethod.Get
  })
  listarValorPonderadoMuestra!: IResourceMethodObservable< void, ImostrarPonderaciones[] >;



  @ResourceAction({
    path: '/bajaProveedor',
    method: ResourceRequestMethod.Post,
    requestBodyType: ResourceRequestBodyType.JSON
  })
  bajaProveedor!: IResourceMethodObservable< any, {id_proveedor:number}>;

  @ResourceAction({
    path: '/calificarPedido',
    method: ResourceRequestMethod.Post,
    requestBodyType: ResourceRequestBodyType.JSON
  })
  calificarPedido!: IResourceMethodObservable< any, any>
  
  @ResourceAction({
    path: '/actualizarPonderacion',
    method: ResourceRequestMethod.Post,
    requestBodyType: ResourceRequestBodyType.JSON
  })
  actualizarPonderacion!: IResourceMethodObservable< any, any>

  @ResourceAction({
    path: '/estadoPostCalificacion',
    method: ResourceRequestMethod.Post,
    responseBodyType: ResourceResponseBodyType.Text
  })
  estadoPostCalificacion!: IResourceMethodObservable< {id_pedido: number}, string>


  @ResourceAction({
    path: '/generarEfectividadProveedores',
    method: ResourceRequestMethod.Get
   })
  efectividadProveedor!: IResourceMethodObservable< void, IproveedorEfectividad[]>


}

