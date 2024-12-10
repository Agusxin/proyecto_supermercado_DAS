use Proveedor4
go
     
select *
 from pedidos

select *
 from detalle_pedido

select *
 from productos

 
delete from detalle_pedido
delete from pedidos


select *
 from detalle_pedido
go

create or alter procedure enviar_informacion_del_proveedor
as
begin
   declare @jsonRequest nvarchar(max),
           @datos_proveedor nvarchar(max),
		   @escala_proveedor nvarchar(max),
		   @productos_proveedor nvarchar(max)


   select @datos_proveedor =  (
     select * 
	    from datos_proveedor
		for json auto, without_array_wrapper
   )

   select @escala_proveedor = (
       select escala_proveedor.id_escala, escala_proveedor.nombre_escala, escala_proveedor.baja, valores_escala.valor_escala, valores_escala.descripcion_valor
	      from escala_proveedor escala_proveedor
		    join valores_escala_proveedor valores_escala
			  on escala_proveedor.id_escala = valores_escala.id_escala
		  for json auto, without_array_wrapper
   
   )

   select @productos_proveedor = (
      select * 
	    from productos
		for json auto
   )

  
   set @jsonRequest = ('{' +
        SUBSTRING(@datos_proveedor, 2, LEN(@datos_proveedor) - 2) + ',' +
        SUBSTRING(@escala_proveedor, 2, LEN(@escala_proveedor) - 2) + ',' + '
		"productos": ' + @productos_proveedor  +      
	  '}' )

   select @jsonRequest as informacion_proveedor
end
go


execute enviar_informacion_del_proveedor
go


create or alter procedure getToken
as
begin
   select dp.token
     from datos_proveedor dp
end
go

execute getToken
go


create or alter procedure enviar_datos_propios
as
begin
  declare @jsonRequest nvarchar(max)

  select @jsonRequest = (
     select * 
	    from datos_proveedor
		for json auto, without_array_wrapper
  )
   
  select @jsonRequest as info_datos_proveedor

end
go

execute enviar_datos_propios
go


create or alter procedure enviar_info_productos
as
begin
  declare @jsonRequest nvarchar(max)
    
	select @jsonRequest = (
	  select cod_barra, precio
	    from productos
		for json auto
	)

	select @jsonRequest  as info_productos_proveedor
end
go


execute enviar_info_productos
go


create or alter procedure insertar_pedidos
(
  @json nvarchar(max)
)
as
begin
  declare @id_pedido int
  declare @jsonResponse nvarchar(max)

  insert into pedidos (fecha_registro_pedido)
  values(CURRENT_TIMESTAMP)
  set @id_pedido = @@IDENTITY

  insert into detalle_pedido (id_pedido, cod_barra, precio_unitario, cantidad)
  select @id_pedido, cod_barra, precio_unitario, cantidad
  from openjson(@json)
  with (
     cod_barra varchar(100),
	 precio_unitario int,
	 cantidad int
  )
  
  select @jsonResponse = (
    select @id_pedido as id_pedido
	for json path,without_array_wrapper
  ) 

  select @jsonResponse as id_pedido_proveedor
end
go

execute insertar_pedidos @json = 
'[
   {
    "cod_barra":"abc123",
    "precio_unitario":600,
    "cantidad":11
   },
   {
    "cod_barra":"cod_barra4",
    "precio_unitario":450,
    "cantidad":11
   }
  ]
'
go


delete detalle_pedido
delete pedidos

create or alter procedure get_pedidos
(
  @jsonRequest nvarchar(max)
)
as
begin
    declare @jsonResponse nvarchar(max)

	    select @jsonResponse = (
	   select p.id_pedido as id_pedido_proveedor, 
	          p.estado_pedido, 
			  p.fecha_prevista_entrega, 
			  p.fecha_real_entrega, 
			  p.id_escala, 
			  p.calificacion_pedido, 
			  (select dp.cod_barra,
			          dp.prod_sin_posibilidad_entrega,
			          dp.motivo_cancelacion
			     from detalle_pedido dp
				   where dp.id_pedido = p.id_pedido 
				     and dp.prod_sin_posibilidad_entrega = 'S'
				   for json path
			   ) as productos_no_entregados
	     from pedidos p
		  join openjson(@jsonRequest)
		   with (
		     id_pedido int
		   ) as jsonPedidos
	       on p.id_pedido = jsonPedidos.id_pedido
		 where p.id_escala is null 
	       for json path
	)

	/*
     select @jsonResponse = (
	   select p.id_pedido as id_pedido_proveedor, 
	          p.estado_pedido, 
			  p.fecha_prevista_entrega, 
			  p.fecha_real_entrega, 
			  p.id_escala, 
			  p.calificacion_pedido, 
			  dp.cod_barra,
			  dp.prod_sin_posibilidad_entrega,
			  dp.motivo_cancelacion
	     from pedidos p
		   join detalle_pedido dp
		     on p.id_pedido = dp.id_pedido
		  join openjson(@jsonRequest)
		   with (
		     id_pedido int
		   ) as jsonPedidos
	       on p.id_pedido = jsonPedidos.id_pedido
		 where p.id_escala is null 
	       for json path
	)
	*/

	select @jsonResponse as pedidos
end
go


execute get_pedidos @jsonRequest = 
'
 [ 
  {
    "id_pedido" : 1176
  },
  {
    "id_pedido" : 1034
  }
 ]
'
go


create or alter procedure cancelar_pedido
(
  @jsonRequest nvarchar(max)
)
as
begin
   declare @id_pedido int

   select @id_pedido = JSON_VALUE(@jsonRequest, '$.id_pedido')

   update p
      set p.anulado = 'S'
     from pedidos p
	where p.id_pedido = @id_pedido
	  and p.estado_pedido = 'Pendiente'
end
go

execute cancelar_pedido @jsonRequest = 
'
   {
    "id_pedido":1176
   }
'
go


create or alter procedure calificar_pedido
(
  @jsonRequest nvarchar(max)
)
as
begin
   declare @id_pedido int,
           @valor_escala int,
		   @id_escala int

   select @id_pedido = JSON_VALUE(@jsonRequest, '$.id_pedido'),
	      @valor_escala = JSON_VALUE(@jsonRequest, '$.valor_escala'),
		  @id_escala = JSON_VALUE(@jsonRequest, '$.id_escala')

	update p
	   set p.calificacion_pedido = @valor_escala,
		   p.id_escala = @id_escala
	   from pedidos p
	  where p.id_pedido = @id_pedido
	   
end
go


execute calificar_pedido @jsonRequest = '
 {
   "id_pedido": 61,
   "valor_escala": 10,
   "id_escala": 1
 }
'




select *
 from pedidos

select *
 from detalle_pedido

update p
   set p.prod_sin_posibilidad_entrega = 'S',
       p.motivo_cancelacion = 'Prueba Final'
  from detalle_pedido p
   where p.id_pedido = 7
