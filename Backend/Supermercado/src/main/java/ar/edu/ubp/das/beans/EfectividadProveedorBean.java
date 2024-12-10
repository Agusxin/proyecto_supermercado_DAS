package ar.edu.ubp.das.beans;

public class EfectividadProveedorBean {
	
	private int id_proveedor;
	private String nombre;
	private int calificacion;
	private int cantidad_pedidos_proveedor;
	private int maxima_tardanza_dias;
	private int minima_tardanza_dias;
	private int promedio_demora_dias;
	
	
	
	public int getId_proveedor() {
		return id_proveedor;
	}
	public void setId_proveedor(int id_proveedor) {
		this.id_proveedor = id_proveedor;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public int getCalificacion() {
		return calificacion;
	}
	public void setCalificacion(int calificacion) {
		this.calificacion = calificacion;
	}
	
	public int getCantidad_pedidos_proveedor() {
		return cantidad_pedidos_proveedor;
	}
	public void setCantidad_pedidos_proveedor(int cantidad_pedidos_proveedor) {
		this.cantidad_pedidos_proveedor = cantidad_pedidos_proveedor;
	}
	
	public int getMaxima_tardanza_dias() {
		return maxima_tardanza_dias;
	}
	public void setMaxima_tardanza_dias(int maxima_tardanaza_dias) {
		this.maxima_tardanza_dias = maxima_tardanaza_dias;
	}
	
	public int getMinima_tardanza_dias() {
		return minima_tardanza_dias;
	}
	public void setMinima_tardanza_dias(int minima_tardanza_dias) {
		this.minima_tardanza_dias = minima_tardanza_dias;
	}
	
	public int getPromedio_demora_dias() {
		return promedio_demora_dias;
	}
	public void setPromedio_demora_dias(int promedio_demora_dias) {
		this.promedio_demora_dias = promedio_demora_dias;
	}

}
