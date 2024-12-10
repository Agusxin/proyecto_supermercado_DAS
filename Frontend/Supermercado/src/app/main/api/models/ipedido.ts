export interface IPedido {
    id_pedido: number;
	estado_pedido: string;
	fecha_registro_pedido: Date;
	fecha_prevista_entrega?: Date;
	fecha_real_entrega?: Date;
	id_proveedor: number;
	nombre_proveedor: string;
	id_escala?: number;
	valor_escala?: number;
}
