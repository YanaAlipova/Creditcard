package bsu;

import dto.UserBanking;
/** К удалению */
public class BankingReceiver {
	protected UserBanking user; // user.getId(), user.getMd5password() должны быть в наличии


	public BankingReceiver(UserBanking user) {
		this.user = user;
	}

	public void action(CommandEnum cmd) {
		switch (cmd) {
		case LOGIN: 
//			this.user = loginCurrentUser();
			System.out.println("<br/>***<b>LOGIN command READY</b>***<br/>");
			// getServletContext().getRequestDispatcher("/loginForm.jsp").include(request, response);
			break;		
		case LOGOUT:
//			this.user = null;
			System.out.println("<br/>***<b>LOGOUT command READY</b>***<br/>");
			break;
		case CARD_BLOCKING:
			System.out.println("<br/>***<b>CARD_BLOCKING command READY</b>***<br/>");
			break;
		case CARD_UNBLOCKING:
			System.out.println("<br/>***<b>CARD_UNBLOCKING command READY</b>***<br/>");
			break;
		case ACCOUNT_RECHARGE:
			System.out.println("<br/>***<b>ACCOUNT_RECHARGE command READY</b>***<br/>");
			break;
		case CREATE_PAYMENT:
			System.out.println("<br/>***<b>CREATE_PAYMENT command READY</b>***<br/>");
			break;
		}
	}
}
