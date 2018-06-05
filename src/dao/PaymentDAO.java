package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import dto.Payment;

/** Payment DAO (операции с платежами) */
public class PaymentDAO extends AbstractDAO<Payment> {
	// private StringBuffer message = new StringBuffer();
	private final static String SQL_INSERT_PAYMENT = "INSERT INTO payments (card_number, amount) VALUES (?,?)";
	// балланс и статус
	private final static String SQL_GET_BALLANCE_CARD = "SELECT `account`.`ballance`,`cards`.`status` FROM `account` JOIN `cards` USING (`account_number`) WHERE `cards`.`card_number` =?";
	private final static String SQL_WITHDRAW_ACCOUNT_TO_CARD = "UPDATE `account` JOIN  `cards` USING (`account_number`) SET `account`.`ballance` = `account`.`ballance` - ?  WHERE  `cards`.`card_number` = ? AND `cards`.`status` = 1";

	public PaymentDAO() {
	}

	public PaymentDAO(Connection connection) {
		super(connection);
	}

	public PaymentDAO(boolean getConnect) {
		super(getConnect);
	}

	// платеж - списание средств со счета по номеру НЕЗАБЛОКИРОВАННОЙ карты
	// внесение в таблицу данных о платеже

	public boolean createPaymentDAO(int cardId, int amount) {
		boolean b = false;
		if (amount <= 0) {
			return false;
		}
		PreparedStatement psGetBallance = null;
		PreparedStatement psWithdrawCash = null;
		PreparedStatement addPayment = null;

		try {
			psGetBallance = getBallanceCardPreparedStatement();
			psWithdrawCash = withdrawCashCardPreparedStatement();
			addPayment = addPaymentPreparedStatement();
			psGetBallance.setInt(1, cardId);
			psWithdrawCash.setInt(1, amount);
			psWithdrawCash.setInt(2, cardId);
			addPayment.setInt(1, cardId);
			addPayment.setInt(2, amount);
			connect.setAutoCommit(false);
			ResultSet rs = psGetBallance.executeQuery();
			if (rs == null) {
				// message = message.append("Карточка заблокирована");
			} else {
				int ballance = 0;
				while (rs.next()) {
					ballance = rs.getInt("ballance");
					System.out.println("ballance " + ballance);
				}
				// int withdrawCash = 0; withdrawCash =
				int resultPayment = 0;
				System.out.println("amount " + amount);
				if (ballance > amount) {
					psWithdrawCash.executeUpdate();
					resultPayment = addPayment.executeUpdate();
				} else {
					// message = message.append("Для платежа недостаточно денег на счете");
					// System.out.println(message);
				}
				b = (resultPayment != 0);
				// System.out.println(b);
			}
			connect.commit();
		} catch (SQLException e) {
			// балланс меньше суммы оплаты
			// System.out.println(b);
			// e.printStackTrace();
			return false;
		} finally {
			try {
				connect.setAutoCommit(true);
			} catch (SQLException e) {
				// e.printStackTrace();
			}
			closeStatement(psGetBallance);
			closeStatement(psWithdrawCash);
			closeStatement(addPayment);
		}
		return b;
	}

	public PreparedStatement getBallanceCardPreparedStatement() {
		PreparedStatement st = getPreparedStatement(SQL_GET_BALLANCE_CARD);
		return st;
	}

	public PreparedStatement withdrawCashCardPreparedStatement() {
		PreparedStatement st = getPreparedStatement(SQL_WITHDRAW_ACCOUNT_TO_CARD);
		return st;
	}

	public PreparedStatement addPaymentPreparedStatement() {
		PreparedStatement st = getPreparedStatement(SQL_INSERT_PAYMENT);
		return st;
	}
}