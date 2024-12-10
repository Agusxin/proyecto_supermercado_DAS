package ar.edu.ubp.das.repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import ar.edu.ubp.das.beans.DetallePedidoBean;
import ar.edu.ubp.das.beans.EfectividadProveedorBean;
import ar.edu.ubp.das.beans.MostrarPonderacionesBean;
import ar.edu.ubp.das.beans.PedidoBean;
import ar.edu.ubp.das.beans.ProductoBean;
import ar.edu.ubp.das.beans.ProveedorBean;
import ar.edu.ubp.das.beans.UsuarioLoginBean;
import ar.edu.ubp.das.beans.ValorEscalaProveedorBean;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;


@Repository
public class SupermercadoRepository {
	
	@Autowired
    private JdbcTemplate jdbcTpl;

	
    
	@SuppressWarnings("unchecked")
	public UsuarioLoginBean login(String jsonRequest) {   
    	  SqlParameterSource in = new MapSqlParameterSource()
	 	    .addValue("jsonRequest", jsonRequest);
			
	      SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
	        .withProcedureName("login_usuario")
		    .returningResultSet("login_usuario", BeanPropertyRowMapper.newInstance(UsuarioLoginBean.class));
		
	      Map<String, Object> out = jdbcCall.execute(in);
	  
	      List<UsuarioLoginBean> result = (List<UsuarioLoginBean>) out.get("login_usuario");
	    
	      if (result != null && !result.isEmpty()) {
	        return result.get(0);
	      }else {
	         throw new RuntimeException("No existe ese usuario.");
	      }
	}
	
	
	
    @SuppressWarnings("unchecked")
	public List<ProveedorBean> listarProveedores() {
    	   SqlParameterSource in = new MapSqlParameterSource();
			   	
		   SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
			 .withProcedureName("listar_proveedores")
		     .returningResultSet("listarProveedores", BeanPropertyRowMapper.newInstance(ProveedorBean.class));
		
	       Map<String, Object> out = jdbcCall.execute(in);
	       
	       List<ProveedorBean> result = (List<ProveedorBean>)out.get("listarProveedores");
	    
	       if (result != null && !result.isEmpty()) {
		        return result;
		      }else {
		    	  return new ArrayList<>();
		      }
	}
    
    @SuppressWarnings("unchecked")
	public List<ProductoBean> listarProductosProveedor(String json) {
    	  
    	SqlParameterSource in = new MapSqlParameterSource()
    		 .addValue("jsonRequest", json);
			   	
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
		  .withProcedureName("obtenerProductosProveedor")
		  .returningResultSet("obtenerProductosProveedor", BeanPropertyRowMapper.newInstance(ProductoBean.class));
		
	    Map<String, Object> out = jdbcCall.execute(in);
	       
	    List<ProductoBean> result = (List<ProductoBean>)out.get("obtenerProductosProveedor");
	    
	    if (result != null && !result.isEmpty()) {
		  return result;
		}else {
		  return new ArrayList<>();
		 }
	}
    
    
    
    
    
    @SuppressWarnings("unchecked")
	public List<PedidoBean> listarPedidos() {
	        SqlParameterSource in = new MapSqlParameterSource();
			   	
		    SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
			  .withProcedureName("listar_pedidos")
		      .returningResultSet("listarPedidos", BeanPropertyRowMapper.newInstance(PedidoBean.class));
		
	        Map<String, Object> out = jdbcCall.execute(in);
	    
	        List<PedidoBean> result = (List<PedidoBean>)out.get("listarPedidos");

	        if (result != null && !result.isEmpty()) {
		        return result;
		      }else {
		         return new ArrayList<>();
		       }
	}
    
    
    @SuppressWarnings("unchecked")
	public List<DetallePedidoBean> listar_detalle_pedido(String json){
     	SqlParameterSource in = new MapSqlParameterSource()
    			.addValue("jsonRequest", json);
	   	
	    SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
		  .withProcedureName("listar_detalle_pedido")
	      .returningResultSet("listar_detalle_pedido", BeanPropertyRowMapper.newInstance(DetallePedidoBean.class));
	
        Map<String, Object> out = jdbcCall.execute(in);
    
        List<DetallePedidoBean> result = (List<DetallePedidoBean>)out.get("listar_detalle_pedido");
  
        
        
        if (result != null && !result.isEmpty()) {
        	
	        return result;
	      }else {
	         return new ArrayList<>();
	       }
    }
    
    
    @SuppressWarnings("unchecked")
	public List<ValorEscalaProveedorBean> listar_valores_escala_proveedor( String json){
    	//System.out.println(json);
    	SqlParameterSource in = new MapSqlParameterSource()
    			.addValue("jsonRequest", json);
	   	
	    SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
		  .withProcedureName("listar_valores_escala_proveedor")
	      .returningResultSet("listar_valores_escala_proveedor", BeanPropertyRowMapper.newInstance(ValorEscalaProveedorBean.class));
	
        Map<String, Object> out = jdbcCall.execute(in);
        
        System.out.println(out);
    
        List<ValorEscalaProveedorBean> result = (List<ValorEscalaProveedorBean>)out.get("listar_valores_escala_proveedor");
  
        if (result != null && !result.isEmpty()) {
        	
	        return result;
	      }else {
	         return new ArrayList<>();
	       }
    }
    
    @SuppressWarnings("unchecked")
   	public List<ValorEscalaProveedorBean> listar_valores_ponderados_para_calificar( String json){
       	//System.out.println(json);
       	SqlParameterSource in = new MapSqlParameterSource()
       			.addValue("jsonRequest", json);
   	   	
   	    SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
   		  .withProcedureName("listar_valores_ponderados_para_calificar")
   	      .returningResultSet("listar_valores_ponderados_para_calificar", BeanPropertyRowMapper.newInstance(ValorEscalaProveedorBean.class));
   	
           Map<String, Object> out = jdbcCall.execute(in);
           
           System.out.println(out);
       
           List<ValorEscalaProveedorBean> result = (List<ValorEscalaProveedorBean>)out.get("listar_valores_ponderados_para_calificar");
     
           if (result != null && !result.isEmpty()) {
   	        return result;
   	      }else {
   	         return new ArrayList<>();
   	       }
      }
    
      @SuppressWarnings("unchecked")
   	  public List<MostrarPonderacionesBean> listar_valores_ponderados_de_muestra(){
    	  
       	
   	    SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
   		  .withProcedureName("mostrar_ponderaciones")
   	      .returningResultSet("resultado", BeanPropertyRowMapper.newInstance(MostrarPonderacionesBean.class));
   	
           Map<String, Object> out = jdbcCall.execute();
           
           System.out.println(out);
       
           List<MostrarPonderacionesBean> result = (List<MostrarPonderacionesBean>)out.get("resultado");
     
           System.out.println(result);
           
           
           if (result != null && !result.isEmpty()) {
   	        return result;
   	      }else {
   	         return new ArrayList<>();
   	       }
       }
    
    
       @SuppressWarnings("unchecked")
  	   public String getDatosProveedor(String json) {
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
    	      .withProcedureName("getDatosProveedor");
    			
            SqlParameterSource in = new MapSqlParameterSource()
              .addValue("json", json);
		
            Map<String, Object> out = jdbcCall.execute(in);
    		
            List<Map<String, Object>> resultSet = (List<Map<String, Object>>) out.get("#result-set-1");
	        
	        if (resultSet != null && !resultSet.isEmpty()) {
	        	Map<String, Object> row = resultSet.get(0);
		        String jsonResult = (String)row.get("proveedor");
		        return jsonResult;
		      }else {
		         throw new RuntimeException("No se cargaron los datos del proveedor");
		       }
        }
	
	
	@SuppressWarnings("unchecked")
	public ProveedorBean insertProveedor( String jsonInfo  ) {
		    SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
		  	  .withProcedureName("insert_proveedores")
		      .returningResultSet("proveedorNuevo", BeanPropertyRowMapper.newInstance(ProveedorBean.class));
			
			
		    SqlParameterSource in = new MapSqlParameterSource()
		      .addValue("json", jsonInfo);
		
		    Map<String, Object> out = jdbcCall.execute(in);
		    
		    List<Map<String, Object>> resultSet = (List<Map<String, Object>>) out.get("proveedorNuevo");
		     
		    
		  // System.out.print(result);
		    
		    if (resultSet != null && !resultSet.isEmpty()) {
		       ProveedorBean nuevoProveedor = (ProveedorBean) resultSet.get(0);
		        return nuevoProveedor;
		      }else {
		         throw new RuntimeException("No se inserto el proveedor");
		       }
	}
	
	
	
	@SuppressWarnings("unchecked")
	public String determinar_proveedores_actualizar_precio() {
		    SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
			  .withProcedureName("determinar_proveedores_para_actualizar_precios");
		
		    SqlParameterSource in = new MapSqlParameterSource();
		
		    Map <String, Object> out = jdbcCall.execute(in);
				
	        List<Map<String, Object>> resultSet = (List<Map<String, Object>>) out.get("#result-set-1");
	       
	        
	        if (resultSet != null && !resultSet.isEmpty()) {
	        	 Map<String, Object> row = resultSet.get(0);
	 	        String jsonResult = (String)row.get("proveedores_a_actualizar");
		        return jsonResult;
		      }else {
		         throw new RuntimeException("No se determinaron los proveedores para actualizar precios");
		       }
	}
	
	
	@SuppressWarnings("unchecked")
	public String determinar_proveedores() {
		    SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
			  .withProcedureName("determinar_proveedores");
		
		    SqlParameterSource in = new MapSqlParameterSource();
		
		    Map <String, Object> out = jdbcCall.execute(in);
			
	        List<Map<String, Object>> resultSet = (List<Map<String, Object>>) out.get("#result-set-1");
	      
	        if(resultSet != null && !resultSet.isEmpty()) {
	          Map<String, Object> row = resultSet.get(0);
	  	      String jsonResult = (String)row.get("proveedores");
	  	      return jsonResult;
	        }else {
	          throw new RuntimeException("No se determinaron los proveedores");
	        }
	}
	
	
	public void actualizar_precios(String json) {
		
		    SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
			  .withProcedureName("actualizar_precios");
		
		    SqlParameterSource in = new MapSqlParameterSource()
			  .addValue("json", json);
		
		    jdbcCall.execute(in);
		    
	}
		
	
	@SuppressWarnings("unchecked")
	public String armado_pedido() {
	  
		    SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
				 .withProcedureName("armado_pedido");
					
		    SqlParameterSource in = new MapSqlParameterSource();
					
		    Map <String, Object> out = jdbcCall.execute(in);
		
		    List<Map<String, Object>> resultSet = (List<Map<String, Object>>) out.get("#result-set-1");
		    
		    if(resultSet  != null && !resultSet.isEmpty()) {
		       Map<String, Object> row = resultSet.get(0);    
			   String jsonResult = (String) row.get("pedidos");
			   return jsonResult;
		    }else {
		    	throw new RuntimeException("No se armaron los pedidos ");
		    }
	}
	
	
	public void actualizar_Pedido_id_pedido_proveedor(String json) {
		 
		  //System.out.println("json repositorio:" + json);
		    SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
			   .withProcedureName("updatePedidos_id_pedido_proveedor");
					
		    SqlParameterSource in = new MapSqlParameterSource()
		       .addValue("jsonRequest", json);
		    
		    jdbcCall.execute(in);
	}
	

	
	@SuppressWarnings("unchecked")
	public String obtener_id_pedidos_proveedor(String json) {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
					 .withProcedureName("getId_pedido_proveedor");
						
			SqlParameterSource in = new MapSqlParameterSource()
			       .addValue("jsonRequest", json);
						
			Map <String, Object> out = jdbcCall.execute(in);
			
			List<Map<String, Object>> resultSet = (List<Map<String, Object>>) out.get("#result-set-1");
			
			if(resultSet != null && !resultSet.isEmpty()) {
			    Map<String, Object> row = resultSet.get(0);    
				String jsonResult = (String) row.get("pedidos_proveedor");
				return jsonResult;			
			}else {
				throw new RuntimeException("No se obtuvo el id del pedido del proveedor ");
			}
	}
	
	
    @SuppressWarnings("unchecked")
	public String consultar_y_actualizar_pedidos(String json) {   	
      	   SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
  			 .withProcedureName("consultar_pedidos_proveedor_y_actualizar");
  					
  		   SqlParameterSource in = new MapSqlParameterSource()
  		       .addValue("jsonRequest", json);
  					
  		   Map <String, Object> out = jdbcCall.execute(in);
  		      
  		   List<Map<String, Object>> resultSet = (List<Map<String, Object>>) out.get("#result-set-1");
				
          // System.out.print(resultSet); 
  		   
           if(resultSet != null && !resultSet.isEmpty()) {
        	  Map<String, Object> row = resultSet.get(0);  
        	  String jsonResult = (String) row.get("id_pedido");
       		  return jsonResult;		
           }else {
        	   throw new RuntimeException("No se pudo consultar los pedidos para seguirlos y actualizarlos "); 
           }
	}
    
    
    
    @SuppressWarnings("unchecked")
	public String cancelar_pedido(String json) {
      
  		   SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
  			 .withProcedureName("cancelar_pedido");
  					
  		   SqlParameterSource in = new MapSqlParameterSource()
  		       .addValue("jsonRequest", json);
  					
  		   Map <String, Object> out = jdbcCall.execute(in);
  		
  		   List<Map<String, Object>> resultSet = (List<Map<String, Object>>) out.get("#result-set-1");
  		   
  		   if(resultSet != null && !resultSet.isEmpty()) {
  	  	       Map<String, Object> row = resultSet.get(0);
  	  		   String jsonResult = (String) row.get("pedido_cancelado");
  	  		   return jsonResult;	  
  		   }else {
  			   throw new RuntimeException("No se pudo cancelar el pedido"); 
  		   }
    }
    
    @SuppressWarnings("unchecked")
   	public String actualizar_ponderacion(String json) {
         
     		   SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
     			 .withProcedureName("actualizar_ponderacion");
     					
     		   SqlParameterSource in = new MapSqlParameterSource()
     		       .addValue("jsonRequest", json);
     					
     		   Map <String, Object> out = jdbcCall.execute(in);
     		   
     		   System.out.println(out);
     		
     		   List<Map<String, Object>> resultSet = (List<Map<String, Object>>) out.get("#result-set-1");
     		   
     		  
     		   if(resultSet != null && !resultSet.isEmpty()) {
     	  	       Map<String, Object> row = resultSet.get(0);
     	  		   String jsonResult = (String) row.get("ponderacion_actualizada");
     	  		   return jsonResult;	  
     		   }else {
     			   throw new RuntimeException("No se pudo actualizar la ponderacion"); 
     		   }
    }
    
       
    @SuppressWarnings("unchecked")
    public String calificar_pedido(String json) {
        
		   SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
			 .withProcedureName("calificar_pedido");
					
		   SqlParameterSource in = new MapSqlParameterSource()
		       .addValue("jsonRequest", json);
					
		   Map <String, Object> out = jdbcCall.execute(in);
		   
		   System.out.println(out);
		
		   List<Map<String, Object>> resultSet = (List<Map<String, Object>>) out.get("#result-set-1");
		   
		  
		   if(resultSet != null && !resultSet.isEmpty()) {
	  	       Map<String, Object> row = resultSet.get(0);
	  		   String jsonResult = (String) row.get("calificacion_pedido_proveedor");
	  		   return jsonResult;	  
		   }else {
			   throw new RuntimeException("No se pudo calificar el pedido"); 
		   }
     }
       
    
    
    @SuppressWarnings("unchecked")
	public String getToken(String url) {
    	
    	System.out.println(url);
  
  		   SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
  				 .withProcedureName("getTokenPorUrl");
  					
  		   SqlParameterSource in = new MapSqlParameterSource()
  		       .addValue("url_endpoint", url);
  					
  		   Map <String, Object> out = jdbcCall.execute(in);
  		   
  		   System.out.println(out);
  		   
  		   
  		   List<Map<String, Object>> resultSet = (List<Map<String, Object>>) out.get("#result-set-1");
  		   
  		   if(resultSet != null && !resultSet.isEmpty()) {
  			 Map<String, Object> row = resultSet.get(0);
             String token = (String) row.get("token");
    		 return token;	
  		   }else {
  			 throw new RuntimeException("No se pudo obtener el token");   
  		   }  	
    }
    
    
    
    public void bajaProveedor(int idProveedor) {
    	
    	System.out.println(idProveedor);
    	
      try {
    	SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
  		    .withProcedureName("baja_proveedor");
  					
  		SqlParameterSource in = new MapSqlParameterSource()
  		    .addValue("id_proveedor", idProveedor);
  					
  		jdbcCall.execute(in);
  		
      } catch (RuntimeException ex) {
    	 // System.err.println("Error al dar de baja al proveedor: " + ex.getMessage());
          throw new RuntimeException("No se puede dar de baja el proveedor, sus pedidos no están en el estado Calificado ", ex);
      }
    }
    
    public String estado_post_calificacion(String json) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
		   .withProcedureName("estado_post_calificacion");
				
	    SqlParameterSource in = new MapSqlParameterSource()
	       .addValue("jsonRequest", json);
	    
	    jdbcCall.execute(in);
	    
	    return "Pedido ya calificado exitosamente";
    }
    
    
    @SuppressWarnings("unchecked")
	public List<EfectividadProveedorBean> efectividadProveedor() {
    
    	SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
    		.withProcedureName("generar_efectividad_proveedor")
    	    .returningResultSet("generar_efectividad_proveedor", BeanPropertyRowMapper.newInstance(EfectividadProveedorBean.class));

    						
    	 SqlParameterSource in = new MapSqlParameterSource();
    	
    						
    	 Map <String, Object> out = jdbcCall.execute(in);
    			   
    	 System.out.println(out);
    			
    	 List<EfectividadProveedorBean> result = (List<EfectividadProveedorBean>) out.get("generar_efectividad_proveedor");
    			   
    	
    	 System.out.println(result);
    			  
    	 if(result != null && !result.isEmpty()) {
    	     return result;	  
    	 }else {
    		 return new ArrayList<>();
    	  }
     }

}

