use Supermercado
go


drop table usuarios
drop table detalle_pedidos
drop table pedidos
drop table valores_escala_proveedor
drop table escalas_proveedores
drop table precios_productos_proveedores
drop table productos_proveedores
drop table productos
drop table proveedores
go


create table usuarios 
(
    id_usuario     int identity(1,1)  not null,
    nombre_usuario varchar(50)        not null,
    clave          varchar(50)        not null,
	primary key (id_usuario),
)
go


create table proveedores
(
    id_proveedor                    int identity(1,1)                   not null,
	cuit                            varchar(50)                         not null,
	nombre                          varchar(50)                         not null,
	direccion                       varchar(50)                         not null,
	email                           varchar(100)                        not null,
	telefono                        varchar(30)                         not null,
	token                           varchar(100)                        not null,
	tipo_servicio                   char(1)                             not null,              
	url_endpoint                    varchar(150)                        not null,
	fecha_ultima_actualizacion      datetime default getdate()          not null,
	dar_baja                        char(1) default 'N'                 not null,
	check(tipo_servicio in ('R', 'S')),
	check(dar_baja in ('S', 'N')),
	primary key (id_proveedor),
	unique (cuit)
)
go



create table productos
(
    cod_barra      varchar(100) not null,
    nombre         varchar(50)  not null,
	imagen         varchar(150) not null,
	stock_actual   int          not null,
	stock_minimo   int          not null,
	primary key (cod_barra)
)
go


create table productos_proveedores
(
    cod_barra       varchar(100)                      not null,
	id_proveedor    int                               not null,
	primary key (cod_barra, id_proveedor),
	foreign key (cod_barra) references productos,
	foreign key (id_proveedor) references proveedores
)
go

create table precios_productos_proveedores
(
    cod_barra       varchar(100)                not null,
	id_proveedor    int                         not null,
	fecha_alta      datetime default getdate()  not null,
	precio          int                         not null,
	baja            char(1) default 'N'         not null,
	primary key (cod_barra, id_proveedor, fecha_alta),
	foreign key (cod_barra, id_proveedor) references productos_proveedores
)
go

create table escalas_proveedores 
(
   id_escala             int  identity(1,1)   not null,
   id_proveedor          int                  not null,
   nombre_escala         varchar(50)          not null,
   baja                  char(1) default 'N'  not null,
   id_escala_proveedor   int                  not null,
   check(baja in ('S', 'N')),
   primary key (id_escala, id_proveedor),
   foreign key (id_proveedor) references proveedores
)
go

create table valores_escala_proveedor
(
   id_escala          int             not null,
   id_proveedor       int             not null,
   valor_escala       int             not null,
   valor_ponderado    int             null,
   descripcion_valor  varchar(50)     null,
   primary key (id_escala, id_proveedor, valor_escala),
   foreign key (id_escala, id_proveedor) references escalas_proveedores
)
go


create table pedidos
( 
   id_pedido                    int  identity(1,1)                not null,
   estado_pedido                varchar(15) default 'Pendiente'   not null,
   fecha_registro_pedido        datetime                          not null,
   fecha_prevista_entrega       datetime                          null,
   fecha_real_entrega           datetime                          null,
   id_proveedor                 int                               not null,
   id_escala                    int                               null,
   valor_escala                 int                               null,
   anulado                      char(1) default 'N'               not null,
   id_pedido_proveedor          int                               null,
   check(estado_pedido in ('Pendiente', 'En proceso', 'Enviado', 'Entregado', 'Calificado') ),
   check(anulado in ('N','S') ),
   primary key (id_pedido),
   foreign key (id_proveedor) references proveedores,
   foreign key (id_escala, id_proveedor, valor_escala) references valores_escala_proveedor
)
go


create table detalle_pedidos
(
   id_pedido            int             not null,
   cod_barra            varchar(100)    not null,
   id_proveedor         int             not null,
   precio_unitario      int             null,
   cantidad             int             null,
   motivo_cancelacion   varchar(100)    null,
   primary key (id_pedido, cod_barra, id_proveedor),
   foreign key (id_pedido) references pedidos,
   foreign key (cod_barra, id_proveedor) references productos_proveedores
)
go


