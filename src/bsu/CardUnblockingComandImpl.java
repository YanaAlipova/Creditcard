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

/** CardUnblockingComand */
public class CardUnblockingComandImpl implements ICommand {
	protected UserBanking user;
	private String message = null;

	public CardUnblockingComandImpl() {
	}

	@Override
	public String execute(SessionRequestContent content) {
		String page = ConfigurationManager.getProperty("path.page.login");
		boolean b = false;
		try {
			user = (UserBanking) content.getSessionAttributes().get(PARAM_SESSION_USER);
			int cardNumber = (int) Integer.parseInt((String) content.getRequestAttributes().get(PARAM_CMD));
			// проверка наличия карты у юзера в списке
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
			// System.out.println(user);
			if (user.getRole() != 1 || !b) {
				message  =  MessageManager.getProperty("error.illegal.operation");
				return page; // недостаточно прав
			}
			CardDAO unBlockDao = null;
			PreparedStatement statement = null;
			Connection connect = null;
			b = false;
			try {
				connect = PoolConnection.getInstance().getConnection();
				unBlockDao = new CardDAO(connect);
				statement = unBlockDao.unBlockCardPreparedStatement();
				b = unBlockDao.setCardUnblock(cardNumber, statement);
				if (b) {
					cards.get(cardIndex).setStatus(true); // просто сменить статус, но оставить в списке
					// System.out.println("<br/>***<b>Карта разблокирована</b>" + cardNumber + " " + cards.get(cardIndex));
					// cards.remove(cardIndex); //исключить отображение карты card.unblocking.true
					message = MessageManager.getProperty("card.unblocking.true").concat(": " + cardNumber);
					page = ConfigurationManager.getProperty("path.page.admin");
				}
			} catch (SQLException e) {
				message = MessageManager.getProperty("error.connection");
			} finally {
				unBlockDao.closeStatement(statement);
			}
		} catch (Exception e) {
			message = MessageManager.getProperty("incorrect.data");
			e.printStackTrace();
		} finally {
			content.getSessionAttributes().put(PARAM_MESSAGE, message);
		}
		return page;
	}
}
