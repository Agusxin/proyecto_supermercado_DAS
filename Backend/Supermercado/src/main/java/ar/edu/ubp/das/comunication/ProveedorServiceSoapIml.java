package ar.edu.ubp.das.comunication;


import javax.jws.WebMethod;
import javax.jws.WebService;



@WebService(targetNamespace = "http://ws.das.ubp.edu.ar/")
public interface ProveedorServiceSoapIml {

	@WebMethod(operationName = "getProveedorInfo", action = "urn:GetProveedorInfo")
	String datosProveedor();
	
	
	@WebMethod(operationName = "getProductosInfo", action = "urn:GetProductosInfo")
	String productosProveedor(String token);
	
	
	@WebMethod(operationName = "getPedidosInfo", action = "urn:GetPedidosInfo")
	String get_pedidos(String json, String token); 
	
	@WebMethod(operationName = "insertarPedidos", action = "urn:InsertarPedidos")
	String insertarPedido(String json, String token);
	
	@WebMethod(operationName = "cancelarPedido", action = "urn:CancelarPedido")
	String cancelar_pedido(String json, String token);
	
	@WebMethod(operationName = "calificarPedido", action = "urn:CalificarPedido")
	String calificar_pedido(String json, String token);
}

