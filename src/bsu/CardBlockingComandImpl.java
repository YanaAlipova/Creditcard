package bsu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import controller.SessionRequestContent;
import dao.CardDAO;
import dto.Card;
import dto.UserBanking;
import resources.ConfigurationManager;
import resources.MessageManager;
import resources.PoolConnection;

/** Blocking Card */
public class CardBlockingComandImpl implements ICommand {
	protected UserBanking user;
	private String message = null;

	CardBlockingComandImpl() {
	}

	@Override
	public String execute(SessionRequestContent content) {
		// message = MessageManager.getProperty("message.addCashAccount.false"); content.getSessionAttributes().put(messageFildName, message);
		String page = ConfigurationManager.getProperty("path.page.login");
		boolean b = false;
		try {
			user = (UserBanking) content.getSessionAttributes().get(PARAM_SESSION_USER);
			int cardNumber = (int) Integer.parseInt((String) content.getRequestAttributes().get(PARAM_CMD));
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
			if (user.getRole() != 0 || !b) {
				message = MessageManager.getProperty("error.illegal.operation");
				return page;
			}
			CardDAO blockDao = null;
			PreparedStatement statement = null;
			Connection connect = null;
			try {
				connect = PoolConnection.getInstance().getConnection();
				blockDao = new CardDAO(connect);
				statement = blockDao.blockCardPreparedStatement();
				b = blockDao.setCardBlock(cardNumber, statement);
				if (b) {
					cards.get(cardIndex).setStatus(false);// меняем статус на заблокировано
					message = MessageManager.getProperty("card.blocking.true").concat(" :" + cardNumber);
					// System.out.println("<br/>***<b>Карта заблокирована</b>" + cardNumber);
					page = ConfigurationManager.getProperty("path.page.user");
				}
			} catch (SQLException e) {
				message = MessageManager.getProperty("error.connection");
			} finally {
				blockDao.closeStatement(statement);
			}
		} catch (Exception e) {
			message = MessageManager.getProperty("incorrect.data");
//			e.printStackTrace();
		} finally {
			content.getSessionAttributes().put(PARAM_MESSAGE, message);
		}
		return page;
	}

}
