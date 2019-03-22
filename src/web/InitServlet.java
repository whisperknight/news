package web;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.LinkDao;
import dao.NewsDao;
import dao.NewsTypeDao;
import model.Link;
import model.News;
import model.NewsType;
import net.sf.json.JSONObject;
import util.DatabaseUtil;
import util.JsonUtil;

@WebServlet(urlPatterns="/init",loadOnStartup=1)
public class InitServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	DatabaseUtil dbUtil = new DatabaseUtil();
	NewsDao newsDao = new NewsDao();
	NewsTypeDao newsTypeDao = new NewsTypeDao();
	LinkDao linkDao = new LinkDao();

	@Override
	public void init(ServletConfig config) throws ServletException {
		ServletContext application = config.getServletContext();
		this.refreshSystem(application);
	}

	/**
	 * 初始化内容到application
	 * @param application
	 */
	private void refreshSystem(ServletContext application) {
		Connection con = null;
		try {
			con = dbUtil.getConnection();

			// 获取新闻类型列表到application
			List<NewsType> newsTypeList = newsTypeDao.getNewsTypeList(con);
			application.setAttribute("newsTypeList", newsTypeList);

			// 获取最近更新的新闻列表到application
			String sql = "select * from t_news order by publishTime desc limit 0,8";
			List<News> rescentNewsList = newsDao.getNewsList(con, sql);
			application.setAttribute("rescentNewsList", rescentNewsList);

			// 获取热门（根据点击量）新闻列表到application
			sql = "select * from t_news order by click desc limit 0,8";
			List<News> hotNewsList = newsDao.getNewsList(con, sql);
			application.setAttribute("hotNewsList", hotNewsList);

			//获取友情链接列表到request
			sql = "select * from t_link order by orderNum";
			List<Link> linkList = linkDao.getLinkList(con, sql);
			application.setAttribute("linkList", linkList);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtil.closeConnection(con);
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		this.refreshSystem(request.getSession().getServletContext());
		JSONObject jo = new JSONObject();
		jo.put("info", "success");
		try {
			JsonUtil.write(jo, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
