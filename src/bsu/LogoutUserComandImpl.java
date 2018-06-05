package bsu;

import controller.SessionRequestContent;
import resources.ConfigurationManager;

public class LogoutUserComandImpl implements ICommand {
//	private String message = null;
	public LogoutUserComandImpl() {
	}

	@Override
	public String execute(SessionRequestContent content) {
		content.setSessionAttributes(null);
		String page = ConfigurationManager.getProperty("path.page.login");
		/*message = MessageManager.getProperty("message.logout");
		content.getSessionAttributes().put(PARAM_MESSAGE, message);*/
		return page;
	}
}
