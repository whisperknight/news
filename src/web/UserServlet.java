package web;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDao;
import model.User;
import util.DatabaseUtil;

@WebServlet("/user")
public class UserServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private DatabaseUtil dbUtil = new DatabaseUtil();
	private UserDao userDao = new UserDao();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		if(action.equals("login"))
			login(request, response);
		else if (action.equals("logout")) {
			logout(request, response);
		}
		
	}

	/**
	 * 登出
	 * @throws IOException
	 */
	private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.getSession().invalidate();
		response.sendRedirect(request.getContextPath()+"/background/login.jsp");
	}

	/**
	 * 登录
	 * @param request
	 * @param response
	 */
	private void login(HttpServletRequest request, HttpServletResponse response) {
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String remember = request.getParameter("remember");
		HttpSession session = request.getSession();
		
		Connection con = null;
		try {
			con = dbUtil.getConnection();
			User user = new User(userName, password);
			User resultUser = userDao.login(con, user);
			if (resultUser != null) {
				session.setAttribute("currentUser", resultUser);
				if (remember != null && remember.equals("on")) {
					// 设置客户端cookie
					setCookie(response, "user", userName + "-" + password);
					setCookie(response, "remember", remember);
				}else {
					setCookie(response, "remember", "no");
				}
				response.sendRedirect(request.getContextPath() + "/background/mainTemplate.jsp");
			} else {
				if (remember != null && remember.equals("on"))
					setCookie(response, "remember", remember);
				else
					setCookie(response, "remember", "no");
				
				request.setAttribute("user", user);
				request.setAttribute("error", "*用户名或密码错误！");
				
				request.getRequestDispatcher("background/login.jsp").forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtil.closeConnection(con);
		}
	}

	/**
	 * 设置一个cookie表单键值对
	 * @param response
	 * @param key
	 * @param value
	 */
	private void setCookie(HttpServletResponse response, String key, String value) {
		Cookie c = new Cookie(key, value);
		c.setMaxAge(1 * 60 * 60 * 24 * 7);
		response.addCookie(c);
	}
}
