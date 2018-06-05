package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dto.UserBanking;
import dto.UserBankingAdmin;
import dto.UserBankingClient;

/** User DAO*/
public class UserDAO extends AbstractDAO<UserBanking> {
	private final static String SQL_INSERT = "INSERT INTO users (user_id, password, role) VALUES (?,?,?)";
	// private final static String SQL_SELECT = "SELECT user_id, password, role from users where user_id=?"; 
	// SELECT user_id, password, role from users where user_id=? AND password=? ;
	private final static String SQL_SELECT = "SELECT user_id, password, role from users where user_id=? AND password=?";

	public UserDAO() {
		super();
	}

	public UserDAO(boolean getConnect) {
		super(getConnect);
	}

	public UserDAO(Connection connect) {
		super (connect);
//		this.connect = connect;
	}

	public UserBanking selectUser(int userId, String passMd, PreparedStatement ps) {
		// int role = user.getRole(); // 1 админ 0 юзер
		UserBanking user = null;
		try {
			ps.setInt(1, userId);
			ps.setString(2, passMd);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				if (rs.getInt(3) != 0) {
					user = new UserBankingAdmin(rs.getInt(1), rs.getInt(3));
					
				} else {
					user = new UserBankingClient(rs.getInt(1), rs.getInt(3));
				}user.setMd5password(passMd);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	public boolean insertUser(UserBanking user, PreparedStatement ps) {
		int role = user.getRole(); // 1 админ 0 юзер
		String pass = UserBanking.md5Custom(user.getPassword());
		boolean flag = false;
		try {
			ps.setInt(1, 0);
			ps.setString(2, pass);
			ps.setInt(3, role);
			ps.executeUpdate();
			flag = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}

	public PreparedStatement insertPreparedStatement() {
		PreparedStatement st = getPreparedStatement(SQL_INSERT);
		return st;
	}

	public PreparedStatement selectPreparedStatement() {
		PreparedStatement st = getPreparedStatement(SQL_SELECT);
		return st;
	}
}
