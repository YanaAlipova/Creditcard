package bsu;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import controller.SessionRequestContent;
import dao.PaymentDAO;
import dto.Account;
import dto.Card;
import dto.UserBanking;
import resources.ConfigurationManager;
import resources.MessageManager;
import resources.PoolConnection;

/** Create Payment (Создание платежа) */
public class CreatePaymentComandImpl implements ICommand {
	private static final String PAYMENT_PREFIX = "payment_";
	protected UserBanking user;
	private String message = null;

	CreatePaymentComandImpl() {
	}

	@Override
	public String execute(SessionRequestContent content) {
		int cardNumber;
		int amount;
		String page = ConfigurationManager.getProperty("path.page.login");
		boolean b = false;
		try {
			user = (UserBanking) content.getSessionAttributes().get(PARAM_SESSION_USER);
			String fildData = (String) content.getRequestAttributes().get(PARAM_CMD);
			// accountPost = accountPost.replaceAll(ACCOUNT_PREFIX, "").trim();
			String amountFildData = (String) content.getRequestAttributes().get(PAYMENT_PREFIX.concat(fildData));
			amountFildData = amountFildData.replaceAll(",", ".").trim();
			cardNumber = (int) Integer.parseInt(fildData);
			amount = (int) (Float.parseFloat(amountFildData) * 100);
//			System.out.println("CR amount " + amount);
			// проверка владельца карты у юзера
			List<Card> cards = user.getCards();
			int i = 0;
			int cardIndex = 0;
			for (Card card : cards) {
				if (card.getId() == cardNumber) {
					b = true;
					cardIndex = i; // номер нужной карты
					break;
				}
				i++;
			}
//			System.out.println(user);
			if (user.getRole() != 0 || !b) {
				message  =  MessageManager.getProperty("error.illegal.operation");
				return page;
			}
			PaymentDAO newPaymantDao = null;
			Connection connect = null;
			b = false;
			try {
				connect = PoolConnection.getInstance().getConnection();
				newPaymantDao = new PaymentDAO(connect);
				b = newPaymantDao.createPaymentDAO(cardNumber, amount);
//				System.out.println("removeCashAccountDao " + b);
				// платежи проходят, возврат данных в юзера
				if (b) {
					int accountId = cards.get(cardIndex).getAccountNumber();
					ArrayList<Account> accounts = user.getAccounts();
					for (Account account : accounts) {
						if (account.getId() == accountId) {
							int ballance = account.getBallance();
							account.setBallance(ballance - amount);// изменяем балланс для отображения
							message  =  MessageManager.getProperty("payment.true");
							message = message.concat(": "+ (float)ballance/100 + " amount:" + (float)amount/100 + " new ballance:" + (float)(ballance-amount)/100);
//							System.out.println("ballance " + ballance + "-" + amount);
							break;
						}
					}
//					System.out.println("removeCashAccount true: " + b);
				} else {
					message  =  MessageManager.getProperty("error.ballance");
				}
				page = ConfigurationManager.getProperty("path.page.user");
			} catch (SQLException e) {
				message  =  MessageManager.getProperty("error.connection");
			}
		} catch (Exception e) {
			message  =  MessageManager.getProperty("incorrect.data");
			
		} finally {
			content.getSessionAttributes().put(PARAM_MESSAGE, message);
		}
		return page;
	}
}
