package ar.edu.ubp.das.beans;

public class DetallePedidoBean {
	private String imagen;
	private String cod_barra;
	private int cantidad;
	private String motivo_cancelacion;
	
	public String getImagen() {
		return imagen;
	}
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
	
	public String getCod_barra() {
		return cod_barra;
	}
	public void setCod_barra(String cod_barra) {
		this.cod_barra = cod_barra;
	}
	
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
	
	public String getMotivo_cancelacion() {
		return motivo_cancelacion;
	}
	public void setMotivo_cancelacion(String motivo_cancelacion) {
		this.motivo_cancelacion = motivo_cancelacion;
	}

}
