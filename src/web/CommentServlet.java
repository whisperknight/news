package web;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CommentDao;
import model.Comment;
import model.PageBean;
import net.sf.json.JSONObject;
import util.DatabaseUtil;
import util.DateUtil;
import util.IPUtil;
import util.JsonUtil;
import util.PropertiesUtil;
import util.StringUtil;

@WebServlet("/comment")
public class CommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	DatabaseUtil dbUtil = new DatabaseUtil();
	CommentDao commentDao = new CommentDao();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String action = request.getParameter("action");
		if (action.equals("add")) {
			addComment(request, response);
		} else if (action.equals("commentList_back")) {
			commentList_back(request, response);
		} else if (action.equals("delete_back")) {
			delete_back(request, response);
		}
	}

	/**
	 * 后台批量删除评论
	 * 
	 * @param request
	 * @param response
	 */
	private void delete_back(HttpServletRequest request, HttpServletResponse response) {
		String commentIds = request.getParameter("commentIds");
		Connection con = null;
		try {
			con = dbUtil.getConnection();
			int result = commentDao.deleteComment(con, commentIds);
			JSONObject jo = new JSONObject();
			if (result > 0)
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
	 * 后台获取评论列表
	 * 
	 * @param request
	 * @param response
	 */
	private void commentList_back(HttpServletRequest request, HttpServletResponse response) {
		// 获取时间段
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");

		// 获取当前页
		String currentPage = request.getParameter("page");
		if (StringUtil.isEmpty(currentPage)) {
			currentPage = "1";
			request.getSession().setAttribute("startTime", startTime);
			request.getSession().setAttribute("endTime", endTime);
		} else {
			startTime = (String) request.getSession().getAttribute("startTime");
			endTime = (String) request.getSession().getAttribute("endTime");
		}

		Connection con = null;
		try {
			con = dbUtil.getConnection();

			// 封装pageBean
			int pageSize_back = Integer.parseInt(PropertiesUtil.getValue("pageSize_back"));
			PageBean pageBean = new PageBean(Integer.parseInt(currentPage), pageSize_back);

			// 查询
			List<Comment> commentList_back = commentDao.getCommentList(con, new Comment(), startTime, endTime,
					pageBean);
			request.setAttribute("commentList_back", commentList_back);

			// 返回总页数和当前页数到前端
			int totalComment = commentDao.commentCount(con, new Comment(), startTime, endTime);
			int totalPage = totalComment % pageSize_back == 0 ? totalComment / pageSize_back
					: totalComment / pageSize_back + 1;
			request.setAttribute("currentPage", currentPage);
			request.setAttribute("totalPage", totalPage);

			request.setAttribute("startTime", startTime);
			request.setAttribute("endTime", endTime);
			request.setAttribute("mainPage", "/background/comment/commentList.jsp");
			request.getRequestDispatcher("/background/mainTemplate.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtil.closeConnection(con);
		}
	}

	/**
	 * 添加评论
	 * 
	 * @param request
	 * @param response
	 */
	private void addComment(HttpServletRequest request, HttpServletResponse response) {
		String newsId = request.getParameter("newsId");
		String content = request.getParameter("content");
		String userIP = IPUtil.getIP(request);
		Comment comment_add = new Comment(Integer.parseInt(newsId), content, userIP);

		Connection con = null;
		try {
			con = dbUtil.getConnection();
			Comment comment = commentDao.addComment(con, comment_add);
			if (comment != null) {
				// 采用json返回
				JSONObject jo_value = new JSONObject();
				jo_value.put("userIP", comment.getUserIP());
				jo_value.put("content", comment.getContent());
				jo_value.put("commentTime", DateUtil.formatToString(comment.getCommentTime(), "yyyy-MM-dd HH:mm"));

				JSONObject jo = new JSONObject();
				jo.put("comment", jo_value);
				JsonUtil.write(jo, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtil.closeConnection(con);
		}
	}

}
