use Supermercado
go


create or alter trigger check_estado_pedidos_antes_baja
on proveedores
for update
as
begin
    declare @id_proveedor int

	select @id_proveedor = id_proveedor 
	 from inserted

	if exists ( 
	   select *
	     from inserted where dar_baja = 'S'
	)
	begin
	   if  exists (
	      select *
		    from pedidos pe
			 where pe.id_proveedor = @id_proveedor
			 and pe.estado_pedido != 'Calificado'
	   )
	   begin 
	      raiserror('No se puede dar de baja al proveedor porque no todos sus pedidos están en estado Calificado.', 16, 1)
		  rollback transaction
	   end
	end
end
go