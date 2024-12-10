use Supermercado
go


/* este procedimiento traera la informacion que me manda el proveedor y la insertamos en el abm de proveedores del supermercado 
   el proveedor me manda json con la info sobre sus datos =   cuit , nombre, direccion, email, telefono, token, tipo_servicio, url_endpoint
   y tambien me manda la id_escala_proveedor , nombre_escala , y los valores de esa escala
   el supermercado recibe la data en json y la transforma a tabla sql y registra el proveedor con los datos = cuit, nombre, direccion, email, telefono, token, tipo_servicio, url_endpoint
   los campos
*/



/*Procedimiento insertar proveedores*/
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
           @url_endpoint  varchar(50)

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

   insert into precios_productos_proveedores( cod_barra, id_proveedor, fecha_alta, precio, fecha_baja)
   select JSON_VALUE(value, '$.cod_barra') as cod_barra,
          @id_proveedor as id_proveedor,
		  CURRENT_TIMESTAMP,
		  JSON_VALUE(VALUE, '$.precio') as precio,
		  CURRENT_TIMESTAMP
   from openjson(@json, '$.productos')
  
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


/*Eliminar proveedor*/
create or alter procedure eliminar_proveedor
(
  @id_proveedor  int
)
as
begin
  update proveedores
   set dar_baja = 'S'
     where id_proveedor = @id_proveedor
	   and dar_baja = 'N'
end
go

/*Ejemplo */
execute eliminar_proveedor @id_proveedor = 1 
go



/*Listar proveedores*/
create or alter procedure listar_proveedores
as
begin
   select id_proveedor,cuit, nombre, direccion, email, telefono, tipo_servicio, url_endpoint
      from proveedores
	 where dar_baja = 'N'
end
go

/*Ejemplo*/
execute listar_proveedores
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

declare @id_escala int
declare @id_proveedor int = 1
execute obtener_id_escala @id_proveedor, @id_escala output

select @id_escala as id_escala
go



create or alter procedure obtener_productos_falta_stock
as
begin
  declare @json nvarchar(max)

  select @json = (
     select *
	    from productos 
		 where stock_actual < stock_minimo
  )

  


  
end
go




select * 
from productos
go

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


delete from productos 
delete from precios_productos_proveedores where id_proveedor = 7
delete from productos_proveedores where id_proveedor =  7
delete from proveedores where id_proveedor = 7

delete from escalas_proveedores where id_proveedor = 7

delete from valores_escala_proveedor where id_proveedor = 7





insert into productos
values ( 'cod_barra1','producto3','imagen3',10,10)


update productos
set stock_actual = 9
  where cod_barra = 'cod_barra1'



/*
declare @valor_ponderado int  
declare @valor_escala int = 70
execute obtener_valor_ponderado @valor_escala, @valor_ponderado output 

select @valor_ponderado as valorponderado

go
*/


/* 
   valor_escala     valor_ponderado
      0 al 10  :          1 
	 11 al 20 :           2
	 21 al 30 :           3
	 31 al 40 :           4
	 41 al 50 :           5
	 51 al 60 :           6
	 61 al 70 :           7
	 71 al 80 :           8
	 81 al 90 :           9
	 91 al 100 :          10	 
*/

