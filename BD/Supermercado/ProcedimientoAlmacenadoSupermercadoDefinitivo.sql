use Supermercado
go


update productos set stock_actual = 8 
go

select *
 from productos
go





select *
 from pedidos

select *
 from detalle_pedidos

select *
 from precios_productos_proveedores



select *
 from proveedores


-- Login del usuario ( usuario y contraseña)
create or alter procedure login_usuario
(
  @jsonRequest nvarchar(max)
)
as
   declare @nombre_usuario  varchar(50),
           @clave     varchar(50)

   select @nombre_usuario = JSON_VALUE(@jsonRequest, '$.nombre_usuario'),
          @clave = JSON_VALUE(@jsonRequest, '$.clave')

begin

    if not exists (
	   select * 
	    from usuarios
	   where nombre_usuario = @nombre_usuario
	     and clave = @clave
	
	)
	begin
	   -- Si no se encuentra ningún resultado, lanzar un error
        RAISERROR('Usuario o clave no válidos.', 16, 1)
	end

    select nombre_usuario, clave
	  from usuarios
	 where nombre_usuario = @nombre_usuario
	   and clave = @clave
end
go


execute login_usuario @jsonRequest = 
'
 {
   "nombre_usuario": "Agusxin",
   "clave": "123456"
 }
'
go




--Procedimiento insertar proveedores
create or alter procedure insert_proveedores
(
   @json nvarchar(max)
)
as
begin
   declare
           @cuit          varchar(50), 
           @nombre        varchar(50),  
           @direccion     varchar(50),  
           @email         varchar(100), 
           @telefono      varchar(30),  
           @token         varchar(100), 
           @tipo_servicio char(1),      
           @url_endpoint  varchar(150)

  declare  @id_proveedor  int,
           @id_escala     int


   select  @cuit          = JSON_VALUE(@json, '$.cuit'),
		   @nombre        = JSON_VALUE(@json, '$.nombre'),
		   @direccion     = JSON_VALUE(@json, '$.direccion'),
		   @email         = JSON_VALUE(@json, '$.email'),
		   @telefono      = JSON_VALUE(@json, '$.telefono'), 
		   @token         = JSON_VALUE(@json, '$.token'),
		   @tipo_servicio = JSON_VALUE(@json, '$.tipo_servicio'),
		   @url_endpoint  = JSON_VALUE(@json, '$.url_endpoint')
	  from openjson(@json) as prov
  


   select @id_proveedor = id_proveedor
    from proveedores
	where cuit = @cuit


   if @id_proveedor is not null
   begin 
      update proveedores
       set dar_baja = 'N',
           nombre = @nombre,         
           direccion = @direccion,
           email = @email,
           telefono = @telefono,
           token = @token,
           tipo_servicio = @tipo_servicio,
           url_endpoint = @url_endpoint
       where id_proveedor = @id_proveedor

	     select p.id_proveedor, p.cuit, p.nombre, p.direccion, p.email, p.telefono
       from proveedores p
       where p.id_proveedor = @id_proveedor

	   return 
   end
   

   insert into proveedores (cuit, nombre, direccion, email, telefono, token, tipo_servicio, url_endpoint)
    values ( @cuit, @nombre, @direccion, @email, @telefono, @token, @tipo_servicio, @url_endpoint) 
   set @id_proveedor = @@IDENTITY

   insert into escalas_proveedores ( id_proveedor, nombre_escala, baja, id_escala_proveedor)
    values (@id_proveedor, JSON_VALUE(@json, '$.nombre_escala'), JSON_VALUE(@json, '$.baja'), JSON_VALUE(@json, '$.id_escala'))
   set @id_escala = @@IDENTITY

   create table #valoresTemp(
      valor_escala int,
	  descripcion_valor varchar(50)
   )

   insert into #valoresTemp(valor_escala, descripcion_valor)
   select JSON_VALUE(valores_escala.value, '$.valor_escala') as valor_escala,
          JSON_VALUE(valores_escala.value, '$.descripcion_valor') as descripcion_valor
   from openjson(@json, '$.valores_escala') as valores_escala

   insert into valores_escala_proveedor (id_escala, id_proveedor, valor_escala, descripcion_valor)
   select @id_escala, @id_proveedor, valor_escala, descripcion_valor
      from #valoresTemp

   create table #tempProductos
	(  
	  cod_barra varchar(100),
	  nombre    varchar(50),
	  imagen    varchar(150),
	  precio    int, 
	  stock     int
	)

	insert into #tempProductos(cod_barra, nombre, imagen, precio, stock)
	select * 
	     from openjson(@json, '$.productos')
		 with
		 (
		   cod_barra varchar(100),
		   nombre varchar(50),
		   imagen varchar(150),
		   precio int,
		   stock int
		 )

	insert into productos (cod_barra, nombre, imagen, stock_actual, stock_minimo)
	select t.cod_barra, t.nombre, t.imagen, t.stock, 10
	from #tempProductos t
	 left join productos p 
	    on t.cod_barra = p.cod_barra
	where p.cod_barra is null


   insert into productos_proveedores(cod_barra, id_proveedor)
   select JSON_VALUE(value, '$.cod_barra') as cod_barra,
          @id_proveedor as id_proveedor
   from openjson(@json, '$.productos')

   insert into precios_productos_proveedores( cod_barra, id_proveedor, fecha_alta, precio)
   select JSON_VALUE(value, '$.cod_barra') as cod_barra,
          @id_proveedor as id_proveedor,
		  CURRENT_TIMESTAMP,
		  JSON_VALUE(VALUE, '$.precio') as precio
   from openjson(@json, '$.productos')

   select p.id_proveedor, p.cuit, p.nombre, p.direccion, p.email, p.telefono
      from proveedores p
	where p.id_proveedor = @id_proveedor 
  
end
go



declare @json nvarchar(max) = '
{ 
  "cuit":"3459875067",
  "nombre":"nombre1",
  "direccion":"direccion1",
  "email":"email1",
  "telefono":"3825-5281471",
  "token":"874602035",
  "tipo_servicio":"R",
  "url_endpoint":"proveedor1",
  "id_escala":1,
  "nombre_escala":"escala1",
  "baja":"N",
  "valores_escala":
            [{
              "valor_escala":1,
              "descripcion_valor":"Malo"
              
            },
            {
              "valor_escala":4,
              "descripcion_valor":"Regular"
              
            },
            {
              "valor_escala":7,
              "descripcion_valor":"Bueno"
              
            },
            {
              "valor_escala":10,
              "descripcion_valor":"Excelente"
              
            }],
  "productos": 
            [{
              "cod_barra":"abc123",
              "nombre":"producto1",
              "imagen":"imagen1",
              "precio":350,
              "stock":45
             },
             {
               "cod_barra":"qwe456",
               "nombre":"producto2",
               "imagen":"imagen2",
               "precio":450,
               "stock":30
             }]
}             
'
execute  insert_proveedores @json
go



create or alter procedure traer_id_proveedor_por_cuit
(
  @cuit varchar(50)
)
as
begin
  select id_proveedor
     from proveedores
	   where @cuit = cuit
end
go

execute traer_id_proveedor_por_cuit @cuit = 20304567890
go


create or alter procedure obtener_id_escala
(
  @id_proveedor int,
  @id_escala    int output
)
as
begin
   set @id_escala =  (
      select id_escala
        from escalas_proveedores ep
	   where @id_proveedor = ep.id_proveedor
	  )
 
end
go



/*Dar de baja al proveedor*/
create or alter procedure baja_proveedor
(
  @id_proveedor int
)
as
begin
  update pro
   set dar_baja = 'S'
     from proveedores pro 
     where id_proveedor = @id_proveedor
	   and dar_baja = 'N'
end
go

execute baja_proveedor @id_proveedor = 2
go



-- Obtener Productos por Proveedor
create or alter procedure obtenerProductosProveedor
(
  @jsonRequest nvarchar(max)
)
as
begin

   declare @id_proveedor int

    select @id_proveedor = JSON_VALUE(@jsonRequest, '$.id_proveedor')
 

   select distinct  p.cod_barra, p.nombre,pre.precio, p.imagen
     from productos p
	    join productos_proveedores pro
		  on p.cod_barra = pro.cod_barra
		    join precios_productos_proveedores pre
			  on p.cod_barra = pre.cod_barra
			   and @id_proveedor = pre.id_proveedor
		where pre.baja = 'N'
end
go

select *
 from precios_productos_proveedores


execute obtenerProductosProveedor @jsonRequest = '
  {
    "id_proveedor": 1
  }
'
go



/*Listar proveedores*/
create or alter procedure listar_proveedores
as
begin
   select id_proveedor,cuit, nombre, direccion, email, telefono
      from proveedores
	 where dar_baja = 'N'
end
go


execute listar_proveedores
go


-- Listar Pedidos
create or alter procedure listar_pedidos
as
begin
  select p.id_pedido, 
         p.estado_pedido, 
		 p.fecha_registro_pedido, 
		 p.fecha_prevista_entrega, 
		 p.fecha_real_entrega,
		 p.id_proveedor,
		 pro.nombre as nombre_proveedor, 
		 p.id_escala,
		 p.valor_escala
    from pedidos p
	 join proveedores pro 
	   on p.id_proveedor = pro.id_proveedor
   where p.anulado = 'N'
      and pro.dar_baja = 'N'
end
go

execute listar_pedidos
go


-- Listar el detalle pedido
create or alter procedure listar_detalle_pedido
(
  @jsonRequest nvarchar(max)
)
as
 declare @id_pedido int
 select @id_pedido = JSON_VALUE(@jsonRequest, '$.id_pedido')
begin
  select p.imagen, dp.cod_barra, dp.cantidad, dp.motivo_cancelacion
    from detalle_pedidos dp
	  join productos p
	   on dp.cod_barra = p.cod_barra
   where id_pedido = @id_pedido
end
go

select *
 from detalle_pedidos

execute listar_detalle_pedido @jsonRequest = 
'
  {
    "id_pedido": 1
  }

'
go


-- Obtener Token en base a la url del proveedor
create or alter procedure getTokenPorUrl
(
  @url_endpoint varchar(150)
)
as
begin
   select p.token
     from proveedores p
   where p.url_endpoint = @url_endpoint 
     and p.dar_baja = 'N'
end
go

execute getTokenPorUrl @url_endpoint = 'http://localhost:8081/proveedor'
go



-- Obtener los Datos del Proveedor
create or alter procedure getDatosProveedor
(
  @json nvarchar(max)
)
as
begin
   declare @jsonRequest nvarchar(max),
           @id_proveedor int

   select @id_proveedor = JSON_VALUE(@json, '$.id_proveedor')
	

   select @jsonRequest = (
      select p.url_endpoint
	    from proveedores p
	  where p.id_proveedor = @id_proveedor
	   and dar_baja = 'N'
	    for json auto, without_array_wrapper
   )
   select @jsonRequest as proveedor
end
go

execute getDatosProveedor @json = 
'
  {
    "id_proveedor" : "1"
  }
'
go

-- Determinar la cantidad de proveedores en el sistema
create or alter procedure determinar_proveedores
as
begin
  declare @jsonRequest nvarchar(max)

  select @jsonRequest = (
     select id_proveedor, cuit, url_endpoint
	   from proveedores
	 where dar_baja = 'N'
	   for json auto
  )
  select @jsonRequest as proveedores
end
go

execute determinar_proveedores
go



-- Determinar la cantidad de proveedores que necesiten actualizar los precios de sus productos mayo a un dia
create or alter procedure determinar_proveedores_para_actualizar_precios
as
begin
   declare @jsonRequest  nvarchar(max)

   select @jsonRequest = (
   
      select id_proveedor, cuit, url_endpoint
	     from proveedores
	  where fecha_ultima_actualizacion < DATEADD(DAY, -1, GETDATE())
	    and dar_baja = 'N'
	     for json auto
    )
   select @jsonRequest as proveedores_a_actualizar
end
go


execute determinar_proveedores_para_actualizar_precios
go



/*En este procedimiento solo entran los proveedores que necesitan actualizarse su precio , para eso se determinan en otro procedimiento*/
create or alter procedure actualizar_precios
(
  @json nvarchar(max)
)
as
begin
   create table #tempProductosPrecioProveedor
   (
     cod_barra varchar(100),
	 precio    int,
	 id_proveedor int
   )

   insert into #tempProductosPrecioProveedor(cod_barra, precio, id_proveedor)
   select * 
      from openjson(@json)
	  with
	  (
	    cod_barra varchar(100),
		precio int,
		id_proveedor int
	  )

   update ppp
     set ppp.baja = 'S'
	   from precios_productos_proveedores ppp
	     join #tempProductosPrecioProveedor tppp
		   on ppp.cod_barra = tppp.cod_barra
		    and ppp.id_proveedor = tppp.id_proveedor
	  where ppp.cod_barra = tppp.cod_barra and ppp.id_proveedor = tppp.id_proveedor


   insert into precios_productos_proveedores (cod_barra, id_proveedor, fecha_alta, precio)
   select tp.cod_barra, tp.id_proveedor, CURRENT_TIMESTAMP, tp.precio
      from #tempProductosPrecioProveedor tp

   update p
     set p.fecha_ultima_actualizacion = CURRENT_TIMESTAMP
    from proveedores p
	  join #tempProductosPrecioProveedor tp
	    on p.id_proveedor = tp.id_proveedor
end
go


select * 
  from productos_proveedores

select *
  from precios_productos_proveedores


execute actualizar_precios @json = 
'[
   {
    "cod_barra":"abc123",
	"precio":500,
	"id_proveedor": 2
   },
   {
    "cod_barra":"qwe456",
	"precio":1000,
	"id_proveedor": 2
   },
   {
    "cod_barra":"cod_barra3",
	"precio": 500,
	"id_proveedor": 1
   }
 ]
'
go




/* array el pedido tiene que tener todos los productos que estan sin stock al proveedor*/
/*ejemplo: productos sin stock :   leche y */
/* Otra forma de hacerlo es que el procedimiento almacenado reciba un json con todos los proveedores del sistema y
    cuando voy a determinar el precio minimo de un producto , que vaya a preguntar a todos los proveedores 1 por 1 cuando lo tenga
	que lo guarde y asi con todos los productos - idea a considerar*/

	
update productos set stock_actual = 8 
go

select *
 from pedidos

select *
 from productos
go

select *
 from precios_productos_proveedores

create or alter procedure armado_pedido
as
begin

   declare @jsonResponse nvarchar(max)

   /*productos_sin_stock*/
   create table #productos_falta_stock 
   (
      cod_barra  varchar(100)
   )

   insert into #productos_falta_stock
   select p.cod_barra
      from productos p
	  where p.stock_actual <= p.stock_minimo
   
   /*determinar_precio_minimo_productos*/

   create table #proveedor_precio_minimo
   (
       id_proveedor int,
       cod_barra    varchar(100),
	   precio int
   )

   insert into #proveedor_precio_minimo (id_proveedor, cod_barra, precio)
   select ppp.id_proveedor, ppp.cod_barra, ppp.precio
     from precios_productos_proveedores ppp
	   join #productos_falta_stock pfs
	     on ppp.cod_barra = pfs.cod_barra
		where ppp.baja = 'N'
		and ppp.precio = ( select MIN(ppp1.precio)
		                      from precios_productos_proveedores ppp1
							 where ppp1.cod_barra = ppp.cod_barra
							   and baja = 'N'
		                  )
	   order by ppp.id_proveedor

	
   -- select * from #proveedor_precio_minimo 

   
   if exists ( select 1 
                 from #proveedor_precio_minimo p1
                group by p1.cod_barra
				having COUNT(*) > 1
			 )
   begin
     create table #ProveedoresPromedio (
      id_proveedor int,
	  cod_barra varchar(100),
	  promedio_calificaciones float
     )

     insert into #ProveedoresPromedio (id_proveedor, cod_barra, promedio_calificaciones)
     select p1.id_proveedor, p1.cod_barra, AVG(vaep.valor_ponderado) as promedio_calificaciones
	   from #proveedor_precio_minimo p1
	     join pedidos p 
		   on p1.id_proveedor = p.id_proveedor
		    join valores_escala_proveedor vaep
			  on p.id_proveedor = vaep.id_proveedor
			    and p.id_escala = vaep.id_escala
				and p.valor_escala = vaep.valor_escala
				 join escalas_proveedores ep
				   on vaep.id_proveedor = ep.id_proveedor
				   and vaep.id_escala = ep.id_escala
	   where ep.baja = 'N' 
	     and p.valor_escala is not null 
		 and p1.cod_barra in (
		       select cod_barra
			     from #proveedor_precio_minimo
				 group by cod_barra, precio
				 having COUNT(*) > 1
		    )
	   group by p1.id_proveedor, p1.cod_barra
	
	 -- select *
	 --   from #ProveedoresPromedio 

	
	  create table #ValorCodBarraRepetido (
      id_proveedor int,
	  cod_barra varchar(100),
      )

	  insert into #ValorCodBarraRepetido
	   select top 1 ppp.id_proveedor, ppp.cod_barra
		     from #proveedor_precio_minimo ppp
			where id_proveedor not in (
             select top 1 pr.id_proveedor
              from proveedores pr
               join #proveedor_precio_minimo pp
                on pr.id_proveedor = pp.id_proveedor
			  order by pr.cuit asc
           ) and cod_barra not in (
		            select cod_barra from #proveedor_precio_minimo
					group by cod_barra
					having COUNT(*) = 1
		     ) 	  


	   if not exists ( select 1 from #ProveedoresPromedio)
	   begin
	      delete from #proveedor_precio_minimo
		  where exists (
		      select 1
               from #ValorCodBarraRepetido vcr
              where vcr.cod_barra = #proveedor_precio_minimo.cod_barra
               and vcr.id_proveedor = #proveedor_precio_minimo.id_proveedor
		  )
		  /*
		  where id_proveedor in (
		     select top 1 ppp.id_proveedor
		     from #proveedor_precio_minimo ppp
			where id_proveedor not in (
             select top 1 pr.id_proveedor
              from proveedores pr
               join #proveedor_precio_minimo pp
                on pr.id_proveedor = pp.id_proveedor
			  order by pr.cuit asc
           ) and cod_barra not in (
		            select cod_barra from #proveedor_precio_minimo
					group by cod_barra
					having COUNT(*) = 1
		     ) 	  
		  )
		  */

		--   select *
		 --    from #proveedor_precio_minimo
	   end
	   else
	   begin
	      delete from #proveedor_precio_minimo
           where id_proveedor not in (
             select top 1 pp.id_proveedor
              from #ProveedoresPromedio pp
             where pp.cod_barra = #proveedor_precio_minimo.cod_barra
              order by pp.promedio_calificaciones desc -- Ordenar por promedio ponderado en orden descendente
           ) and cod_barra not in (
		            select cod_barra from #proveedor_precio_minimo
					group by cod_barra
					having COUNT(*) = 1
		     )  
	   end

	  --  select *
	  --     from #proveedor_precio_minimo

	   drop table #ProveedoresPromedio
   end

   
   --select *
    -- from #proveedor_precio_minimo
   
   if exists (
      select 1
	     from pedidos p
		  where p.anulado != 'S'
   )
   begin
      delete from #proveedor_precio_minimo
	  where exists (
        select 1
        from detalle_pedidos dp
        join pedidos p on dp.id_pedido = p.id_pedido
		where p.estado_pedido = 'Pendiente'
        and dp.cod_barra = #proveedor_precio_minimo.cod_barra
        and dp.id_proveedor = #proveedor_precio_minimo.id_proveedor
	 )
   end
   else
   begin
     delete from #proveedor_precio_minimo
	  where exists (
        select 1
        from detalle_pedidos dp
        join pedidos p on dp.id_pedido = p.id_pedido
		where p.estado_pedido = 'Pendiente'
        and dp.cod_barra = #proveedor_precio_minimo.cod_barra
        and dp.id_proveedor = #proveedor_precio_minimo.id_proveedor
	 )
   end
   
  /*
   delete from #proveedor_precio_minimo
   where exists (
     select 1
        from detalle_pedidos dp
        join pedidos p on dp.id_pedido = p.id_pedido
		where p.estado_pedido = 'Pendiente'
        and dp.cod_barra = #proveedor_precio_minimo.cod_barra
        and dp.id_proveedor = #proveedor_precio_minimo.id_proveedor
   )  or exists (
       select 1
	     from pedidos p 
		  where p.anulado != 'S'
       
   ) 
  */


 
  -- select *
  --  from #proveedor_precio_minimo 
	
   if exists ( select 1 from #proveedor_precio_minimo   )
   begin
     
	
     
     declare @tempTablaPedidos table (id_pedido int, id_proveedor int)
     /*Registrar pedido*/
     insert into pedidos ( fecha_registro_pedido, id_proveedor)
     output inserted.id_pedido, inserted.id_proveedor into @tempTablaPedidos
     select CURRENT_TIMESTAMP, pp.id_proveedor
       from #proveedor_precio_minimo pp
	   group by pp.id_proveedor

     insert into detalle_pedidos (id_pedido, cod_barra, id_proveedor, precio_unitario, cantidad)
     select t.id_pedido, pp.cod_barra, pp.id_proveedor, pp.precio, 11
       from @tempTablaPedidos t
	    join #proveedor_precio_minimo pp
	      on t.id_proveedor = pp.id_proveedor


	  select @jsonResponse = (
	     select p.id_pedido, p.id_proveedor, 
		        ( select dp.cod_barra, dp.precio_unitario, dp.cantidad
				     from #proveedor_precio_minimo ppp
					   join detalle_pedidos dp
					     on ppp.id_proveedor = dp.id_proveedor
						 and ppp.cod_barra = dp.cod_barra
					 where dp.id_pedido = p.id_pedido
					 for json path
				) as detalle_pedidos
		    from pedidos p
			  join #proveedor_precio_minimo ppp
			    on p.id_proveedor = ppp.id_proveedor
		   where p.fecha_prevista_entrega is null and p.fecha_real_entrega is null
		   group by p.id_pedido, p.id_proveedor
		   for json path
	  )
   end

    
   select @jsonResponse as pedidos

end
go

execute armado_pedido
go

select *
 from pedidos

select *
 from detalle_pedidos

-- Para cada Pedido insertado cuando me comunico con el proveedor me envia su id de pedido el cual seria el id_pedido_proveedor
create or alter procedure updatePedidos_id_pedido_proveedor
(
  @jsonRequest nvarchar(max)
)
as
begin
    declare @id_pedido int,
	        @id_proveedor int,
			@id_pedido_proveedor int

    select  @id_pedido = JSON_VALUE(@jsonRequest, '$.id_pedido'),
	        @id_proveedor = JSON_VALUE(@jsonRequest, '$.id_proveedor'),
			@id_pedido_proveedor = JSON_VALUE(@jsonRequest, '$.id_pedido_proveedor')


   update p
      set p.id_pedido_proveedor = @id_pedido_proveedor
     from pedidos p
	where p.id_pedido = @id_pedido
	  and p.id_proveedor = @id_proveedor

end
go


-- Obtener el Id_pedido_proveedor
create or alter procedure getId_pedido_proveedor
(
  @jsonRequest nvarchar(max)
)
as
begin
   declare @jsonResponse nvarchar(max),
           @id_proveedor int

   select @id_proveedor = JSON_VALUE(@jsonRequest, '$.id_proveedor')
		 

   select @jsonResponse = (
        select p.id_pedido_proveedor as id_pedido,
		       p.estado_pedido 
		   from pedidos p
          where p.id_proveedor = @id_proveedor
		    and p.anulado = 'N'
			and p.estado_pedido != 'Entregado'
		for json path, without_array_wrapper
   )

   select @jsonResponse as pedidos_proveedor
end
go

execute getId_pedido_proveedor @jsonRequest =
'
 { 
  "id_proveedor" : 1
 }
'
go


-- Consultar los pedidos del proveedor y actualizar si hay un cambio
create or alter procedure consultar_pedidos_proveedor_y_actualizar
(
  @jsonRequest nvarchar(max)
)
as
begin

   create table #pedidoConsulta(
       id_pedido_proveedor int,
	   estado_pedido varchar(15),
       id_proveedor int,
       fecha_prevista_entrega datetime,
       fecha_real_entrega datetime,
	   productos_no_entregados nvarchar(max) 
	)

	create table #prodTemp (
	   id_pedido_proveedor integer,
	   cod_barra varchar(100),
	   motivo_cancelacion varchar(100)
	)
 
   insert into  #pedidoConsulta(id_pedido_proveedor, estado_pedido, id_proveedor, fecha_prevista_entrega, fecha_real_entrega, productos_no_entregados)
   select id_pedido_proveedor,
          estado_pedido, 
		  id_proveedor,
		  fecha_prevista_entrega,
		  fecha_real_entrega,
		  productos_no_entregados
	 from openjson(@jsonRequest)
	   with(
	         id_pedido_proveedor int '$.id_pedido_proveedor',
	         estado_pedido varchar(15) '$.estado_pedido',
             id_proveedor int  '$.id_proveedor',
             fecha_prevista_entrega datetime '$.fecha_prevista_entrega',
             fecha_real_entrega datetime '$.fecha_real_entrega',
	         productos_no_entregados nvarchar(max) '$.productos_no_entregados' as json
	       )
	
   insert into  #prodTemp(id_pedido_proveedor, cod_barra, motivo_cancelacion)
   select id_pedido_proveedor, cod_barra, motivo_cancelacion
    from #pedidoConsulta
	    cross apply openjson(productos_no_entregados)
		with (
		       cod_barra          varchar(100)  '$.cod_barra',
			   motivo_cancelacion varchar(100)  '$.motivo_cancelacion'
			 )

   update p
     set p.estado_pedido = pc.estado_pedido,
	     p.fecha_prevista_entrega = pc.fecha_prevista_entrega,
		 p.fecha_real_entrega = pc.fecha_real_entrega
	  from pedidos p
	   join #pedidoConsulta pc
         on p.id_pedido_proveedor = pc.id_pedido_proveedor
          join detalle_pedidos dp
		    on p.id_pedido = dp.id_pedido
			  and p.id_proveedor = dp.id_proveedor
		        join #prodTemp pt
				  on dp.cod_barra = pt.cod_barra

   update dp 
     set dp.motivo_cancelacion = pt.motivo_cancelacion
     from detalle_pedidos dp
	    join #prodTemp pt
		  on dp.cod_barra = pt.cod_barra
	   join pedidos p
	     on p.id_pedido_proveedor = pt.id_pedido_proveedor 
	   /*
   update p 
    set p.estado_pedido = j.estado_pedido,
	    p.fecha_prevista_entrega = j.fecha_prevista_entrega,
		p.fecha_real_entrega = j.fecha_real_entrega,
		dp.motivo_cancelacion = t.motivo_cancelacion
     from pedidos p
	   join openjson(@jsonRequest)
	   with(
	    id_pedido_proveedor int,
		estado_pedido varchar(15),
        id_proveedor int,
        fecha_prevista_entrega datetime,
        fecha_real_entrega datetime,
		productos_no_entregados nvarchar(max)  '$.productos_no_entregados' as json
		 
	   ) j 
	   on p.id_pedido_proveedor = j.id_pedido_proveedor
	   and p.id_proveedor = j.id_proveedor
	    	   join detalle_pedidos dp
	     on p.id_pedido = dp.id_pedido
		  and p.id_proveedor = dp.id_proveedor
		  join #prodMotivoTemp t
		    on dp.cod_barra = t.cod_barra
*/

	update prod
      set prod.stock_actual = prod.stock_actual + dp.cantidad
       from productos prod
        join detalle_pedidos dp
        on prod.cod_barra = dp.cod_barra
         join pedidos p
         on p.id_pedido = dp.id_pedido
          and p.id_proveedor = dp.id_proveedor
           join openjson(@jsonRequest)
           with (
            id_pedido_proveedor int,
            estado_pedido varchar(15),
            id_proveedor int,
            fecha_prevista_entrega datetime,
            fecha_real_entrega datetime
           ) j
            on p.id_pedido_proveedor = j.id_pedido_proveedor
            and p.id_proveedor = j.id_proveedor
      where j.estado_pedido = 'Entregado';

   
     select id_pedido
       from pedidos
	 where id_pedido_proveedor in (select id_pedido_proveedor from openjson(@jsonRequest)
	                                with (id_pedido_proveedor int ) ) 
	 for json auto
	
end
go

select *
 from pedidos

select *
 from detalle_pedidos


execute consultar_pedidos_proveedor_y_actualizar @jsonRequest = 
'[  
  {
    "id_pedido_proveedor":4, 
	"estado_pedido": "Pendiente", 
	"productos_no_entregados":
	 [
	  {
	   "cod_barra": "0123456", 
	   "motivo_cancelacion": "Se cancelo por que nos quedamos sin stock"
	  }
	 ]
  }
 ]
'
execute consultar_pedidos_proveedor_y_actualizar @jsonRequest =
'[
  { 
    "id_pedido_proveedor": 1007,
    "id_proveedor": 1,
    "estado_pedido": "En proceso",
    "fecha_prevista_entrega": "2024-09-12T00:00:00",
    "fecha_real_entrega": null,
	"productos_no_entregados":
	   [
	     {
		   "cod_barra":"0123456", 
		   "prod_sin_posibilidad_entrega": "S", 
		   "motivo_cancelacion": "Se cancelo por que nos quedamos sin stock"
		  }
	   ]
  }
]'


execute consultar_pedidos_proveedor_y_actualizar @jsonRequest = 
' 
  [
   {
    "id_pedido_proveedor":1007,
	"estado_pedido":"Pendiente",
	"id_proveedor":1
   }
  ]
'
go


-- Cancelar el Pedido
create or alter procedure cancelar_pedido
(
  @jsonRequest nvarchar(max)
)
as
begin
    declare @jsonResponse nvarchar(max),
	        @id_pedido int
	       
	select @id_pedido = JSON_VALUE(@jsonRequest, '$.id_pedido')

    update p
	    set p.anulado = 'S'
	   from pedidos p
      where p.id_pedido = @id_pedido
	   and p.estado_pedido = 'Pendiente'

    
	select @jsonResponse = (
	   select p.id_pedido_proveedor as id_pedido,
	          pro.url_endpoint
	      from pedidos p
		    join proveedores pro
			  on p.id_proveedor = pro.id_proveedor
	    where p.id_pedido = @id_pedido
		for json path, without_array_wrapper
	)

	select @jsonResponse as pedido_cancelado
end
go

execute cancelar_pedido @jsonRequest =
'
 { 
  "id_pedido" : 1065
 }
'
go


-- Actualizar los valores Ponderados
create or alter procedure actualizar_ponderacion
(
  @jsonRequest nvarchar(max)
)
as
begin
    declare @id_proveedor int, 
	        @valor_escala int,
	        @descripcion_valor varchar(100),
	     	@valor_ponderado int,
			@jsonResponse nvarchar(max)

	select @id_proveedor = JSON_VALUE(@jsonRequest, '$.id_proveedor'),
	       @valor_escala = JSON_VALUE(@jsonRequest, '$.valor_escala'),
	       @descripcion_valor = JSON_VALUE(@jsonRequest, '$.descripcion_valor'),
		   @valor_ponderado = JSON_VALUE(@jsonRequest, '$.valor_ponderado')
	 
	 update vep
	  set vep.valor_ponderado = @valor_ponderado
	   from valores_escala_proveedor vep
	     join escalas_proveedores ep
		   on vep.id_escala = ep.id_escala
		    and vep.id_proveedor = ep.id_proveedor
		where ep.baja = 'N' 
		 and vep.id_proveedor = @id_proveedor
		 and vep.descripcion_valor = @descripcion_valor
		 and vep.valor_escala = @valor_escala

	select @jsonResponse = (
	   select *
	     from valores_escala_proveedor vae
		where  vae.id_proveedor = @id_proveedor
		  and vae.valor_escala = @valor_escala
		  and vae.descripcion_valor = @descripcion_valor
		  for json auto, without_array_wrapper
	)

	select @jsonResponse as ponderacion_actualizada
end
go

execute actualizar_ponderacion @jsonRequest = '
  {
    "id_proveedor": 1,
	"valor_escala": 10,
	"descripcion_valor": "Excelente",
	"valor_ponderado": "100"
  }
'
go




-- Listar los Valores de la escala del proveedor
create or alter procedure listar_valores_escala_proveedor
(
  @jsonRequest nvarchar(max)
)
as
begin
    declare @id_proveedor int
	
	select @id_proveedor = JSON_VALUE(@jsonRequest, '$.id_proveedor')


	select vaep.valor_escala, 
	       vaep.descripcion_valor
	  from valores_escala_proveedor vaep
	    join escalas_proveedores ep
		  on vaep.id_escala = ep.id_escala
		    and vaep.id_proveedor = ep.id_proveedor
			  join proveedores p
			    on vaep.id_proveedor = p.id_proveedor
	 where vaep.id_proveedor = @id_proveedor
	    and ep.baja = 'N'
		and p.dar_baja = 'N'
	  order by vaep.valor_escala
end
go



execute listar_valores_escala_proveedor @jsonRequest = '
  {
    "id_proveedor": 1
  }
'
go


-- Listar los valores ponderados junto con los valores de la escala del proveedor
create or alter procedure listar_valores_ponderados_para_calificar
(
  @jsonRequest nvarchar(max)
)
as
begin
    declare @id_proveedor int
	
	select @id_proveedor = JSON_VALUE(@jsonRequest, '$.id_proveedor')


	select vaep.valor_escala, 
	       vaep.descripcion_valor,
		   vaep.valor_ponderado
	  from valores_escala_proveedor vaep
	    join escalas_proveedores ep
		  on vaep.id_escala = ep.id_escala
		    and vaep.id_proveedor = ep.id_proveedor
			  join proveedores p
			    on vaep.id_proveedor = p.id_proveedor
	 where vaep.id_proveedor = @id_proveedor
	    and ep.baja = 'N'
		and p.dar_baja = 'N'
	  order by vaep.valor_ponderado
end
go


execute listar_valores_ponderados_para_calificar @jsonRequest = '
  {
    "id_proveedor": 1
  }
'
go

--Mostrar las ponderaciones que maneja el sistema
create or alter procedure  mostrar_ponderaciones
as
begin
    declare @valores_a_mostrar table (valor_ponderado_muestra int)


    insert into @valores_a_mostrar 
    VALUES (1),
	       (2), 
		   (3), 
		   (4), 
		   (5)

    select *
	   from @valores_a_mostrar
   
end
go

execute mostrar_ponderaciones
go



-- Calificar el Pedido (Evaluar)
create or alter procedure calificar_pedido
(
  @jsonRequest nvarchar(max)
)
as
begin
   declare @id_pedido int,
           @valor_escala int,
		   @descripcion_valor varchar(100),
		   @valor_ponderado int,
		   @jsonResponse nvarchar(max)

    select @id_pedido = JSON_VALUE(@jsonRequest, '$.id_pedido'),
	       @valor_escala = JSON_VALUE(@jsonRequest, '$.valor_escala'),
	       @valor_ponderado = JSON_VALUE(@jsonRequest, '$.valor_ponderado'),
		   @descripcion_valor = JSON_VALUE(@jsonRequest, '$.descripcion_valor')

    
	update p
	   set p.valor_escala =   @valor_escala,
	       p.id_escala = ep.id_escala
	  from pedidos p
	    join valores_escala_proveedor vaep 
		  on p.id_proveedor = vaep.id_proveedor
		   and @valor_escala = vaep.valor_escala
		    join escalas_proveedores ep
			  on p.id_proveedor = ep.id_proveedor
	   where vaep.valor_ponderado = @valor_ponderado
	      and vaep.descripcion_valor = @descripcion_valor
		  and p.id_pedido =  @id_pedido
		  and ep.baja = 'N'

	 select @jsonResponse = (
	    select p.id_pedido_proveedor as id_pedido, 
		       p.valor_escala, 
			   ep.id_escala_proveedor as id_escala,
			   pro.url_endpoint
     	  from pedidos p
	        join valores_escala_proveedor vaep 
		      on p.id_proveedor = vaep.id_proveedor
			    and @valor_escala = vaep.valor_escala
		        join escalas_proveedores ep
			      on p.id_proveedor = ep.id_proveedor
				   join proveedores pro
				     on p.id_proveedor = pro.id_proveedor
	     where vaep.valor_ponderado = @valor_ponderado
	        and vaep.descripcion_valor = @descripcion_valor
		    and p.id_pedido =  @id_pedido
		    and ep.baja = 'N'
		  for json path,  without_array_wrapper
	  )

	  select @jsonResponse as calificacion_pedido_proveedor
end
go

  


execute calificar_pedido @jsonRequest = '
  {"id_pedido": 168,
   "valor_ponderado": 10,
   "descripcion_valor": "Excelente"
  }
'
go


-- Estado de "Calificacion" para el pedido una vez que se lo evalua
create or alter procedure estado_post_calificacion
(
   @jsonRequest nvarchar(max)
)
as
begin
   declare @id_pedido int

   select @id_pedido = JSON_VALUE(@jsonRequest, '$.id_pedido')

   update p
      set p.estado_pedido = 'Calificado'
      from pedidos p
	 where p.id_pedido = @id_pedido
	  and p.estado_pedido = 'Entregado'
end
go

execute estado_post_calificacion @jsonRequest = '
  {
    "id_pedido": 1
  }
'
go


-- Generar las estadisticas de efectividad del proveedor
create or alter procedure generar_efectividad_proveedor
as
begin

   select pro.id_proveedor, 
          pro.nombre, 
		  AVG(vaep.valor_ponderado) as calificacion, 
		  COUNT(distinct p.id_pedido) as cantidad_pedidos_proveedor,
		  MAX(DATEDIFF(day, p.fecha_prevista_entrega, p.fecha_real_entrega)) as maxima_tardanza_dias,
		  MIN(DATEDIFF(day, p.fecha_prevista_entrega, p.fecha_real_entrega)) as minima_tardanza_dias,
		  AVG(DATEDIFF(day, p.fecha_prevista_entrega, p.fecha_real_entrega)) as promedio_demora_dias
   from proveedores pro
     join pedidos  p
	    on pro.id_proveedor = p.id_proveedor
	     join escalas_proveedores ep
		  on pro.id_proveedor = ep.id_proveedor
		   join valores_escala_proveedor vaep
		    on pro.id_proveedor = vaep.id_proveedor
			 and ep.id_escala = vaep.id_escala
	  where p.anulado = 'N'
	   and p.estado_pedido = 'Calificado'
	   and ep.baja = 'N'
	group by pro.id_proveedor, pro.nombre

end
go

execute generar_efectividad_proveedor




/*
insert into pedidos (fecha_registro_pedido, id_proveedor, id_escala, valor_escala)
values(CURRENT_TIMESTAMP, 1, 1, 10)

insert into pedidos (fecha_registro_pedido, id_proveedor, id_escala, valor_escala)
values(CURRENT_TIMESTAMP, 2, 2, 4)


/*Con el tema de los precios producto repetidos me lo hace bien siempre y cuando haya un valor_escala para hacer lo de mejor proveedor , si el valor escala es null me trae los 2 proveedores (falta agregar eso)*/

delete p
   from precios_productos_proveedores p
 where p.cod_barra = 'cod_barra3' and p.id_proveedor = '1'

update vaep
     set vaep.valor_ponderado = 40
   from valores_escala_proveedor vaep
  where vaep.id_proveedor = 2 and vaep.id_escala = 2 and vaep.valor_escala = 4

select * 
  from valores_escala_proveedor

select * 
  from productos

update p
   set p.stock_actual = 5
   from productos p
     where p.cod_barra = 'qwe456' 


select * 
  from productos_proveedores


insert into productos_proveedores 
values('cod_barra4', '2')

insert into precios_productos_proveedores (cod_barra, id_proveedor, fecha_alta, precio)
values('abc123',1, CURRENT_TIMESTAMP, 600)




update pp
  set pp.precio = 350 
  from precios_productos_proveedores pp
   where pp.id_proveedor = 2 and pp.cod_barra = 'cod_barra3'
go

delete 
   from precios_productos_proveedores 
   where precios_productos_proveedores.cod_barra = 'abc123'
go


select * 
  from precios_productos_proveedores
go

select * 
  from productos_proveedores


insert into productos_proveedores 
values('abc123', '1')

insert into precios_productos_proveedores (cod_barra, id_proveedor, fecha_alta, precio)
values('abc123',1, CURRENT_TIMESTAMP, 800)

update pp
  set pp.precio = 500 
  from precios_productos_proveedores pp
   where pp.id_proveedor = 2 and pp.cod_barra = 'abc123'
go

delete 
   from precios_productos_proveedores 
   where precios_productos_proveedores.cod_barra = 'abc123'
go




/*pruebas */
select * 
  from productos
    where stock_actual <= stock_minimo
go

update p
  set p.stock_actual = 9
   from productos p 
   where p.cod_barra = 'abc123'
go



update p
   set p.imagen = 'cafeDolca.png'
  from productos p
   where p.nombre = 'Cafe Dolca'



update p
	 set  p.imagen = 'lecheIlolay.png'
  from productos p
   where p.nombre = 'Leche Ilolay'


update p
   set p.imagen = 'yogurManfrey.png'
  from productos p
   where p.nombre = 'Yogur Manfrey'


update p
   set p.imagen = 'galletasTerrabusi.png'
  from productos p
   where p.nombre = 'Galletas Terrabusi'

select *
  from productos

select * 
  from pedidos

select * 
  from detalle_pedidos
go

select * 
   from pedidos


select *
 from productos_proveedores
go

select * 
  from precios_productos_proveedores
go

select * 
from proveedores
go

select * 
 from escalas_proveedores

select *  
  from valores_escala_proveedor


update ppp
  set ppp.fecha_baja = 'N'
    from precios_productos_proveedores ppp
	where ppp.cod_barra = 'qwe456' and ppp.id_proveedor = 1

delete from productos 
delete from precios_productos_proveedores where id_proveedor = 7
delete from precios_productos_proveedores where cod_barra = 'abc123'  and id_proveedor = 1 and precio = 500 
delete from precios_productos_proveedores where cod_barra = 'qwe456'  and id_proveedor = 1 and precio = 1000 
delete from productos_proveedores where id_proveedor =  7
delete from proveedores where id_proveedor = 7

delete from escalas_proveedores where id_proveedor = 7

delete from valores_escala_proveedor where id_proveedor = 7




insert into productos
values ( 'cod_barra1','producto3','imagen3',10,10)


insert into productos
values ( 'cod_barra2','producto4','imagen5',8,10)

update productos
set stock_actual = 9
  where cod_barra = 'cod_barra1'




  
     /*
     insert into detalle_pedidos (id_pedido, cod_barra, id_proveedor, precio_unitario, cantidad)
       select @id_pedido, pp.cod_barra, pp.id_proveedor, pp.precio, 11
        from #proveedor_precio_minimo pp
	    join precios_productos_proveedores ppp
		  on pp.id_proveedor = ppp.id_proveedor
		    and pp.cod_barra = ppp.cod_barra
		where ppp.fecha_baja = 'N'
     */


/*
declare @valor_ponderado int  
declare @valor_escala int = 70
execute obtener_valor_ponderado @valor_escala, @valor_ponderado output 

select @valor_ponderado as valorponderado

go
*/



update productos
  set stock_actual = 8
go

select *
 from productos


 
select * from usuarios
go

select *
 from proveedores
go

select *
 from productos
go


delete from usuarios where id_usuario = 2

insert into usuarios
values ('Agusxin', '123456')

select *
 from usuarios

select *
 from pedidos

delete from detalle_pedidos
delete from pedidos

select *
 from proveedores
go

select *
  from productos

select *
 from pedidos

delete from detalle_pedidos
delete from pedidos


update p
  set p.stock_actual = 8
  from productos p
where cod_barra = 'abc123' or cod_barra = 'qwe456'
 
select *
  from proveedores

update p
   set p.url_endpoint = 'http://localhost:8080/Proveedor2-SOAP/services/ProveedorInfoWSPort?wsdl'
  from proveedores p
 where p.id_proveedor = 3


delete from proveedores

select *
  from productos

select * 
  from precios_productos_proveedores

select *
 from productos_proveedores
 go

 

select *
  from proveedores

delete from precios_productos_proveedores
delete from productos_proveedores

select *
 from productos

select *
  from valores_escala_proveedor

delete proveedores
  where id_proveedor = 1

select *
  from pedidos

select *
  from detalle_pedidos

  
select *
 from pedidos

delete detalle_pedidos
delete pedidos

declare @id_escala int
declare @id_proveedor int = 1
execute obtener_id_escala @id_proveedor, @id_escala output

select @id_escala as id_escala
go



select *
  from productos_proveedores
select *
    from precios_productos_proveedores 

update pp
   set pp.fecha_ultima_actualizacion = (DATEADD(DAY, -1, GETDATE()))
  from proveedores pp
  where pp.id_proveedor between 1 and 2 
go
*/