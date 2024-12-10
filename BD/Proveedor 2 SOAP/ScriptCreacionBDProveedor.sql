use Proveedor2
go


drop table datos_proveedor
drop table detalle_pedido
drop table pedidos
drop table valores_escala_proveedor
drop table escala_proveedor
drop table productos
go



create table datos_proveedor
(
    cuit                            varchar(50)  not null,
    nombre                          varchar(50)  not null,
	direccion                       varchar(50)  not null,
	email                           varchar(100) not null,
	telefono                        varchar(30)  not null,
	token                           varchar(100) not null,
	tipo_servicio                   char(1)      not null,
	url_endpoint                    varchar(200)  not null,
	check (tipo_servicio in ('R', 'S' )),
	primary key (cuit)
)
go


create table productos
(
      cod_barra      varchar(100) not null,
      nombre         varchar(50)  not null,
	  imagen         varchar(150) not null,
	  precio         int          not null,
	  stock          int          not null,
	  primary key (cod_barra)
)
go

create table escala_proveedor
(
    id_escala         int identity(1,1)    not null, 
	nombre_escala     varchar(50)          not null,
    baja              char(1) default 'N'  not null,
	check (baja in ('N','S') ),
	primary key (id_escala)
)
go


create table valores_escala_proveedor
(
    id_escala           int           not null,
	valor_escala        int           not null,
	descripcion_valor   varchar(50)   not null,
	primary key (id_escala, valor_escala)
)
go


create table pedidos
(
    id_pedido                int identity(1,1)                not null,
	estado_pedido            varchar(15) default 'Pendiente'  not null,
	fecha_registro_pedido    datetime                         not null,
	fecha_prevista_entrega   datetime                         null,
	fecha_real_entrega       datetime                         null,
	id_escala                int                              null,
	calificacion_pedido      int                              null, 
	anulado                  char(1) default 'N'              not null,
	check ( estado_pedido in ('Pendiente','En Proceso', 'Enviado', 'Entregado') ),
	check ( anulado in ('N','S')),
	primary key (id_pedido),
	foreign key ( id_escala, calificacion_pedido) references valores_escala_proveedor

)
go




create table detalle_pedido
(
   id_pedido                     int                  not null,
   cod_barra                     varchar(100)         not null,
   precio_unitario               int                  not null,
   cantidad                      int                  not null,
   motivo_cancelacion            varchar(100)         not null,
   prod_sin_posibilidad_entrega  char(1) default 'N'  not null
   primary key (id_pedido, cod_barra),
   foreign key (id_pedido) references pedidos,
   foreign key (cod_barra) references productos
)
go