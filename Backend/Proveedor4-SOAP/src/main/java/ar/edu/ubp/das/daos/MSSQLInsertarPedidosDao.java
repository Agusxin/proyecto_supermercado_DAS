package ar.edu.ubp.das.daos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import ar.edu.ubp.das.db.Dao;

public class MSSQLInsertarPedidosDao extends Dao<String, String, String> {

	@Override
	public String delete(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String insert(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String make(ResultSet resultSet) throws SQLException {
		return resultSet.getString(1); 
	}

	@Override
	public List<String> select(String json) throws SQLException {
		 System.out.println(json);
		  this.connect();
		   
		  this.setStatement("{call insertar_pedidos(?)}", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		    
		  this.setParameter(1, json);
		    
		  boolean hasResults = this.getStatement().execute();
		  
		  List<String> lista = new LinkedList<>();
		  
		  while (hasResults || this.getStatement().getUpdateCount() != -1) {
		        if (hasResults) {
		            ResultSet resultSet = this.getStatement().getResultSet();
		            
		            if (resultSet != null) {
		                while (resultSet.next()) {
		                    String idPedidoProveedor = this.make(resultSet);
		                    lista.add(idPedidoProveedor);
		                }
		            }
		        } else {
		           int updateCount = this.getStatement().getUpdateCount();
		            if (updateCount == -1) {
		                break;
		            }
		        }
		        hasResults = this.getStatement().getMoreResults();
		    }
		    
		  
		    this.getStatement().close();
		    
		    System.out.println("lista: " + lista);
		    
		    return lista;
	}

	@Override
	public String update(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean valid(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}
 
}
