package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.News;
import model.PageBean;
import util.DateUtil;
import util.PropertiesUtil;
import util.StringUtil;

public class NewsDao {
	/**
	 * 以指定sql获取新闻列表
	 * 
	 * @param con
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public List<News> getNewsList(Connection con, String sql) throws Exception {
		List<News> list = new ArrayList<>();
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			News news = new News();
			news.setNewsId(rs.getInt("newsId"));
			news.setTitle(rs.getString("title"));
			news.setContent(rs.getString("content"));
			news.setPublishTime(DateUtil.formatToDate(rs.getString("publishTime"), "yyyy-MM-dd HH:mm:ss"));
			news.setAuthor(rs.getString("author"));
			news.setTypeId(rs.getInt("typeId"));
			news.setClick(rs.getInt("click"));
			news.setIsHead(rs.getInt("isHead"));
			news.setIsImage(rs.getInt("isImage"));
			news.setImageName(PropertiesUtil.getValue("newsImageRelativePath") + "/" + rs.getString("imageName"));
			news.setIsHot(rs.getInt("isHot"));
			list.add(news);
		}
		return list;
	}

	/**
	 * 以指定分页范围查询特定类型的新闻
	 * 
	 * @param con
	 * @param news
	 * @param pageBean
	 * @return
	 * @throws Exception
	 */
	public List<News> getNewsList(Connection con, News s_news, PageBean pageBean, String startTime, String endTime) throws Exception {
		List<News> list = new ArrayList<>();
		StringBuffer sb = new StringBuffer("select * from t_news t1,t_newstype t2 where t1.typeId=t2.newsTypeId");
		
		if (s_news.getTypeId() != -1)
			sb.append(" and t1.typeId=" + s_news.getTypeId());
		if (StringUtil.isNotEmpty(s_news.getTitle()))
			sb.append(" and t1.title like '%" + s_news.getTitle() + "%'");
		if (StringUtil.isNotEmpty(startTime))
			sb.append(" and timestampdiff(second,'" + startTime + "',publishTime) >=0");
		if (StringUtil.isNotEmpty(endTime))
			sb.append(" and timestampdiff(second,'" + endTime + "',publishTime) <=0");
		
		sb.append(" order by t1.publishTime desc");
		if (pageBean != null)
			sb.append(" limit " + pageBean.getStart() + "," + pageBean.getPageSize());

		PreparedStatement ps = con.prepareStatement(sb.toString());
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			News news = new News();
			news.setNewsId(rs.getInt("newsId"));
			news.setTitle(rs.getString("title"));
			news.setContent(rs.getString("content"));
			news.setPublishTime(DateUtil.formatToDate(rs.getString("publishTime"), "yyyy-MM-dd HH:mm:ss"));
			news.setAuthor(rs.getString("author"));
			news.setTypeId(rs.getInt("typeId"));
			news.setTypeName(rs.getString("typeName"));
			news.setClick(rs.getInt("click"));
			news.setIsHead(rs.getInt("isHead"));
			news.setIsImage(rs.getInt("isImage"));
			news.setImageName(PropertiesUtil.getValue("newsImageRelativePath") + "/" + rs.getString("imageName"));
			news.setIsHot(rs.getInt("isHot"));
			list.add(news);
		}
		return list;
	}

	/**
	 * 获取指定条件的新闻总条数
	 * 
	 * @param con
	 * @param s_news
	 * @return
	 * @throws SQLException
	 */
	public int newsCount(Connection con, News s_news,String startTime, String endTime) throws SQLException {
		StringBuffer sb = new StringBuffer("select count(*) as total from t_news");

		// 指定类别
		if (s_news.getTypeId() != -1)
			sb.append(" and typeId=" + s_news.getTypeId());
		if (StringUtil.isNotEmpty(s_news.getTitle()))
			sb.append(" and title like '%" + s_news.getTitle() + "%'");
		if (StringUtil.isNotEmpty(startTime))
			sb.append(" and timestampdiff(second,'" + startTime + "',publishTime) >=0");
		if (StringUtil.isNotEmpty(endTime))
			sb.append(" and timestampdiff(second,'" + endTime + "',publishTime) <=0");

		PreparedStatement ps = con.prepareStatement(sb.toString().replaceFirst("and", "where"));
		ResultSet rs = ps.executeQuery();
		if (rs.next())
			return rs.getInt("total");
		else
			return 0;
	}

	/**
	 * 以新闻ID获取一条新闻
	 * 
	 * @param con
	 * @param newsId
	 * @return
	 * @throws Exception
	 */
	public News getNewsByNewsId(Connection con, int newsId) throws Exception {
		News news = new News();
		String sql = "select * from t_news t1,t_newstype t2 where t1.typeId=t2.newsTypeId and t1.newsId=?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, newsId);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			news.setNewsId(rs.getInt("newsId"));
			news.setTitle(rs.getString("title"));
			news.setContent(rs.getString("content"));
			news.setPublishTime(DateUtil.formatToDate(rs.getString("publishTime"), "yyyy-MM-dd HH:mm:ss"));
			news.setAuthor(rs.getString("author"));
			news.setTypeId(rs.getInt("typeId"));
			news.setTypeName(rs.getString("typeName"));
			news.setClick(rs.getInt("click"));
			news.setIsHead(rs.getInt("isHead"));
			news.setIsImage(rs.getInt("isImage"));
			news.setImageName(PropertiesUtil.getValue("newsImageRelativePath") + "/" + rs.getString("imageName"));
			news.setIsHot(rs.getInt("isHot"));
		}
		return news;
	}

	/**
	 * 新闻点击次数加一
	 * 
	 * @param con
	 * @param news
	 * @return
	 * @throws Exception
	 */
	public int newsClick(Connection con, int newsId) throws Exception {
		String sql = "update t_news set click=click+1 where newsId=?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, newsId);
		return ps.executeUpdate();
	}

	/**
	 * 获取上一个和下一个新闻到列表中，共两条，此新闻只包括newsId和title
	 */
	public List<News> getUpAndDownNewsById(Connection con, int typeId, int newsId) throws Exception {
		List<News> list = new ArrayList<>();
		// 上一条
		String sql = "select * from t_news where typeId=? and newsId>? order by newsId limit 1";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, typeId);
		ps.setInt(2, newsId);
		ResultSet rs = ps.executeQuery();
		News news = null;
		if (rs.next())
			news = new News(rs.getInt("newsId"), rs.getString("title"));
		else
			news = new News(-1, "");
		list.add(news);

		// 下一条
		sql = "select * from t_news where typeId=? and newsId<? order by newsId desc limit 1";
		ps = con.prepareStatement(sql);
		ps.setInt(1, typeId);
		ps.setInt(2, newsId);
		rs = ps.executeQuery();
		if (rs.next())
			news = new News(rs.getInt("newsId"), rs.getString("title"));
		else
			news = new News(-1, "");
		list.add(news);

		return list;
	}

	/**
	 * 判断该新闻类别下是否有新闻
	 * 
	 * @param con
	 * @param typeId
	 * @return
	 * @throws Exception
	 */
	public boolean existNewsWithNewsTypeId(Connection con, int typeId) throws Exception {
		String sql = "select * from t_news where typeId=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, typeId);
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 添加新闻
	 * 
	 * @param con
	 * @param news
	 * @return
	 * @throws Exception
	 */
	public int addNews(Connection con, News news) throws Exception {
		String sql = "insert into t_news values(null,?,?,now(),?,?,0,?,?,?,?)";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, news.getTitle());
		ps.setString(2, news.getContent());
		ps.setString(3, news.getAuthor());
		ps.setInt(4, news.getTypeId());
		ps.setInt(5, news.getIsHead());
		ps.setInt(6, news.getIsImage());
		ps.setString(7, news.getImageName().substring(news.getImageName().lastIndexOf("/") + 1));
		ps.setInt(8, news.getIsHot());
		return ps.executeUpdate();
	}

	/**
	 * 更新新闻
	 * 
	 * @param con
	 * @param news
	 * @return
	 * @throws Exception
	 */
	public int updateNews(Connection con, News news) throws Exception {
		String sql = "update t_news set title=?,content=?,author=?,typeId=?,isHead=?,isImage=?,imageName=?,isHot=? where newsId=?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, news.getTitle());
		ps.setString(2, news.getContent());
		ps.setString(3, news.getAuthor());
		ps.setInt(4, news.getTypeId());
		ps.setInt(5, news.getIsHead());
		ps.setInt(6, news.getIsImage());
		ps.setString(7, news.getImageName().substring(news.getImageName().lastIndexOf("/") + 1));
		ps.setInt(8, news.getIsHot());
		ps.setInt(9, news.getNewsId());
		return ps.executeUpdate();
	}

	/**
	 * 删除新闻
	 * 
	 * @param con
	 * @param newsId
	 * @return
	 * @throws Exception
	 */
	public int deleteNews(Connection con, int newsId) throws Exception {
		String sql = "delete from t_news where newsId=?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, newsId);
		return ps.executeUpdate();
	}
}
