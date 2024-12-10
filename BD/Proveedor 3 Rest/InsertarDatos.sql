use Proveedor3
go
     
	 

insert into datos_proveedor
values('21-4850493-6', 'Proveedor de chocolates y cafes ', 'San salvador 33', 'email3', '351-456789', '123456', 'R', 'http://localhost:8083/proveedor')


insert into escala_proveedor (nombre_escala)
values ('Escala-puntuacion-calidad')


insert into valores_escala_proveedor
values ( 1 , 5 , 'Malo'),
       ( 1 , 10 , 'Regular'),
	   ( 1 , 15 , 'Bueno'),
	   ( 1 , 20, 'Excelente')
	   

insert into productos (cod_barra, nombre, imagen, precio, stock)
values('4536890','Cafe Dolca', 'cafeDolca.png', 2000, 30 ),
      ('8723948','Nesquik', 'Nesquik.png', 3000, 30)


/*		  
select * 
  from datos_proveedor

select *
  from pedidos

delete from detalle_pedido
delete from pedidos

select *
  from productos

select * 
  from detalle_pedido

insert into productos
values('abc123', 'producto1', 'imagen1', 600, 60 )



delete from detalle_pedido

delete from pedidos

select *
  from datos_proveedor

select * 
   from escala_proveedor

select * 
  from valores_escala_proveedor

select * 
  from productos

  
select *
   from escala_proveedor ep
     join valores_escala_proveedor vep
	   on ep.id_escala = vep.id_escala





insert into productos
values('qwe456', 'producto2', 'imagen2', 500, 20)

select *
  from productos

update p
   set p.imagen = 'galletasTerrabusi.png'
  from productos p
   where p.nombre = 'Galletas Terrabusi'



update p
   set p.imagen = 'lecheIlolay.png'
  from productos p
   where p.nombre = 'Leche Ilolay'


update p
   set p.imagen = 'yoguManfrey.png'
  from productos p
   where p.nombre = 'Yogur Manfrey'


select *
 from productos

 delete detalle_pedido
 delete pedidos


update p
   set p.estado_pedido = 'Entregado',
       p.fecha_prevista_entrega = '2024-05-10 18:00:00',
	   p.fecha_real_entrega = '2024-10-10 18:00:00'
    from pedidos p
  where id_pedido = 66


select *
 from pedidos

select *
 from detalle_pedido

 
update pedidos set estado_pedido = 'Entregado',
                   fecha_prevista_entrega = '2024-11-11 15:18:10',
				   fecha_real_entrega = '2024-15-11 20:04:16'


update pedidos set estado_pedido = 'Entregado',
                   fecha_prevista_entrega = '2024-11-11 15:18:10',
				   fecha_real_entrega = '2024-15-11 20:04:16'
				where id_pedido = '68'




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
 from datos_proveedor
*/