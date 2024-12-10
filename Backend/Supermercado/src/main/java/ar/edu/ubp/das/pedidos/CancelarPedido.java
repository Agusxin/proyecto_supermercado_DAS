package ar.edu.ubp.das.pedidos;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import ar.edu.ubp.das.comunication.ProveedorService;
import ar.edu.ubp.das.comunication.ProveedorServiceFactory;
import ar.edu.ubp.das.repository.SupermercadoRepository;

@Component
public class CancelarPedido {
	

	
	@Autowired
	private SupermercadoRepository SupermercadoRepository;
	
	@Autowired
	private  ProveedorServiceFactory proveedorServiceFactory;
		
    
	Gson gson = new Gson();
	
	
	public ResponseEntity<?> cancelar_pedido(String jsonId_pedido){
		
		String pedido_cancelado = SupermercadoRepository.cancelar_pedido(jsonId_pedido);
		
		System.out.print(pedido_cancelado);
		
		
	
		Map<String, Object> map_pedido_cancelado = gson.fromJson(pedido_cancelado, new TypeToken<Map<String, Object>>() {}.getType());
		
		
		
		
		//id_pedido_proveedor_cancelado
		int id_pedido_proveedor = (int)((double) map_pedido_cancelado.get("id_pedido"));
		
		Map<String, Object> map_id_pedido_proveedor = new HashMap<>();
		map_id_pedido_proveedor.put("id_pedido", id_pedido_proveedor);
	    
		String json_id_pedido_proveedor = gson.toJson(map_id_pedido_proveedor);
		
		System.out.print(json_id_pedido_proveedor);
		
		String url_proveedor = (String) map_pedido_cancelado.get("url_endpoint");
		
		System.out.println(url_proveedor);
		
		String token = SupermercadoRepository.getToken(url_proveedor);
		ProveedorService proveedorService = proveedorServiceFactory.crearProveedorService(url_proveedor);
		proveedorService.cancelar_pedido(url_proveedor, json_id_pedido_proveedor, token);
		
		return null;		
	}
}
