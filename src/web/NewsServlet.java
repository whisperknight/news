package web;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import dao.CommentDao;
import dao.NewsDao;
import dao.NewsTypeDao;
import model.Comment;
import model.News;
import model.NewsType;
import model.PageBean;
import net.sf.json.JSONObject;
import util.DatabaseUtil;
import util.DateUtil;
import util.JsonUtil;
import util.PropertiesUtil;
import util.StringUtil;

@WebServlet("/news")
public class NewsServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	DatabaseUtil dbUtil = new DatabaseUtil();
	NewsDao newsDao = new NewsDao();
	NewsTypeDao newsTypeDao = new NewsTypeDao();
	CommentDao commentDao = new CommentDao();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		if (action.equals("list")) {
			newsList(request, response);
		} else if (action.equals("show")) {
			newsShow(request, response);
		} else if (action.equals("preEdit")) {
			preEdit(request, response);
		} else if (action.equals("editFinished")) {
			editFinished(request, response);
		} else if (action.equals("newsList_back")) {
			newsList_back(request, response);
		} else if (action.equals("delete_back")) {
			delete_back(request, response);
		}
	}

	/**
	 * 后台删除新闻
	 * 
	 * @param request
	 * @param response
	 */
	private void delete_back(HttpServletRequest request, HttpServletResponse response) {
		String newsId = request.getParameter("newsId");

		Connection con = null;
		try {
			con = dbUtil.getConnection();

			int result = newsDao.deleteNews(con, Integer.parseInt(newsId));
			JSONObject jo = new JSONObject();
			if (result > 0)
				jo.put("info", "success");
			else
				jo.put("info", "error");
			JsonUtil.write(jo, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 显示一条新闻
	 * 
	 * @param request
	 * @param response
	 */
	private void newsShow(HttpServletRequest request, HttpServletResponse response) {
		String newsId = request.getParameter("newsId");
		String typeId = request.getParameter("typeId");

		Connection con = null;
		try {
			con = dbUtil.getConnection();

			// 点击量加一
			newsDao.newsClick(con, Integer.parseInt(newsId));

			// 获取当前新闻
			News news = newsDao.getNewsByNewsId(con, Integer.parseInt(newsId));
			request.setAttribute("news", news);

			// 获取上下页中的新闻标题显示
			List<News> upAndDownNews = newsDao.getUpAndDownNewsById(con, Integer.parseInt(typeId),
					Integer.parseInt(newsId));
			News upNews = upAndDownNews.get(0);
			News downNews = upAndDownNews.get(1);
			request.setAttribute("upNews", upNews);
			request.setAttribute("downNews", downNews);

			// 获取这条新闻的所有评论
			Comment s_comment = new Comment();
			s_comment.setNewsId(Integer.parseInt(newsId));
			List<Comment> commentList = commentDao.getCommentList(con, s_comment, null, null, null);
			request.setAttribute("commentList", commentList);

			request.setAttribute("mainPage", "news/newsShow.jsp");
			request.getRequestDispatcher("foreground/newsTemplate.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 显示新闻列表
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private void newsList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 封装查询类别的News
		String typeId = request.getParameter("typeId");
		News s_news = new News();
		if (StringUtil.isNotEmpty(typeId))
			s_news.setTypeId(Integer.parseInt(typeId));

		// 封装查询分页的PageBean
		String currentPage = request.getParameter("page");
		if (StringUtil.isEmpty(currentPage))
			currentPage = "1";
		PageBean pageBean = new PageBean(Integer.parseInt(currentPage),
				Integer.parseInt(PropertiesUtil.getValue("pageSize")));

		Connection con = null;
		try {
			con = dbUtil.getConnection();
			List<News> newsList_type_limit = newsDao.getNewsList(con, s_news, pageBean, null, null);

			// 返回查到的新闻列表
			request.setAttribute("newsList_type_limit", newsList_type_limit);

			// 返回当前页数、总页数
			int pageSize = Integer.parseInt(PropertiesUtil.getValue("pageSize"));
			int newsCount = newsDao.newsCount(con, s_news, null, null);
			int totalPage = newsCount % pageSize == 0 ? newsCount / pageSize : newsCount / pageSize + 1;
			request.setAttribute("totalPage", totalPage);
			request.setAttribute("currentPage", currentPage);

			// 返回采用newsList.jsp做主页
			request.setAttribute("mainPage", "news/newsList.jsp");
			request.getRequestDispatcher("foreground/newsTemplate.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtil.closeConnection(con);
		}
	}

	/**
	 * 后台开始编辑新闻
	 * 
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	private void preEdit(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		String newsId = request.getParameter("newsId");

		Connection con = null;
		try {
			con = dbUtil.getConnection();

			// 获取当前新闻
			if (StringUtil.isNotEmpty(newsId)) {
				News news = newsDao.getNewsByNewsId(con, Integer.parseInt(newsId));
				request.setAttribute("news", news);
			}

			List<NewsType> newsTypeList = newsTypeDao.getNewsTypeList(con);
			request.setAttribute("newsTypeList", newsTypeList);

			request.setAttribute("mainPage", "news/editNews.jsp");
			request.getRequestDispatcher("background/mainTemplate.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 后台编辑完成
	 * 
	 * @param request
	 * @param response
	 */
	private void editFinished(HttpServletRequest request, HttpServletResponse response) {

		try {
			Connection con = null;
			try {
				con = dbUtil.getConnection();
				String newsId = null;
				News news = new News();

				FileItemFactory factory = new DiskFileItemFactory();
				ServletFileUpload upload = new ServletFileUpload(factory);

				@SuppressWarnings("unchecked")
				List<FileItem> fileItemList = upload.parseRequest(request);

				for (var item : fileItemList) {
					// 如果提交的是表单
					if (item.isFormField()) {
						String fieldName = item.getFieldName();
						if (fieldName.equals("newsId")) {
							newsId = item.getString("utf-8");
							if(StringUtil.isNotEmpty(newsId))
								news.setNewsId(Integer.parseInt(newsId));
						} else if (fieldName.equals("title")) {
							news.setTitle(item.getString("utf-8"));
						} else if (fieldName.equals("content")) {
							news.setContent(item.getString("utf-8"));
						} else if (fieldName.equals("author")) {
							news.setAuthor(item.getString("utf-8"));
						} else if (fieldName.equals("typeId")) {
							news.setTypeId(Integer.parseInt(item.getString("utf-8")));
						} else if (fieldName.equals("isHead")) {
							news.setIsHead(Integer.parseInt(item.getString("utf-8")));
						} else if (fieldName.equals("isImage")) {
							news.setIsImage(Integer.parseInt(item.getString("utf-8")));
						} else if (fieldName.equals("upload-view")) {
							news.setImageName(item.getString("utf-8"));
						} else if (fieldName.equals("isHot")) {
							news.setIsHot(Integer.parseInt(item.getString("utf-8")));
						}
					}
					// 如果提交的是文件且文件名不为空串
					else if (item.getName() != null && !item.getName().equals("")) {
						// 是更新则删除原文件（暂时没有实现不是轮播则删除原轮播图片）
						if (StringUtil.isNotEmpty(newsId)) {
							File old_file = new File(
									PropertiesUtil.getValue("projectAbsolutePath") + news.getImageName());
							if (old_file.exists() && old_file.isFile())
								old_file.delete();
						}

						String newImageNameWithoutSuffix = DateUtil.getCurrentDateStrForFileName();
						String imageName = newImageNameWithoutSuffix
								+ item.getName().substring(item.getName().lastIndexOf("."));
						String url = PropertiesUtil.getValue("newsImageRelativePath") + "/" + imageName;
						// 写入文件
						item.write(new File(PropertiesUtil.getValue("projectAbsolutePath") + url));
						// 写入news对象（这里写url是为了方便万一想在页面上显示）
						news.setImageName(url);
					}
				}

				int result = 0;
				if (StringUtil.isNotEmpty(newsId))
					result = newsDao.updateNews(con, news);
				else
					result = newsDao.addNews(con, news);
				if (result > 0) {
					response.sendRedirect("news?action=newsList_back");
				} else {
					request.setAttribute("news", news);
					request.setAttribute("error", "保存失败！");
					request.setAttribute("mainPage", "news/editNews.jsp");
					request.getRequestDispatcher("main.jsp").forward(request, response);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					dbUtil.closeConnection(con);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * 显示新闻列表
	 * 
	 * @param request
	 * @param response
	 */
	private void newsList_back(HttpServletRequest request, HttpServletResponse response) {
		// 获取时间段
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String title = request.getParameter("title");
		// 获取当前页
		String currentPage = request.getParameter("page");
		if (StringUtil.isEmpty(currentPage)) {
			currentPage = "1";
			request.getSession().setAttribute("startTime", startTime);
			request.getSession().setAttribute("endTime", endTime);
			request.getSession().setAttribute("title", title);
		} else {
			startTime = (String) request.getSession().getAttribute("startTime");
			endTime = (String) request.getSession().getAttribute("endTime");
			title = (String) request.getSession().getAttribute("title");
		}

		News s_news = new News();
		if (StringUtil.isNotEmpty(title))
			s_news.setTitle(title);
		Connection con = null;
		try {
			con = dbUtil.getConnection();

			// 封装pageBean
			int pageSize_back = Integer.parseInt(PropertiesUtil.getValue("pageSize_back"));
			PageBean pageBean = new PageBean(Integer.parseInt(currentPage), pageSize_back);

			// 查询
			List<News> newsList_back = newsDao.getNewsList(con, s_news, pageBean, startTime, endTime);
			request.setAttribute("newsList_back", newsList_back);

			// 返回总页数和当前页数到前端
			int totalNews = newsDao.newsCount(con, s_news, startTime, endTime);
			int totalPage = totalNews % pageSize_back == 0 ? totalNews / pageSize_back : totalNews / pageSize_back + 1;
			request.setAttribute("currentPage", currentPage);
			request.setAttribute("totalPage", totalPage);

			// request.setAttribute("startTime", startTime);
			// request.setAttribute("endTime", endTime);
			request.setAttribute("mainPage", "/background/news/newsList.jsp");
			request.getRequestDispatcher("/background/mainTemplate.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtil.closeConnection(con);
		}
	}
}
