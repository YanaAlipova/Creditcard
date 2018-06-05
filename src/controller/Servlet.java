package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bsu.CommandEnum;
import bsu.ICommand;
import dto.UserBanking;
import resources.ConfigurationManager;
import resources.MessageManager;

/**
 * Servlet implementation class Servlet
 */
@WebServlet({ "/Servlet", "/go", "/user*", "/admin*","/index.jsp" })
public class Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Servlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String page = ConfigurationManager.getProperty("path.page.login");
		HttpSession session = request.getSession(true);
		String messageFildName = ICommand.PARAM_MESSAGE;
		String userFildName = ICommand.PARAM_SESSION_USER;
		try {
			UserBanking user = (UserBanking) session.getAttribute(userFildName);
			if (user.getRole() == 1) {
				page = ConfigurationManager.getProperty("path.page.admin");
				session.setAttribute(messageFildName, MessageManager.getProperty("message.hello.admin"));
			} else if (user.getRole() == 0) {
				page = ConfigurationManager.getProperty("path.page.user");
				session.setAttribute(messageFildName, MessageManager.getProperty("message.hello.user"));
			}
		} catch (Exception e) {
			session.setAttribute(messageFildName, MessageManager.getProperty("message.need.login"));
		}
//		 getServletContext().getRequestDispatcher(page).forward(request, response);
		getServletContext().getRequestDispatcher(page).include(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// ошибку авторизации добавить

//		PrintWriter out = response.getWriter();
		// HttpSession session = request.getSession(true);
		// doGet(request, response);
		String page;
		SessionRequestContent content = new SessionRequestContent();
		CommandEnum currentCommand = content.extractValues(request);
		page = currentCommand.getCurrentCommand().execute(content); // страница
		// page="/jsp/login.jsp"; page="/main.jsp";
		// теперь мы должны вернуть ответ пользователю составить ответ
		// обработать пустую команду
		request = content.insertAttributes(request);
		if (page == null) {
			// out.println("<br/>***<b>page = null!! " + " </b>***<br/>");//TODO
			page = ConfigurationManager.getProperty("path.page.user");

		}
		// getServletContext().getRequestDispatcher(page).forward(request, response);
		getServletContext().getRequestDispatcher(page).include(request, response);
	}

}
/*
 * // определение команды, пришедшей из JSP ActionFactory client = new ActionFactory(); ActionCommand command = client.defineCommand(request);
 * 
 * //вызов реализованного метода execute() и передача параметров //классу-обработчику конкретной команды
 * 
 * page = command.execute(request); // метод возвращает страницу ответа // page = null; // поэксперементировать! if (page != null) { RequestDispatcher
 * dispatcher = getServletContext().getRequestDispatcher(page); // вызов страницы ответа на запрос dispatcher.forward(request, response); } else { // установка
 * страницы c cообщением об ошибке page = ConfigurationManager.getProperty("path.page.index"); request.getSession().setAttribute("nullPage",
 * MessageManager.getProperty("message.nullpage")); response.sendRedirect(request.getContextPath() + page); }
 */
