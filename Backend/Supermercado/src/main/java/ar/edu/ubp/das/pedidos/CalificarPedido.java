package ar.edu.ubp.das.pedidos;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import ar.edu.ubp.das.comunication.ProveedorService;
import ar.edu.ubp.das.comunication.ProveedorServiceFactory;
import ar.edu.ubp.das.repository.SupermercadoRepository;


@Component
public class CalificarPedido {
	
	
	@Autowired
	private SupermercadoRepository SupermercadoRepository;
	
	@Autowired
	private  ProveedorServiceFactory proveedorServiceFactory;
	

	Gson gson = new Gson();
	
	
	
	public ResponseEntity<?> calificarPedido(String jsonPedidoCalificado){
		
		
		String pedidoCalificado = SupermercadoRepository.calificar_pedido(jsonPedidoCalificado);
		
		if(pedidoCalificado == null || pedidoCalificado.isEmpty()) {
			System.out.println("El pedido no tiene una ponderacion actualizada");
			return ResponseEntity.noContent().build();
		}
		
		System.out.println("calificacionPedido:" + pedidoCalificado);
		
		JsonObject jsonObjectPedido = JsonParser.parseString(pedidoCalificado).getAsJsonObject();
		
		String url_proveedor = jsonObjectPedido.remove("url_endpoint").toString();

		String json_calificar_pedido = gson.toJson(jsonObjectPedido);
		
		System.out.println(url_proveedor);
		System.out.println(json_calificar_pedido);
		  
	    
	    url_proveedor = url_proveedor.replace("\"", "");
	    
	    
	    	
	    System.out.println("voy a entrar al token:");
		String token = SupermercadoRepository.getToken(url_proveedor);
		System.out.println(token);
		ProveedorService proveedorService = proveedorServiceFactory.crearProveedorService(url_proveedor);
		proveedorService.calificar_pedido(url_proveedor, json_calificar_pedido, token);
	
		return null;
	}
	
	/*
	 * 
	 * filto pedidos , estado ,etc
	 * 
	 * consulta cuanto tiempo tarda en entregar efectividad - cual es promedio ponderado , mejor clasificacion peor clasificacion
	 * 
	 *
	 * 
	 * 1 malo   -    inserte que quiere : ponderacion
 	 * 2
	 * 2
	 * 2
	 * tablita de valores escala - ponderacion 
	 * 
	 * 
	 * 
	 * calificar pedido 
	 * 
	 * malo               10 
	 * refgular            8
	 * tablita de calificar - valor_ponderado
	 * 
	 * 
	 * 
	 * 
	 * */
	
	
  
	
}
