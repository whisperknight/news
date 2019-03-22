package web;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.LinkDao;
import model.Link;
import net.sf.json.JSONObject;
import util.DatabaseUtil;
import util.JsonUtil;
import util.StringUtil;

@WebServlet("/link")
public class LinkServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private DatabaseUtil dbUtil = new DatabaseUtil();
	private LinkDao linkDao = new LinkDao();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		if (action.equals("preEdit"))
			preEdit(request, response);
		else if (action.equals("editFinished")) {
			editFinished(request, response);
		} else if (action.equals("linkList")) {
			getLinkList(request, response);
		} else if (action.equals("deletelink")) {
			delete(request, response);
		}
	}

	/**
	 * 异步，删除链接
	 * 
	 * @param request
	 * @param response
	 */
	private void delete(HttpServletRequest request, HttpServletResponse response) {
		String linkId = request.getParameter("linkId");
		Connection con = null;
		try {
			con = dbUtil.getConnection();
			JSONObject jo = new JSONObject();
			int result = linkDao.deleteLink(con, Integer.parseInt(linkId));
			if(result == 1)
				jo.put("info", "success");
			else
				jo.put("info", "error");
			JsonUtil.write(jo, response);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtil.closeConnection(con);
		}
	}

	/**
	 * 获取所有链接列表
	 * 
	 * @param request
	 * @param response
	 */
	private void getLinkList(HttpServletRequest request, HttpServletResponse response) {
		Connection con = null;
		try {
			con = dbUtil.getConnection();

			String sql = "select * from t_link order by orderNum";
			List<Link> linkList = linkDao.getLinkList(con, sql);
			request.setAttribute("linkList", linkList);

			request.setAttribute("mainPage", "/background/link/linkList.jsp");
			request.getRequestDispatcher("/background/mainTemplate.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtil.closeConnection(con);
		}
	}

	/**
	 * 预编辑
	 * 
	 * @param request
	 * @param resp
	 * @throws IOException
	 */
	private void preEdit(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String linkId = request.getParameter("linkId");
		Connection con = null;
		try {
			con = dbUtil.getConnection();

			if (StringUtil.isNotEmpty(linkId)) {
				Link link = linkDao.getLinkByLinkId(con, Integer.parseInt(linkId));
				request.setAttribute("link", link);
			}
			request.setAttribute("mainPage", "/background/link/editLink.jsp");
			request.getRequestDispatcher("/background/mainTemplate.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtil.closeConnection(con);
		}
	}

	/**
	 * 友情链接编辑完成
	 * 
	 * @param request
	 * @param response
	 */
	private void editFinished(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String linkId = request.getParameter("linkId");
		String linkName = request.getParameter("linkName");
		String linkUrl = request.getParameter("linkUrl");
		String linkEmail = request.getParameter("linkEmail");
		String orderNum = request.getParameter("orderNum");
		Link link = new Link(linkName, linkUrl, linkEmail, Integer.parseInt(orderNum));
		if (StringUtil.isNotEmpty(linkId)) {
			link.setLinkId(Integer.parseInt(linkId));
		}

		Connection con = null;
		try {
			con = dbUtil.getConnection();
			if (StringUtil.isNotEmpty(linkId)) {
				linkDao.updateLink(con, link);
			} else {
				linkDao.addLink(con, link);
			}
			response.sendRedirect("link?action=linkList");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtil.closeConnection(con);
		}
	}
}
