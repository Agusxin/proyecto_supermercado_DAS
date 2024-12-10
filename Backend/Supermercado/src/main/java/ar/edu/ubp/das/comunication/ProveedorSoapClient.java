package ar.edu.ubp.das.comunication;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.stereotype.Component;


@Component
public class ProveedorSoapClient implements ProveedorService{
	
	private ProveedorServiceSoapIml proveedorServiceSoapIml;

	   
	public void configurarUrl(String url) {
	   JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
	   factory.setServiceClass(ProveedorServiceSoapIml.class);
	   
	   factory.setAddress(url);
	   this.proveedorServiceSoapIml = (ProveedorServiceSoapIml) factory.create();
	}

	@Override
	public String datosProveedor(String url) {
		configurarUrl(url);
		return proveedorServiceSoapIml.datosProveedor();
	}

	@Override
	public String productosProveedor(String url, String token) {
		configurarUrl(url);
		return proveedorServiceSoapIml.productosProveedor(token);
	}

	@Override
	public String insertar_pedidos(String url, String json, String token) {
		configurarUrl(url);
		String respuesta = proveedorServiceSoapIml.insertarPedido(json, token);
		
		System.out.print(respuesta);
		
		return respuesta;
		
	}

	@Override
	public String get_pedidos(String url, String json, String token) {
		configurarUrl(url);
		return proveedorServiceSoapIml.get_pedidos(json, token);
	}

	@Override
	public String cancelar_pedido(String url, String json, String token) {
		configurarUrl(url);
		return proveedorServiceSoapIml.cancelar_pedido(json, token);
	}

	
	@Override
	public String calificar_pedido(String url, String json, String token) {
		configurarUrl(url);
		return proveedorServiceSoapIml.calificar_pedido(json, token);
	}
}

