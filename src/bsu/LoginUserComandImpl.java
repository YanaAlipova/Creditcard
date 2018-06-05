package bsu;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import controller.SessionRequestContent;
import dao.AccountDAO;
import dao.CardDAO;
import dao.UserDAO;
import dto.Account;
import dto.Card;
import dto.UserBanking;
import dto.UserBankingAdmin;
import dto.UserBankingClient;
import resources.ConfigurationManager;
import resources.MessageManager;
import resources.PoolConnection;

/** Login */
public class LoginUserComandImpl implements ICommand {
	private static final String PARAM_NAME_LOGIN = "username";
	private static final String PARAM_NAME_PASSWORD = "password";
	private UserBanking user;
	private String page;
	private String message = null;

	LoginUserComandImpl() {
	}

	@Override
	public String execute(SessionRequestContent content) {
		page = ConfigurationManager.getProperty("path.page.login");
		// user =new UserBankingClient();
		int userName = 0;
		String userMdPassword = null;
		try {
			String userPassword = (String) content.getRequestAttributes().get(PARAM_NAME_PASSWORD);
			userMdPassword = UserBankingClient.md5Custom(userPassword);
			userName = (int) Integer.parseInt((String) content.getRequestAttributes().get(PARAM_NAME_LOGIN));
			UserDAO getUserDao = null;
			PreparedStatement statementLogin = null;
			Connection connect = null;
//			System.out.println("<br/>***<b>Аунтификация - начало userName: " + userName + " userMdPassword: " + userMdPassword + "</b>***<br/>");
			try {
				connect = PoolConnection.getInstance().getConnection();
				getUserDao = new UserDAO(connect);
				statementLogin = getUserDao.selectPreparedStatement();
				user = getUserDao.selectUser(userName, userMdPassword, statementLogin);
				// System.out.println("<br/>*++<b>коннект true. получаем все данные по юзеру</b>***<br/>");
				if (user != null) {
					// System.out.println("<br/>***<b>Аунтификация - успешно</b>***<br/>");
					PreparedStatement statementAccount = null;
					//TODO user type correct update
					//здесь лучше вынести в отдельный метод и сделать через overload
					if (user instanceof UserBankingClient) {
						AccountDAO accountDao = null;
						try {
							accountDao = new AccountDAO(connect);
							UserBankingClient currentUser = (UserBankingClient) user;
							statementAccount = accountDao.selectUserAccountPreparedStatement();
							ArrayList<Account> accounts = new ArrayList<>();
							accounts = accountDao.selectAccountUser(user.getId(), statementAccount);
							currentUser.setAccounts(accounts);
							List<Card> cards = new ArrayList<>();
							Iterator<Account> it = accounts.iterator();
							// добавляем карты отдельно
							while (it.hasNext()) {
								Account ac = it.next();
								// System.out.println("account " + ac.getId());
								Iterator<Card> iter = ac.getCards().iterator();
								while (iter.hasNext()) {
									cards.add(iter.next());
								}
							}
							currentUser.setCards(cards);
							// this.user = currentUser;
							content.getSessionAttributes().put("currentUser", currentUser);
							page = ConfigurationManager.getProperty("path.page.user");
							message = MessageManager.getProperty("message.true.user"); 
							// System.out.println("<br/>***userType <b>UserBankingClient</b>**pageType <b>" + page + "</b>***<br/>");
							return page;
						} catch (Exception e) {
							message = MessageManager.getProperty("incorrect.data");
//							e.printStackTrace();
						} finally {
							accountDao.closeStatement(statementAccount);
						}
					} else if (user instanceof UserBankingAdmin) {
						CardDAO cardDao = null;
						try {
							cardDao = new CardDAO(connect);
							UserBankingAdmin currentUser = (UserBankingAdmin) user;
							statementAccount = cardDao.selectBlockPreparedStatement();
							List<Card> cards = new ArrayList<>();
							cards = cardDao.selectBlockCards(statementAccount);
							currentUser.setCards(cards);
							content.getSessionAttributes().put("currentUser", currentUser);
							page = ConfigurationManager.getProperty("path.page.admin");
							message = MessageManager.getProperty("message.true.admin"); 
//							System.out.println("<br/>***userType <UserBankingAdmin</b>**pageType <b>" + page + "</b>***<br/>");
							return page;
						} catch (Exception e) {
							message = MessageManager.getProperty("incorrect.data");
//							e.printStackTrace();
						} finally {
							cardDao.closeStatement(statementAccount);
						}
					}
				}
			} catch (IOException e) {
				message = MessageManager.getProperty("incorrect.data");
//				e.printStackTrace();
			} catch (SQLException e) {
				message  =  MessageManager.getProperty("error.connection");
//				e.printStackTrace();
			} catch (PropertyVetoException e) {
				message = MessageManager.getProperty("incorrect.data");
//				e.printStackTrace();
			} finally {
				getUserDao.closeStatement(statementLogin);
			}
		} catch (Exception e) {
//			System.out.println("<br/>***<b>Отсуствуют или не корректные входные данные пользователя</b>***<br/>");
			message = MessageManager.getProperty("message.loginerror");
//			e.printStackTrace();
			return page; // page = ConfigurationManager.getProperty("path.page.login");
		} finally {
			content.getSessionAttributes().put(PARAM_MESSAGE, message);
		}
		// System.out.println("<br/>***<b>Аунтификация - false</b>***<br/>");
		// message = MessageManager.getProperty("message.loginerror"); 
		return page; // page = ConfigurationManager.getProperty("path.page.login");
	}
}
