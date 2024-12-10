package ar.edu.ubp.das.beans;

public class ProductoBean {
	
	private String cod_barra;
	private String nombre;
	private int precio;
	private String imagen;
	
	
	public String getCod_barra() {
		return cod_barra;
	}
	public void setCod_barra(String cod_barra) {
		this.cod_barra = cod_barra;
	}
	
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
	public int getPrecio() {
		return precio;
	}
	public void setPrecio(int precio) {
		this.precio = precio;
	}
	
	
	public String getImagen() {
		return imagen;
	}
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

}
