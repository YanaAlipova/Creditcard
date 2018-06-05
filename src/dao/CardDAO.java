package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.Card;
/**CardDAO (операции с картами) */
public class CardDAO extends AbstractDAO<Card> {//card_number account_number status user_id
	private final static String SQL_SELECT_CARD_BLOCK = "SELECT `cards`.`card_number`, `account_number`, `status`, `account`.`user_id` from cards JOIN account USING (`account_number`) where `cards`.`status`=0";
	private final static String SQL_SET_CARD_UNBLOCK = "UPDATE `payment_card`.`cards` SET `status` = 1 WHERE `cards`.`card_number` =?";
	private final static String SQL_SET_CARD_BLOCK = "UPDATE `payment_card`.`cards` SET `status` = 0 WHERE `cards`.`card_number` =?";

	public CardDAO() {
	}

	public CardDAO(boolean getConnect) {
		super(getConnect);
	}

	public CardDAO(Connection connect) {
		super(connect);
	}

	public List<Card> selectBlockCards(PreparedStatement ps) {
		List<Card> cards = new ArrayList<>();
		try {
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				cards.add(new Card(rs.getInt("card_number"), rs.getInt("account_number"), (boolean) rs.getBoolean("status"), rs.getInt("user_id")));
//				cards.add(new Card(rs.getInt(1), rs.getInt(2), (boolean) rs.getBoolean(3), rs.getInt(4)));
//				System.out.println("cardAdminAdd " + rs.getInt("card_number"));
			}
		} catch (SQLException e) {
//			e.printStackTrace();
		}
		return cards;
	}
	public boolean setCardUnblock(int cardNumber, PreparedStatement ps) {
		boolean b = false;
		try {
			ps.setInt(1, cardNumber);
			int rs = ps.executeUpdate();
			b = (rs != 0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return b;
	}

	public boolean setCardBlock(int cardNumber, PreparedStatement ps) {
		boolean b = false;
		try {
			ps.setInt(1, cardNumber);
			int rs = ps.executeUpdate();
			b = (rs != 0);
		} catch (SQLException e) {
//			e.printStackTrace();
		}
		return b;
	}

	public PreparedStatement selectBlockPreparedStatement() {
		PreparedStatement st = getPreparedStatement(SQL_SELECT_CARD_BLOCK);
		return st;
	}

	public PreparedStatement unBlockCardPreparedStatement() {
		PreparedStatement st = getPreparedStatement(SQL_SET_CARD_UNBLOCK);
		return st;
	}

	public PreparedStatement blockCardPreparedStatement() {
		PreparedStatement st = getPreparedStatement(SQL_SET_CARD_BLOCK);
		return st;
	}

}