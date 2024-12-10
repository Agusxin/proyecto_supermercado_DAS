use [Proveedor 1]
go


insert into datos_proveedor
values('20-44660372-2', 'LaceSRL', 'Velez Sarsfield 372', 'lace@gmail.com', '351-5281471', '874602035', 'R', 'http://localhost:8081/proveedor')


insert into escala_proveedor (nombre_escala)
values ('Escala-valores-analisis-sangre')


insert into valores_escala_proveedor (id_escala, valor_escala, descripcion_valor)
values (1, 1 , 'Malo'),
       (1, 2 , 'Regular'),
	   (1, 3 , 'Bueno'),
	   (1, 4, 'Excelente')

insert into productos (cod_barra, nombre, imagen, precio, stock)
values('0123456','Leche Illolay', 'lecheIlolay.png', 1000, 50 ),
      ('9876543','Yogur Manfrey', 'yogurManfrey.png', 1500, 50)



	  

update pedidos set estado_pedido = 'Entregado',
                   fecha_prevista_entrega = '2024-11-11 15:18:10',
				   fecha_real_entrega = '2024-15-11 20:04:16'
				

select *
 from pedidos

delete from detalle_pedido
delete from pedidos

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

select *
  from detalle_pedido



update p
   set p.estado_pedido = 'En proceso',
       p.fecha_prevista_entrega = '2024-10-02 15:18:10'
    from pedidos p
  where id_pedido = 2

select *
 from pedidos

update p
   set 
       p.fecha_real_entrega = '2024-05-10 18:00:00'
    from pedidos p
  where id_pedido = 1176





update pedidos set estado_pedido = 'Entregado',
                   fecha_prevista_entrega = '2024-11-11 15:18:10',
				   fecha_real_entrega = '2024-15-11 20:04:16'
				where id_pedido = '68'
*/





