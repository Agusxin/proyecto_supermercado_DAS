use Proveedor2
go



insert into datos_proveedor
values('18-40598264-9', 'AutomotoresLacio', '9 de julio 1038', 'automotores@hotmail.com', '380-6730945', '123', 'S', 'http://localhost:8082/Proveedor2-SOAP/services/ProveedorInfoWSPort?wsdl')


insert into escala_proveedor (nombre_escala)
values ('Escala-calificacion-partes-motor-auto')


insert into valores_escala_proveedor
values ( 1 , 10 , 'Muy Suave'),
       ( 1 , 20 , 'Suave'),
	   ( 1 , 30 , 'Pesado'),
	   ( 1 , 40, 'Muy Pesado')
	   
insert into productos (cod_barra, nombre, imagen, precio, stock)
values('0123456','Leche Illolay', 'lecheIlolay.png', 1000, 50 ),
      ('6783456','Club Social Original', 'ClubSocialOriginal.png', 800, 15)



/*
select *
 from datos_proveedor


select *
 from pedidos


 
select *
  from pedidos

select *
  from detalle_pedido

delete from detalle_pedido
delete from pedidos

select *
  from productos

select * 
  from datos_proveedor

select * 
  from escala_proveedor

select *
 from valores_escala_proveedor



update p
   set p.estado_pedido = 'En proceso'
    from pedidos p
  where id_pedido = 1176


update p
   set p.estado_pedido = 'Entregado',
       p.fecha_prevista_entrega = '2024-10-05 15:18:10',
	   p.fecha_real_entrega = '2024-10-10 10:03:20'
    from pedidos p
  where id_pedido = 121

select *
  from pedidos

delete from detalle_pedido
delete from pedidos

update p
   set 
       p.fecha_real_entrega = '2024-05-10 18:00:00'
    from pedidos p
  where id_pedido = 1176

  

 
update pedidos set estado_pedido = 'Entregado',
                   fecha_prevista_entrega = '2024-11-11 15:18:10',
				   fecha_real_entrega = '2024-15-11 20:04:16'


update pedidos set estado_pedido = 'Entregado',
                   fecha_prevista_entrega = '2024-11-11 15:18:10',
				   fecha_real_entrega = '2024-15-11 20:04:16'
				where id_pedido = '68'

*/
