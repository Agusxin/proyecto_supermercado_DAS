use Proveedor4
go




insert into datos_proveedor
values('64503986', 'Distribuidoras Centro', '9 de julio 1138', 'DCentro@gmail.com', '351-8567980', '4567', 'S', 'http://localhost:8084/Proveedor4-SOAP/services/ProveedorInfoWSPort?wsdl')


insert into escala_proveedor (nombre_escala)
values ('escala Proveedor')


insert into valores_escala_proveedor
values ( 1 , 1 , 'Desastroso'),
       ( 1 , 2 , 'Muy Malo'),
	   ( 1 , 3 , 'Malo'),
	   ( 1 , 4, 'Bajo Rendimiento')
	  
	  
	   
insert into productos (cod_barra, nombre, imagen, precio, stock)
values('458792','Galletas Terrabusi', 'galletasTerrabusi.png', 500, 10 ),
      ('6783456','Club Social Original', 'ClubSocialOriginal.png', 300, 10)



/*
select *
  from datos_proveedor

select * 
   from escala_proveedor

select * 
  from valores_escala_proveedor

select * 
  from productos


select *
  from pedidos
go

select *
  from detalle_pedido
go

delete from detalle_pedido
delete from pedidos
go



select *
  from productos

select * 
  from datos_proveedor


update p
   set p.estado_pedido = 'En proceso'
    from pedidos p
  where id_pedido = 1176


update p
   set p.estado_pedido = 'Enviado',
       p.fecha_prevista_entrega = '2024-10-02 15:18:10'
    from pedidos p
  where id_pedido = 1176


update p
   set p.imagen = 'cafeDolca.png'
  from productos p
   where p.nombre = 'Cafe Dolca'



update p
	 set  p.imagen = 'lecheIlolay.png'
  from productos p
   where p.nombre = 'Leche Ilolay'


update p
   set p.imagen = 'yoguManfrey.png'
  from productos p
   where p.nombre = 'Yogur Manfrey'


select *
  from productos


update p
   set 
       p.fecha_real_entrega = '2024-05-10 18:00:00'
    from pedidos p
  where id_pedido = 1176


  

update pedidos set estado_pedido = 'Entregado',
                   fecha_prevista_entrega = '2024-11-10 15:18:10',
				   fecha_real_entrega = '2024-15-10 20:04:16'



update p
   set p.estado_pedido = 'Pendiente',
       p.fecha_real_entrega = null
  from pedidos p
   where id_pedido = 2


update pedidos set estado_pedido = 'Entregado',
                   fecha_prevista_entrega = '2024-11-11 15:18:10',
				   fecha_real_entrega = '2024-15-11 20:04:16'


update pedidos set estado_pedido = 'Entregado',
                   fecha_prevista_entrega = '2024-11-11 15:18:10',
				   fecha_real_entrega = '2024-15-11 20:04:16'
				where id_pedido = '68'
*/




