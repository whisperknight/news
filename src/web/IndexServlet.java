package web;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.NewsDao;
import dao.NewsTypeDao;
import model.News;
import model.NewsType;
import util.DatabaseUtil;
import util.StringUtil;

@WebServlet("/goIndex")
public class IndexServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	DatabaseUtil dbUtil = new DatabaseUtil();
	NewsDao newsDao = new NewsDao();
	NewsTypeDao newsTypeDao = new NewsTypeDao();
	
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		Connection con = null;
		try {
			con = dbUtil.getConnection();
			
			//获取新闻类型列表到request
			@SuppressWarnings("unchecked")
			List<NewsType> newsTypeList = (List<NewsType>) request.getServletContext().getAttribute("newsTypeList");
			
			//获取图片新闻列表到request
			String sql = "select * from t_news where isImage=1 order by publishTime desc limit 0,5";
			List<News> imageNewsList = newsDao.getNewsList(con, sql);
			request.setAttribute("imageNewsList", imageNewsList);
			
			//获取头条新闻到request
			sql = "select * from t_news where isHead=1 order by publishTime desc limit 0,1";
			List<News> headNewsList = newsDao.getNewsList(con, sql);
			News headNews = headNewsList.get(0);
			headNews.setContent(StringUtil.htmlToText(headNews.getContent()));
			request.setAttribute("headNews", headNews);
			
			//获取热点新闻列表到request
			sql = "select * from t_news where isHot=1 order by publishTime desc limit 0,8";
			List<News> hotSpotNewsList = newsDao.getNewsList(con, sql);
			request.setAttribute("hotSpotNewsList", hotSpotNewsList);
			
			//获取各版块的新闻二维列表到request
			List<List<News>> allTypeNewsList = new ArrayList<>();
			for(int i=0;i<newsTypeList.size();i++) {
				sql = "select * from t_news,t_newstype where typeId=newsTypeId and newsTypeId="+ newsTypeList.get(i).getNewsTypeId() +" order by publishTime desc limit 0,8";
				List<News> tempList = newsDao.getNewsList(con, sql);
				allTypeNewsList.add(tempList);
			}
			request.setAttribute("allTypeNewsList", allTypeNewsList);
			
			request.getRequestDispatcher("index.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtil.closeConnection(con);
		}
	}

}
