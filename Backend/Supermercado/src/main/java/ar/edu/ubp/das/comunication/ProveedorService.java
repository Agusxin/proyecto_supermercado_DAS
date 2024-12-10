package ar.edu.ubp.das.comunication;

public interface ProveedorService {
	String datosProveedor(String url);
	String productosProveedor(String url, String token);
	String insertar_pedidos(String url, String json, String token);
	String get_pedidos(String url,String json, String token);
	String cancelar_pedido(String url, String json, String token);
	String calificar_pedido(String url, String json, String token);
}
