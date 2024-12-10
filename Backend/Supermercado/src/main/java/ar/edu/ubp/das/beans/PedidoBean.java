package ar.edu.ubp.das.beans;

import java.sql.Date;

public class PedidoBean {
	
	private int id_pedido;
	private String estado_pedido;
	private Date fecha_registro_pedido;
	private Date fecha_prevista_entrega;
	private Date fecha_real_entrega;
	private int id_proveedor;
	private String nombre_proveedor;
	private Integer id_escala;
	private Integer valor_escala;
	
	public int getId_pedido() {
		return id_pedido;
	}
	public void setId_pedido(int id_pedido) {
		this.id_pedido = id_pedido;
	}
	
	public String getEstado_pedido() {
		return estado_pedido;
	}
	public void setEstado_pedido(String estado_pedido) {
		this.estado_pedido = estado_pedido;
	}
	
	public Date getFecha_registro_pedido() {
		return fecha_registro_pedido;
	}
	public void setFecha_registro_pedido(Date fecha_registro_pedido) {
		this.fecha_registro_pedido = fecha_registro_pedido;
	}
	
	public Date getFecha_prevista_entrega() {
		return fecha_prevista_entrega;
	}
	public void setFecha_prevista_entrega(Date fecha_prevista_entrega) {
		this.fecha_prevista_entrega = fecha_prevista_entrega;
	}
	
	public Date getFecha_real_entrega() {
		return fecha_real_entrega;
	}
	public void setFecha_real_entrega(Date fecha_real_entrega) {
		this.fecha_real_entrega = fecha_real_entrega;
	}
	public int getId_proveedor() {
		return id_proveedor;
	}
	public void setId_proveedor(int id_proveedor) {
		this.id_proveedor = id_proveedor;
	}
	

	public String getNombre_proveedor() {
		return nombre_proveedor;
	}
	public void setNombre_proveedor(String nombre_proveedor) {
		this.nombre_proveedor = nombre_proveedor;
	}
	
	
	public Integer getId_escala() {
		return id_escala;
	}
	public void setId_escala(Integer id_escala) {
		this.id_escala = id_escala;
	}
	
	public Integer getValor_escala() {
		return valor_escala;
	}
	public void setValor_escala(Integer valor_escala) {
		this.valor_escala = valor_escala;
	}
	
	
}
