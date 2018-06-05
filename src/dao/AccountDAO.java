package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import dto.Account;
import dto.Card;
//import dto.UserBanking;
//import dto.UserBankingAdmin;
//import dto.UserBankingClient;

/**AccountDAO (client account - операции со счетами)*/
public class AccountDAO extends AbstractDAO<Object> {

	private final static String SQL_SELECT_USER_ACCOUNT = "SELECT `account`.`account_number`, `account`.`ballance`, `cards`.`card_number`, `cards`.`status` from account JOIN cards USING (`account_number`) where user_id=? ORDER BY `account`.`account_number`";
	private final static String SQL_RECHARGE_ACCOUNT = "UPDATE `account` SET `account`.`ballance` = `account`.`ballance` + ? WHERE `account`.`account_number` =?";
	private final static String SQL_GET_BALLANCE_ACCOUNT = "SELECT `account`.`ballance` FROM `account` WHERE `account`.`account_number` =?";
	private final static String SQL_WITHDRAW_ACCOUNT = "UPDATE `account` SET `account`.`ballance` = `account`.`ballance` - ? WHERE `account`.`account_number` =?";

	public AccountDAO() {
		super();
	}

	public AccountDAO(Connection connect) {
		super(connect);
	}

	public ArrayList<Account> selectAccountUser(int userId, PreparedStatement ps) {
		ArrayList<Account> accounts = new ArrayList<>();
		Collection<Card> cards = new ArrayList<>();
		// UserBanking user = null;
		// Account account = null;
		Card card = null;
		// cards.add(card);
		int currentAccount = 0;
		try {
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				if (currentAccount != (rs.getInt("account_number"))) {
					cards = new ArrayList<>();
//					System.out.println(rs.getInt("ballance") + " rs.getFloat");
 
					accounts.add(new Account(rs.getInt("account_number"), cards, userId, rs.getInt("ballance")));
					currentAccount = rs.getInt("account_number");
				}
				if (currentAccount == 0) {
					accounts.add(new Account(rs.getInt("account_number"), cards, userId, rs.getInt("ballance")));
					currentAccount = rs.getInt("account_number");
				}
				card = new Card(rs.getInt("card_number"), rs.getInt("account_number"), (boolean) rs.getBoolean("status"), userId);
				cards.add(card);
			}
		} catch (SQLException e) {
//			e.printStackTrace();
		}
		return accounts;
	}

	public boolean addCashAccount(int accountId, int amount, PreparedStatement ps) {
		boolean b = false;
		try {
			ps.setInt(1, amount);
			ps.setInt(2, accountId);
			int rs = ps.executeUpdate();
			b = (rs != 0);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return b;
	}

	/* уменьшение балланса */
	public boolean withdrawCashAccount(int accountId, int amount) {
		boolean b = false;
		if (amount <= 0) {
			return false;
		}
		PreparedStatement psGetBallance = null;
		PreparedStatement psWithdrawCash = null;
		try {
			psGetBallance = getBallanceAccountPreparedStatement();
			psWithdrawCash = withdrawCashAccountPreparedStatement();
			psGetBallance.setInt(1, accountId);
			psWithdrawCash.setInt(1, amount);
			psWithdrawCash.setInt(2, accountId);
			connect.setAutoCommit(false);
			ResultSet rs = psGetBallance.executeQuery();
			int ballance = 0;
			while (rs.next()) {
				ballance = rs.getInt("ballance");
			}
			int withdrawCash = 0;
			if (ballance < amount) {
				withdrawCash = psWithdrawCash.executeUpdate();
			}
			b = (withdrawCash != 0);
			// int rs = ps.executeUpdate();
			connect.commit();
		} catch (SQLException e) {
			//балланс меньше суммы оплаты
//			e.printStackTrace();
			return false;
		} finally {
			try {
				connect.setAutoCommit(true);
			} catch (SQLException e) {
//				e.printStackTrace();
			}
			closeStatement (psGetBallance);
			closeStatement (psWithdrawCash);
		}
		return b;
	}

	/* уменьшение балланса */
	public boolean withdrawCashAccount(int accountId, int amount, PreparedStatement ps) {
		boolean b = false;
		try {
			ps.setInt(1, amount);
			ps.setInt(2, accountId);

			connect.setAutoCommit(false);
			int rs = ps.executeUpdate();
			b = (rs != 0);

			connect.commit();
		} catch (SQLException e) {
//			e.printStackTrace();
			return false;
		}
		return b;
	}

	public PreparedStatement selectUserAccountPreparedStatement() {
		PreparedStatement st = getPreparedStatement(SQL_SELECT_USER_ACCOUNT);
		return st;
	}

	public PreparedStatement addCashAccountPreparedStatement() {
		PreparedStatement st = getPreparedStatement(SQL_RECHARGE_ACCOUNT);
		return st;
	}

	public PreparedStatement withdrawCashAccountPreparedStatement() {
		PreparedStatement st = getPreparedStatement(SQL_WITHDRAW_ACCOUNT);
		return st;
	}

	public PreparedStatement getBallanceAccountPreparedStatement() {
		PreparedStatement st = getPreparedStatement(SQL_GET_BALLANCE_ACCOUNT);
		return st;
	}
}
