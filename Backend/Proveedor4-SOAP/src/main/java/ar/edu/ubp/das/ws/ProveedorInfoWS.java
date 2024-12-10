package ar.edu.ubp.das.ws;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.naming.AuthenticationException;

import org.apache.cxf.interceptor.Fault;

import ar.edu.ubp.das.db.Dao;
import ar.edu.ubp.das.db.DaoFactory;

@WebService(targetNamespace = "http://ws.das.ubp.edu.ar/", portName = "ProveedorInfoWSPort", serviceName = "ProveedorInfoWSService")
public class ProveedorInfoWS {
	

	
	@WebMethod(operationName = "getToken", action = "urn:GetToken")
	@WebResult(name = "return")
	public String getToken() {
		   try(Dao<String, String, String> dao = DaoFactory.getDao("Token", "ar.edu.ubp.das")) {
			  List<String> tokens = dao.select(null);
			  return (tokens.isEmpty()) ? null: tokens.get(0);
		   } catch (Exception e) {
			   throw new Fault(e);
		     }
	}	
	
	private boolean isTokenValid(String token) throws AuthenticationException {
		String TokenAlmacenado = getToken();
		return TokenAlmacenado != null && TokenAlmacenado.equals(token);
	}
	
	
	@WebMethod(operationName = "getProveedorInfo", action = "urn:GetProveedorInfo")
	@WebResult(name = "return")
	public List<String> getProveedorInfo(){
	   try(Dao<String, String, String> dao = DaoFactory.getDao("datosProveedor", "ar.edu.ubp.das")) {
		  return dao.select(null);
	   } catch (Exception e) {
		   throw new Fault(e);
	     }
	}
	
    @WebMethod(operationName = "getProductosInfo", action = "urn:GetProductosInfo")
	@WebResult(name = "return")
	public List<String> getProductosInfo(@WebParam(name = "arg0") String token) throws AuthenticationException{
	  
	  if(!isTokenValid(token)) {
		  throw new AuthenticationException("Token inválido");
	  }
		
		
	  try(Dao<String, String, String> dao = DaoFactory.getDao("Productos", "ar.edu.ubp.das")) {
		  return dao.select(null);
	  } catch (Exception e) {
		  throw new Fault(e);
		}
	}
	
	
	@WebMethod(operationName = "getPedidosInfo", action = "urn:GetPedidosInfo")
	@WebResult(name = "return")
	public List<String> getPedidosInfo(@WebParam(name = "arg0") String json, @WebParam(name = "arg1") String token) throws AuthenticationException{
		
	  if(!isTokenValid(token)) {
		throw new AuthenticationException("Token inválido");
	  }
		
	  try(Dao<String, String, String> dao = DaoFactory.getDao("Pedidos", "ar.edu.ubp.das")) {
		return dao.select(json);
	  } catch (Exception e) {
		  throw new Fault(e);
	    }
	}
	
	
	@WebMethod(operationName = "insertarPedidos", action = "urn:InsertarPedidos")
	@WebResult(name = "return")
	public String insertarPedidos(@WebParam(name = "arg0") String json,@WebParam(name = "arg1") String token) throws AuthenticationException{
	  
	  if(!isTokenValid(token)) {
		throw new AuthenticationException("Token inválido");
	  }
	  
	  System.out.println("JSON recibido: " + json); 

	  
	  try(Dao<String, String, String> dao = DaoFactory.getDao("InsertarPedidos", "ar.edu.ubp.das")) {
		 List<String> result = dao.select(json);
		 System.out.println("resultado select:" + result);
	     return result.get(0);
	  } catch (Exception e) {
		    e.printStackTrace();
			throw new Fault(e);
		}	
	}
	
	@WebMethod(operationName = "cancelarPedido", action = "urn:CancelarPedido")
	@WebResult(name = "return")
	public String cancelarPedido(@WebParam(name = "arg0") String json, @WebParam(name = "arg1") String token) throws AuthenticationException {
	   if(!isTokenValid(token)) {
		 throw new AuthenticationException("Token inválido");
	   }
	   
	   System.out.println("JSON recibido: " + json); 
  
	   try(Dao<String, String, String> dao = DaoFactory.getDao("CancelarPedido", "ar.edu.ubp.das")) {
		 String result = dao.update(json);
		 System.out.println("resultado update cancelarPedido:" + result);
		 return result;
	   } catch (Exception e) {
			e.printStackTrace();
			throw new Fault(e);
		 }	
	}
	
	@WebMethod(operationName = "calificarPedido", action = "urn:CalificarPedido")
	@WebResult(name = "return")
	public String calificarPedido(@WebParam(name = "arg0") String json, @WebParam(name = "arg1") String token) throws AuthenticationException {
		if(!isTokenValid(token)) {
			throw new AuthenticationException("Token inválido");
		}

		System.out.println("JSON recibido: " + json); 
	  
		try(Dao<String, String, String> dao = DaoFactory.getDao("CalificarPedido", "ar.edu.ubp.das")) {
		  String result = dao.update(json);
		  System.out.println("resultado update pedido calificado:" + result);
		  return result;
		} catch (Exception e) {
		    e.printStackTrace();
		    throw new Fault(e);
		  }	
	}

}
