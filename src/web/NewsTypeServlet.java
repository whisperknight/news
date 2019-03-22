package web;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.NewsDao;
import dao.NewsTypeDao;
import model.NewsType;
import net.sf.json.JSONObject;
import util.DatabaseUtil;
import util.JsonUtil;
import util.StringUtil;

@WebServlet("/newsType")
public class NewsTypeServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	DatabaseUtil dbUtil=new DatabaseUtil();
	NewsDao newsDao=new NewsDao();
	NewsTypeDao newsTypeDao=new NewsTypeDao();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String action=request.getParameter("action");
		if(action.equals("preEdit")){
			this.perEdit(request, response);
		}else if(action.equals("editFinished")){
			this.editFinished(request, response);
		}else if(action.equals("newsTypeList")){
			this.getTypeList(request, response);
		}else if(action.equals("deleteNewsType")){
			this.deleteNewsType(request, response);
		}
	}

	/**
	 * 预编辑
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void perEdit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String newsTypeId=request.getParameter("newsTypeId");
		Connection con=null;
		try{
			con=dbUtil.getConnection();
			if(StringUtil.isNotEmpty(newsTypeId)){
				NewsType newsType=newsTypeDao.getNewsTypeByTypeId(con, Integer.parseInt(newsTypeId));
				request.setAttribute("newsType", newsType);
			}
			request.setAttribute("mainPage", "/background/newsType/editNewsType.jsp");
			request.getRequestDispatcher("/background/mainTemplate.jsp").forward(request, response);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
				dbUtil.closeConnection(con);
		}
	}
	
	/**
	 * 编辑完成
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void editFinished(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String newsTypeId=request.getParameter("newsTypeId");
		String typeName=request.getParameter("typeName");
		
		NewsType newsType=new NewsType(typeName);
		
		if(StringUtil.isNotEmpty(newsTypeId)){
			newsType.setNewsTypeId(Integer.parseInt(newsTypeId));
		}
		Connection con=null;
		try{
			con=dbUtil.getConnection();
			if(StringUtil.isNotEmpty(newsTypeId)){
				newsTypeDao.updateNewsType(con, newsType);
			}else{
				newsTypeDao.addNewsType(con, newsType);
			}
			response.sendRedirect("newsType?action=newsTypeList");
		}catch(Exception e){
			e.printStackTrace();
		}finally{
				dbUtil.closeConnection(con);
		}
	}
	
	/**
	 * 查所有
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void getTypeList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection con=null;
		try{
			con=dbUtil.getConnection();
			List<NewsType> newsTypeList=newsTypeDao.getNewsTypeList(con);
			request.setAttribute("newsTypeList", newsTypeList);
			request.setAttribute("mainPage", "/background/newsType/newsTypeList.jsp");
			request.getRequestDispatcher("/background/mainTemplate.jsp").forward(request, response);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
				dbUtil.closeConnection(con);
		}
		
	}
	
	/**
	 * 删
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void deleteNewsType(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String newsTypeId=request.getParameter("newsTypeId");
		Connection con=null;
		try{
			con=dbUtil.getConnection();
			JSONObject jo =new JSONObject();
			boolean exist=newsDao.existNewsWithNewsTypeId(con, Integer.parseInt(newsTypeId));
			if(exist){
				jo.put("info", "exist");
			}else{
				int result=newsTypeDao.deleteNewsType(con, Integer.parseInt(newsTypeId));
				if(result>0){
					jo.put("info", "success");
				}else{
					jo.put("info", "error");
				}
			}
			JsonUtil.write(jo, response);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
				dbUtil.closeConnection(con);
		}
	}
}
