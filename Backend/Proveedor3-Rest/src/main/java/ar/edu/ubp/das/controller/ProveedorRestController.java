package ar.edu.ubp.das.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.ubp.das.repository.ProveedorRestRepository;


@RestController
@RequestMapping(path="/proveedor",
                produces={MediaType.APPLICATION_JSON_VALUE}
               )
public class ProveedorRestController {
	
	@Autowired
	ProveedorRestRepository ProveedorRestRepository;
	
	@GetMapping(path= "/datosProveedor")
	public ResponseEntity<?> getInsertProveedor(){
		return new ResponseEntity<>(ProveedorRestRepository.obtenerDatosProveedor(), HttpStatus.OK);
	}
	
	@GetMapping(path= "/productosProveedor")
	public ResponseEntity<?> getProductosProveedor(){
		return new ResponseEntity<>(ProveedorRestRepository.obtenerProductosProveedor(), HttpStatus.OK);
	}
	
	@PostMapping(path="/insertar_pedidos")
	public ResponseEntity<?> insertar_pedidos(@RequestBody String jsonPedidos){
		return new ResponseEntity<>(ProveedorRestRepository.insertar_pedidos(jsonPedidos), HttpStatus.OK);
	}
	
	@PostMapping(path= "/pedidosProveedor")
	public ResponseEntity<?> get_pedidos(@RequestBody String jsonPedidos){
		return new ResponseEntity<>(ProveedorRestRepository.get_pedidos(jsonPedidos), HttpStatus.OK);
	}


	@PostMapping(path= "/cancelarPedido")
	public ResponseEntity<?> cancelar_pedido(@RequestBody String jsonPedido){
		return new ResponseEntity<>(ProveedorRestRepository.cancelar_pedido(jsonPedido), HttpStatus.OK);
	}
	
	@PostMapping(path= "/calificarPedido")
	public ResponseEntity<?> calificar_pedido(@RequestBody String jsonPedido){
		return new ResponseEntity<>(ProveedorRestRepository.calificar_pedido(jsonPedido), HttpStatus.OK);
	}
}
