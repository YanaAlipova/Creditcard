package bsu;

import controller.SessionRequestContent;

/**Комманды */
public interface ICommand {
	public static final String PARAM_CMD= "cmdValue";
	public static final String PARAM_SESSION_USER = "currentUser";
	public static final String PARAM_MESSAGE = "message";
	public String execute(SessionRequestContent content);
}