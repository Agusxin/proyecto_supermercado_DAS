package ar.edu.ubp.das.comunication;

import org.springframework.stereotype.Component;


@Component
public class ProveedorServiceFactory {
	
	
	public ProveedorService crearProveedorService(String url) {
		if(url.contains("?wsdl")) {
			return new ProveedorSoapClient();
		}else {
			return new ProveedorRestClient();
		}
	}
}
