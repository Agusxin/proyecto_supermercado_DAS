package ar.edu.ubp.das.repository;





import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;

@Repository
public class ProveedorRestRepository {
	
	@Autowired
    private JdbcTemplate jdbcTpl;
	
	private Gson gson = new Gson();

	
	
	public String obtenerDatosProveedor() {
		
	    SqlParameterSource in = new MapSqlParameterSource();
			   	
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
			.withProcedureName("enviar_informacion_del_proveedor");
		
	    Map<String, Object> out = jdbcCall.execute(in);
	    
	    
	    List<Map<String, Object>> resultSet = (List<Map<String, Object>>) out.get("#result-set-1");
	    Map<String, Object> row = resultSet.get(0);
	    
	    
	    String jsonResult = (String)row.get("informacion_proveedor");
	 	
		return jsonResult;
	}
	
	
	public String obtenerProductosProveedor() {
		SqlParameterSource in = new MapSqlParameterSource();
	   	
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
			.withProcedureName("enviar_info_productos");
		
	    Map<String, Object> out = jdbcCall.execute(in);
	    
	    
	    List<Map<String, Object>> resultSet = (List<Map<String, Object>>) out.get("#result-set-1");
	    Map<String, Object> row = resultSet.get(0);
	    
	    
	    String jsonResult = (String)row.get("info_productos_proveedor");
	 	
	    
	    
		return jsonResult;
	}
	
	public String insertar_pedidos(String jsonPedidos) {
		
		SqlParameterSource in = new MapSqlParameterSource()
	       .addValue("json", jsonPedidos);
			
	   	
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
			.withProcedureName("insertar_pedidos");
		
	    Map<String, Object> out = jdbcCall.execute(in);
	    
	   
	    List<Map<String, Object>> resultSet = (List<Map<String, Object>>) out.get("#result-set-1");
	    Map<String, Object> row = resultSet.get(0);
	    
	    
	    String jsonResult = (String)row.get("id_pedido_proveedor");
	 	
	    
		return jsonResult;
	}
	
	
	public String get_pedidos(String jsonPedidos) {

		SqlParameterSource in = new MapSqlParameterSource()
	       .addValue("jsonRequest", jsonPedidos);
			
	   	
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
			.withProcedureName("get_pedidos");
		
	    Map<String, Object> out = jdbcCall.execute(in);
	    
	    
	    List<Map<String, Object>> resultSet = (List<Map<String, Object>>) out.get("#result-set-1");
	    Map<String, Object> row = resultSet.get(0);
	    
	    
	    String jsonResult = (String)row.get("pedidos");
	    
		return jsonResult;
	}
	
	public String cancelar_pedido(String jsonPedido) {

		
    	SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
				 .withProcedureName("cancelar_pedido");
					
		SqlParameterSource in = new MapSqlParameterSource()
		       .addValue("jsonRequest", jsonPedido);
					
		Map <String, Object> out = jdbcCall.execute(in);
		
        String result = (String) out.get("#result-set-1");
		
		return result;		
	}
	
    public String calificar_pedido(String jsonPedido) {

		
    	SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
				 .withProcedureName("calificar_pedido");
					
		SqlParameterSource in = new MapSqlParameterSource()
		       .addValue("jsonRequest", jsonPedido);
					
		Map <String, Object> out = jdbcCall.execute(in);
		
        String result = (String) out.get("#result-set-1");
		
		return result;		
	}
	
    public String getToken() {

        SqlParameterSource in = new MapSqlParameterSource();
	   	
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
			.withProcedureName("getToken");
		
	    Map<String, Object> out = jdbcCall.execute(in);
	    
		List<Map<String, Object>> resultSet = (List<Map<String, Object>>) out.get("#result-set-1");
	    Map<String, Object> row = resultSet.get(0);
     	
        String token = (String) row.get("token");
	
		
		return token;	
	}
	
	

}

