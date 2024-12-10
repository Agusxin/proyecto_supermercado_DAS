package ar.edu.ubp.das.daos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import ar.edu.ubp.das.db.Dao;

public class MSSQLPedidosDao extends Dao<String, String, String> {

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
	public String make(ResultSet result) throws SQLException {
		return result.getString("pedidos");
	}

	@Override
	public List<String> select(String json) throws SQLException {
		this.connect();    
		this.setProcedure("get_pedidos(?)");
		this.setParameter(1, json);
		return this.executeQuery();
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
