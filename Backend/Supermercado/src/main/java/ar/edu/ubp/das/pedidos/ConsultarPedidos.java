package ar.edu.ubp.das.pedidos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import ar.edu.ubp.das.comunication.ProveedorService;
import ar.edu.ubp.das.comunication.ProveedorServiceFactory;
import ar.edu.ubp.das.repository.SupermercadoRepository;

@Component
public class ConsultarPedidos {
	
	
	@Autowired
	private SupermercadoRepository SupermercadoRepository;
	
	@Autowired
	private  ProveedorServiceFactory proveedorServiceFactory;
		
	
	Gson gson = new Gson();
	
   
	public ResponseEntity<?> getProveedores(){
		try {
			String jsonResponse = SupermercadoRepository.determinar_proveedores();
			return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
		}catch(Exception e) {
			System.out.println("Exception: " + e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	//@Scheduled(cron = "*/10 * * * * *")
	public void consultarPedidos(){
		
	   ResponseEntity<?> proveedoresResponse = getProveedores();
	   String proveedoresJson = (String) proveedoresResponse.getBody();
	   
	  // System.out.println("proveedores Consulta json:" + proveedoresJson);
	   
	   List<Map<String, Object>> proveedores = gson.fromJson(proveedoresJson, new TypeToken<List<Map<String, Object>>>(){}.getType());
	   
	   if( proveedores == null || proveedores.isEmpty()) {
		   System.out.println("No hay proveedores registrados en el sistema.");
		   return;
	   }
	
	   for (Map<String, Object> proveedor : proveedores) {
		   
		// System.out.println("proveedor:" + proveedor);
		   
		 Map<String, Object> idProveedorMap = new HashMap<>();
		 idProveedorMap.put("id_proveedor", (int)((double) proveedor.get("id_proveedor"))); // Convertir a int

		 String json_id_proveedor = gson.toJson(idProveedorMap); 
		 String id_pedidos_proveedor = SupermercadoRepository.obtener_id_pedidos_proveedor(json_id_proveedor);
		 
		 System.out.println("id_pedido_proveedor:" + id_pedidos_proveedor);
		    
		 
		 
		 if(id_pedidos_proveedor == null) {
			continue;
		 }
		 
		 //System.out.println(proveedor.get("url_endpoint"));
		
	     String url_proveedor = (String) proveedor.get("url_endpoint");
	      
	     //System.out.println("url:" + url_proveedor);
	     
	     
	     String token = SupermercadoRepository.getToken(url_proveedor);		
	     
	     //System.out.println(token);
	     
	     //System.out.print("token de consultar pedidos:" + token);
		 ProveedorService proveedorService = proveedorServiceFactory.crearProveedorService(url_proveedor);
		 String jsonPedidos_proveedor = proveedorService.get_pedidos(url_proveedor, id_pedidos_proveedor, token);
		 
		
		 
		// System.out.println("json Pedidos Consultados:" + jsonPedidos_proveedor);
		 
		 List<Map<String, Object>> pedidos = gson.fromJson(jsonPedidos_proveedor, new TypeToken<List<Map<String, Object>>>(){}.getType());
		 
		 if(pedidos == null ||  pedidos.isEmpty()) {
			 continue;
		 }
			
		 System.out.print("pedidos Consultados:" + pedidos);
		 
		 
         for (Map<String, Object> pedido : pedidos) {	
        	 int id_pedido_proveedor = (int)((double) pedido.get("id_pedido_proveedor"));
        	 pedido.put("id_pedido_proveedor", id_pedido_proveedor);
			 pedido.putAll(idProveedorMap);
			 
			 
		 }	  
		 
         String jsonPedidos = gson.toJson(pedidos);
         
         //System.out.println("jsonPedidos:" + jsonPedidos);
         
         //System.out.print("jsonPedidos:" + jsonPedidos);
		 
		 String jsonPedidosActualizados = SupermercadoRepository.consultar_y_actualizar_pedidos(jsonPedidos);
		 
		 if(jsonPedidosActualizados == null || jsonPedidosActualizados.isEmpty()) {
			 System.out.println("Pedidos del proveedor " + idProveedorMap.get("id_proveedor") + " actualizado" );
		 }
	   } 
	}

}
