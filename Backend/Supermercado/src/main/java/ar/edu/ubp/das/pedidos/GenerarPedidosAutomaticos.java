package ar.edu.ubp.das.pedidos;



import java.util.ArrayList;
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
public class GenerarPedidosAutomaticos {
	
	@Autowired
	private SupermercadoRepository SupermercadoRepository;
    
    @Autowired
	private  ProveedorServiceFactory proveedorServiceFactory;
	
	Gson gson = new Gson();
	

	public ResponseEntity<?> getProveedoresActualizarPrecios(){
		try {
			String jsonResponse = SupermercadoRepository.determinar_proveedores_actualizar_precio();
			return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
		}catch(Exception e) {
			System.out.println("Exception: " + e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
		
	public void actualizar_precios(){
		
	  ResponseEntity<?> proveedores_a_actualizar = getProveedoresActualizarPrecios();
	  String proveedoresJsonActualizar = (String) proveedores_a_actualizar.getBody();
	  
	  //System.out.print(proveedoresJsonActualizar);
	  
	  if(proveedoresJsonActualizar == null || proveedoresJsonActualizar.isEmpty()) {
		  System.out.println("No hay proveedores a actualizar");
		  return;
		 // return ResponseEntity.noContent().build();
	  } 
	  List<Map<String, Object>> proveedoresActualizar = gson.fromJson(proveedoresJsonActualizar, new TypeToken<List<Map<String, Object>>>(){}.getType());  
	  List<Map<String, Object>> productosProveedorActualizar = new ArrayList<Map<String,Object>>();
		
	  for (Map<String, Object> proveedor : proveedoresActualizar) {
	  	
		int id_proveedor = (int)((double) proveedor.get("id_proveedor"));
		String url_endpoint = (String) proveedor.get("url_endpoint");
	
		String token = SupermercadoRepository.getToken(url_endpoint);
		//System.out.print("token de generar pedidos:" + token);
		ProveedorService proveedorService = proveedorServiceFactory.crearProveedorService(url_endpoint);
		String productosProveedorJson = proveedorService.productosProveedor(url_endpoint, token);
			
		List<Map<String, Object>> productosProveedor = gson.fromJson(productosProveedorJson, new TypeToken<List<Map<String, Object>>>(){}.getType());
        		
		for (Map<String, Object> producto  : productosProveedor) {
			int precio = (int)((double) producto.get("precio"));
            producto.put("id_proveedor", id_proveedor);
            producto.put("precio", precio);
            productosProveedorActualizar.add(producto);
        }
      }
	  
	  
	  String productosProveedorActualizarJson = gson.toJson(productosProveedorActualizar);
	  
	  System.out.println("ProductosProveedorActualizar:" + productosProveedorActualizarJson);
	  SupermercadoRepository.actualizar_precios(productosProveedorActualizarJson);
	//  return new ResponseEntity<>(SupermercadoRepository.actualizar_precios(productosProveedorActualizarJson), HttpStatus.OK);
	}
	
	

	public void registrar_pedido(){
	  
	  actualizar_precios();
	
	  
	
	  String pedidosJson = SupermercadoRepository.armado_pedido();
	  
	  
	  System.out.println("pedidos:" + pedidosJson);
	  if(pedidosJson == null) {
		  System.out.println("El proveedor no tiene pedidos a registrar");
		  return;
	  }
	  if(pedidosJson != null) {
        List<Map<String, Object>> pedidos = gson.fromJson(pedidosJson, new TypeToken<List<Map<String, Object>>>(){}.getType());

        //System.out.println(pedidos);
	    
	    for (Map<String, Object> pedido: pedidos) {
		  int id_pedido = (int)((double) pedido.get("id_pedido"));
		  int id_proveedor = (int)((double) pedido.get("id_proveedor"));
		
		  Map<String, Object> idProveedorMap = new HashMap<>();
		  idProveedorMap.put("id_proveedor", id_proveedor);
		  
		  String jsonIdProveedor = gson.toJson(idProveedorMap);
		  
		  List<Map<String, Object>> detallePedidos = (List<Map<String, Object>>) pedido.get("detalle_pedidos");
		  
		  System.out.println("Detalle Pedidos" + detallePedidos);
		  
		  if(detallePedidos == null) {
			  continue;
		  }
		  
		  
	      for (Map<String, Object> detalle : detallePedidos) {
		        int precio_unitario = (int)((double) detalle.get("precio_unitario")); 
		        int cantidad = (int)((double) detalle.get("cantidad")); 
		        detalle.put("precio_unitario", precio_unitario);
		        detalle.put("cantidad", cantidad);
		  }
	
	      String Json_url_proveedor = SupermercadoRepository.getDatosProveedor(jsonIdProveedor);
		  Map<String, Object> url_map = gson.fromJson(Json_url_proveedor, Map.class);
		  
		  String url_proveedor = (String) url_map.get("url_endpoint");
		    
		  System.out.println("Id Proveedor: " + jsonIdProveedor + " pedido: " + detallePedidos + " url: " + url_proveedor);
			 
		  String jsonDetallePedidos = gson.toJson(detallePedidos);
	
	      //Insertar pedido 
		  String token = SupermercadoRepository.getToken(url_proveedor);
		  ProveedorService proveedorService = proveedorServiceFactory.crearProveedorService(url_proveedor);
		  String jsonId_pedido_proveedor = proveedorService.insertar_pedidos(url_proveedor, jsonDetallePedidos, token);
		  
		  
		  System.out.println("id_pedido_proveedor:" + jsonId_pedido_proveedor);

		  //Update id_pedido_proveedor
		  Map<String, Object> id_pedido_proveedorMap = gson.fromJson(jsonId_pedido_proveedor, Map.class);
		  int id_pedido_proveedor = (int)((double)id_pedido_proveedorMap.get("id_pedido"));
		  
		  Map<String, Object > mapUpdatePedido = new HashMap<>();
		  mapUpdatePedido.put("id_pedido", id_pedido);
		  mapUpdatePedido.put("id_proveedor", id_proveedor);
		  mapUpdatePedido.put("id_pedido_proveedor", id_pedido_proveedor);
		  
		  String jsonUpdatePedidoIdProveedor = gson.toJson(mapUpdatePedido);
		 
		  SupermercadoRepository.actualizar_Pedido_id_pedido_proveedor(jsonUpdatePedidoIdProveedor);
	   }
	    
	  }
	  
	}
}
