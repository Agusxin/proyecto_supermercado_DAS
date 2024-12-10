package ar.edu.ubp.das.daos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import ar.edu.ubp.das.db.Dao;

public class MSSQLCancelarPedidoDao extends Dao<String, String, String> {

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
	public String make(ResultSet arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> select(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String update(String jsonRequest) throws SQLException {
		this.connect();
		System.out.println(jsonRequest);
		this.setProcedure("cancelar_pedido(?)");
		this.setParameter(1, jsonRequest);
		this.executeUpdateQuery();
		return "Pedido Cancelado";
	}

	@Override
	public boolean valid(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

}
