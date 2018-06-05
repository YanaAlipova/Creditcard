package dao;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import resources.PoolConnection;

/**Операции с сущностями бд (MySQL) */
public abstract class AbstractDAO<T> {
	protected Connection connect;
	protected T currentDAO;

	AbstractDAO() {

	}

	AbstractDAO(Connection connect) {
		this.connect = connect;
	}

	AbstractDAO(boolean getConnect) {
		if (getConnect) {
			try {
				connect = PoolConnection.getInstance().getConnection();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (PropertyVetoException e) {
				e.printStackTrace();
			}
		}
	}

	protected PreparedStatement getPreparedStatement(String sqlRequest) {
		PreparedStatement ps = null;
		try {
			ps = connect.prepareStatement(sqlRequest);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ps;
	}

	public void closeStatement(PreparedStatement ps) {
		if (ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public T getCurrentDAO() {
		return currentDAO;
	}

	public T getCurrentDAO(int id) {
		return currentDAO;
	}

	// TODO Допилить DAO
	/*
	 * вынести запросы в отдельные файлы .properties допилить типитизацию, реализовать стандартные методы добавления, поиска, замены, удаления
	 */
}
