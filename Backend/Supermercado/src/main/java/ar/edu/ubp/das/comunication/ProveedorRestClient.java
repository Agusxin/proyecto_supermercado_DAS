package ar.edu.ubp.das.comunication;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


import org.springframework.stereotype.Service;



@Service
public class ProveedorRestClient implements ProveedorService {
	
	private final HttpClient httpClient;
	

	public ProveedorRestClient() {
		this.httpClient = HttpClient.newHttpClient();
	}


	@Override
	public String datosProveedor(String url) {
		url = url + "/datosProveedor";
		
		
		try {
			HttpRequest request = HttpRequest.newBuilder()
					.uri(new URI(url))
					.GET()
					.build();
			
			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			
			if(response.statusCode() == 200) {
				return response.body();
			}else {
				throw new RuntimeException("Error al obtener la información del proveedor:" + response.statusCode());
			}
		}catch(Exception e) {
			 throw new RuntimeException("Error en la comunicación con el proveedor al traer los datos", e);
		}
	}

	@Override
	public String productosProveedor(String url, String token) {
		url = url + "/productosProveedor";
		
	    try {
			HttpRequest request = HttpRequest.newBuilder()
					.uri(new URI(url))
					.header("Authorization", "Bearer " + token)
					.GET()
					.build();
			
			
			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			
			
			if(response.statusCode() == 200) {
				return response.body();
			}else {
				throw new RuntimeException("Error al obtener la información de los productos del proveedor:" + response.statusCode());
			}
		}catch(Exception e) {
			 throw new RuntimeException("Error en la comunicación con el proveedor", e);
		}
	}


	@Override
	public String insertar_pedidos(String url, String json, String token) {
		url = url + "/insertar_pedidos";
	
		try {
			HttpRequest request = HttpRequest.newBuilder()
					.uri(new URI(url))
					.header("Authorization", "Bearer " + token)
					.header("Content-Type", "application/json")
					.POST(HttpRequest.BodyPublishers.ofString(json))
					.build();
			
			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			
			if(response.statusCode() == 200) {
				return response.body();
			}else {
				throw new RuntimeException("Error al insertar los pedidos:" + response.statusCode());
			}
		}catch(Exception e) {
			 throw new RuntimeException("Error en la comunicación con el proveedor", e);
		}
	}


	@Override
	public String get_pedidos(String url, String json, String token) {
		url = url + "/pedidosProveedor";
		
		try {
			HttpRequest request = HttpRequest.newBuilder()
					.uri(new URI(url))
					.header("Authorization", "Bearer " + token)
					.header("Content-Type", "application/json")
					.POST(HttpRequest.BodyPublishers.ofString(json))
					.build();
			
			
			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			
			
			if(response.statusCode() == 200) {
				return response.body();
			}else {
				throw new RuntimeException("Error al obtener la información de los pedidos del proveedor:" + response.statusCode());
			}
		}catch(Exception e) {
			 throw new RuntimeException("Error en la comunicación con el proveedor", e);
		}
	}


	@Override
	public String cancelar_pedido(String url, String json, String token) {
		url = url + "/cancelarPedido";
		
		try {
			HttpRequest request = HttpRequest.newBuilder()
					.uri(new URI(url))
					.header("Authorization", "Bearer " + token)
					.header("Content-Type", "application/json")
					.POST(HttpRequest.BodyPublishers.ofString(json))
					.build();
			
			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			
			if(response.statusCode() == 200) {
				return response.body();
			}else {
				throw new RuntimeException("Error al cancelar el pedido:" + response.statusCode());
			}
		}catch(Exception e) {
			 throw new RuntimeException("Error en la comunicación con el proveedor", e);
		}
	}


	@Override
	public String calificar_pedido(String url, String json, String token) {
        url = url + "/calificarPedido";
		
		try {
			HttpRequest request = HttpRequest.newBuilder()
					.uri(new URI(url))
					.header("Authorization", "Bearer " + token)
					.header("Content-Type", "application/json")
					.POST(HttpRequest.BodyPublishers.ofString(json))
					.build();
			
			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			
			if(response.statusCode() == 200) {
				return response.body();
			}else {
				throw new RuntimeException("Error al calificar al pedido:" + response.statusCode());
			}
		}catch(Exception e) {
			 throw new RuntimeException("Error en la comunicación con el proveedor", e);
		}
	}

}
