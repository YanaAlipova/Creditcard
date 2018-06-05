package bsu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import controller.SessionRequestContent;
import dao.AccountDAO;
import dto.Account;
import dto.UserBanking;
import resources.ConfigurationManager;
import resources.MessageManager;
import resources.PoolConnection;

/** Account Recharge (Пополнение счета) */
public class AccountRechargeComandImpl implements ICommand {
	private static final String ACCOUNT_PREFIX = "account_";
	private int amount;
	private String page;
	private String message = null;
	private UserBanking user;
	
	AccountRechargeComandImpl() {
	}

	@Override
	public String execute(SessionRequestContent content) {
		page = ConfigurationManager.getProperty("path.page.login");
		boolean b = false;
		try {
			user = (UserBanking) content.getSessionAttributes().get(PARAM_SESSION_USER);
			String accountPostData = (String) content.getRequestAttributes().get(PARAM_CMD);
			// accountPost = accountPost.replaceAll(ACCOUNT_PREFIX, "").trim();
			String amountPostData = (String) content.getRequestAttributes().get(ACCOUNT_PREFIX.concat(accountPostData));
			amountPostData = amountPostData.replaceAll(",", ".").trim();
			int accountNumber = (int) Integer.parseInt(accountPostData);
			amount = (int) (Float.parseFloat(amountPostData) * 100);
			// проверка владельца карты у юзера
			ArrayList<Account> accounts = user.getAccounts();
			int i = 0;
			int accountIndex = 0;
			for (Account account : accounts) {
				if (account.getId() == accountNumber) {
					b = true;
					accountIndex = i; // номер нужной карты
					break;
				}
				i++;
			}
			if (user.getRole() != 0 || !b) {
				message  =  MessageManager.getProperty("error.illegal.operation");
				return page;
			}

			AccountDAO addCashDao = null;
			PreparedStatement statement = null;
			Connection connect = null;
			b = false;
			try {
				connect = PoolConnection.getInstance().getConnection();
				addCashDao = new AccountDAO(connect);
				statement = addCashDao.addCashAccountPreparedStatement();
				b = addCashDao.addCashAccount(accountNumber, amount, statement);
				int ballance = accounts.get(accountIndex).getBallance();
				accounts.get(accountIndex).setBallance(ballance + amount);// увеличиваем балланс для отображения
				System.out.println("addCashAccount " + b);
				page = ConfigurationManager.getProperty("path.page.user");
				message =  MessageManager.getProperty("message.addCashAccount.true"); 
			} catch (Exception e) {
				message  =  MessageManager.getProperty("error.connection");
				page = ConfigurationManager.getProperty("path.page.user");
				
			} finally {
				addCashDao.closeStatement(statement);
			}
		} catch (Exception e) {
			message =  MessageManager.getProperty("message.addCashAccount.false");  
		}finally {
			content.getSessionAttributes().put(PARAM_MESSAGE, message);
		}
		return page;
	}
}