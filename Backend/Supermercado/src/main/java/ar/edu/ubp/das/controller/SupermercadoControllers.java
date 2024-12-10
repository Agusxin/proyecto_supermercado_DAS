package ar.edu.ubp.das.controller;


import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.repository.config.RepositoryNameSpaceHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.ubp.das.beans.DetallePedidoBean;
import ar.edu.ubp.das.beans.EfectividadProveedorBean;
import ar.edu.ubp.das.beans.MostrarPonderacionesBean;
import ar.edu.ubp.das.beans.PedidoBean;
import ar.edu.ubp.das.beans.ProductoBean;
import ar.edu.ubp.das.beans.ProveedorBean;
import ar.edu.ubp.das.beans.UsuarioLoginBean;
import ar.edu.ubp.das.beans.ValorEscalaProveedorBean;
import ar.edu.ubp.das.comunication.ProveedorService;
import ar.edu.ubp.das.comunication.ProveedorServiceFactory;
import ar.edu.ubp.das.pedidos.CalificarPedido;
import ar.edu.ubp.das.pedidos.CancelarPedido;
import ar.edu.ubp.das.pedidos.ConsultarPedidos;
import ar.edu.ubp.das.pedidos.GenerarPedidosAutomaticos;
import ar.edu.ubp.das.repository.SupermercadoRepository;

@RestController
@RequestMapping(path="/supermercado")
public class SupermercadoControllers {
	
	@Autowired
	private SupermercadoRepository SupermercadoRepository;
    
	@Autowired
	private  ProveedorServiceFactory proveedorServiceFactory;
	
	private CancelarPedido cancelarPedido;
	private CalificarPedido calificarPedido;
	
	@Autowired
	private GenerarPedidosAutomaticos pedidosAutomaticos; 
	
	@Autowired
	private ConsultarPedidos consultarPedidos;
	
	
	public SupermercadoControllers(CancelarPedido cancelarPedido, CalificarPedido calificarPedido) {
		this.cancelarPedido = cancelarPedido;
		this.calificarPedido = calificarPedido;
	}
	
	
	
	@PostMapping("/login")
	public ResponseEntity<UsuarioLoginBean> login(@RequestBody String jsonRequest) {
		return new ResponseEntity<>(SupermercadoRepository.login(jsonRequest), HttpStatus.OK);
	}
	
	
	@GetMapping("/listarProveedores")
	public ResponseEntity<List<ProveedorBean>> listarProveedores() {
		return new ResponseEntity<>(SupermercadoRepository.listarProveedores(), HttpStatus.OK);
	}
	
	@GetMapping("/listarPedidos")
	public ResponseEntity<List<PedidoBean>> listarPedidos() {
		return new ResponseEntity<>(SupermercadoRepository.listarPedidos(), HttpStatus.OK);
	}
	
	@PostMapping("/listarProductosProveedor")
	public ResponseEntity<List<ProductoBean>> listarProductosProveedor(@RequestBody String json) {
		return new ResponseEntity<>(SupermercadoRepository.listarProductosProveedor(json), HttpStatus.OK);
	}
	
	@PostMapping("/listarDetallePedido")
	public ResponseEntity<List<DetallePedidoBean>> listarDetallePedido(@RequestBody String json){
		return new ResponseEntity<>(SupermercadoRepository.listar_detalle_pedido(json), HttpStatus.OK);
	}
	
	@PostMapping("/listarValorEscalaProveedor")
	public ResponseEntity<List<ValorEscalaProveedorBean>> listarValorEscalaProveedor(@RequestBody String json){
		return new ResponseEntity<>(SupermercadoRepository.listar_valores_escala_proveedor(json), HttpStatus.OK);
	}
	
	@PostMapping("/listarValorPonderadoProveedor")
	public ResponseEntity<List<ValorEscalaProveedorBean>> listarValorPonderadoProveedor(@RequestBody String json){
		return new ResponseEntity<>(SupermercadoRepository.listar_valores_ponderados_para_calificar(json), HttpStatus.OK);
	}
	
	@GetMapping("/listarValoresPonderadosMuestra")
	public ResponseEntity<List<MostrarPonderacionesBean>> listarValorPonderadoMuestras(){
		return new ResponseEntity<>(SupermercadoRepository.listar_valores_ponderados_de_muestra(), HttpStatus.OK);
	}
	
	
	@GetMapping(path= "/insertarProveedor")
	public ResponseEntity<ProveedorBean> insertInfoProveedor(@RequestParam String url){
	    ProveedorService proveedorService = proveedorServiceFactory.crearProveedorService(url);
		String resultado = proveedorService.datosProveedor(url);
		return new ResponseEntity<>(SupermercadoRepository.insertProveedor(resultado), HttpStatus.OK);
	}
	
	@GetMapping(path= "/productosProveedor")
	public ResponseEntity<?> productosProveedor(@RequestParam String url){
		String token = SupermercadoRepository.getToken(url);
	    ProveedorService proveedorService = proveedorServiceFactory.crearProveedorService(url);
		String resultado = proveedorService.productosProveedor(url, token);
		return new ResponseEntity<>(resultado, HttpStatus.OK);
	}
	
	@GetMapping(path= "/getDatosProveedor")
	public ResponseEntity<?> datosProveedor(@RequestBody String json){
		return new ResponseEntity<>(SupermercadoRepository.getDatosProveedor(json), HttpStatus.OK);
	}
	
	@PostMapping(path="/bajaProveedor")
	public ResponseEntity<?> bajaProveedor(@RequestBody Map<String, Integer> idProveedorMap){
		System.out.println(idProveedorMap);
		try {
			int idProveedor = idProveedorMap.get("id_proveedor");
			SupermercadoRepository.bajaProveedor( idProveedor);
			
			Map<String, String> response = new HashMap<>();
		    response.put("message", "Proveedor dado de baja exitosamente ");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			 Map<String, String> errorResponse = new HashMap<>();
			 errorResponse.put("error", "Error al dar de baja al proveedor: " + e.getMessage());
			return new ResponseEntity<>(errorResponse,  HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PostMapping(path= "/cancelarPedido")
	public ResponseEntity<?> cancelarPedido(@RequestBody String json){
	    return cancelarPedido.cancelar_pedido(json);
	}
	
	
	@PostMapping(path="/registrarPedidos")
	public void registrarPedidos() {
	  pedidosAutomaticos.registrar_pedido();
	}
	
	@PostMapping(path="/consultarPedidos")
	public void consultarPedidos() {
		consultarPedidos.consultarPedidos();
	}
	
	@PostMapping(path="/actualizarPonderacion")
	public ResponseEntity<?> actualizarPonderacion(@RequestBody String json){
	   return new ResponseEntity<>(SupermercadoRepository.actualizar_ponderacion(json), HttpStatus.OK);
	}
	
	@PostMapping(path="/calificarPedido")
	public ResponseEntity<?> calificarPedido(@RequestBody String json){
		return calificarPedido.calificarPedido(json);
	}
	
	@PostMapping(path="/estadoPostCalificacion")
	public ResponseEntity<String> postEstado(@RequestBody String json){
	   return new ResponseEntity<String>(SupermercadoRepository.estado_post_calificacion(json), HttpStatus.OK);
	}
	
	@GetMapping(path="/generarEfectividadProveedores")
	public ResponseEntity<List<EfectividadProveedorBean>> efectividadProveedor(){
		 return new ResponseEntity<>(SupermercadoRepository.efectividadProveedor(), HttpStatus.OK);
	}
	
	@GetMapping("/{imageName}")
	public ResponseEntity<Resource> getImage(@PathVariable String imageName) throws IOException  {
	    Path path = Paths.get("C:/Users/Agux/Desktop/Proyecto DAS/Desarrollo/BD/Imagenes Productos/" + imageName); 
	    Resource resource = new UrlResource(path.toUri());
	    return ResponseEntity.ok()
	            .contentType(MediaType.IMAGE_PNG) 
	            .body(resource);
	}
	
	
	

	
	
	
	
	
	
	
	/*
	@GetMapping(path= "/realizarPedidos")
	public ResponseEntity<?> realizarPedidos(){
		return pedidosAutomaticos.registrar_pedido();
	}
	*/
	
	
	/*
	
	*/
}


