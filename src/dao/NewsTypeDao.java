package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.NewsType;

public class NewsTypeDao {
	/**
	 * 查所有
	 * @param con
	 * @return
	 * @throws Exception
	 */
	public List<NewsType> getNewsTypeList(Connection con) throws Exception{
		List<NewsType> list = new ArrayList<>();
		String sql = "select * from t_newstype";
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			NewsType newsType = new NewsType();
			newsType.setNewsTypeId(rs.getInt("newsTypeId"));
			newsType.setTypeName(rs.getString("typeName"));
			list.add(newsType);
		}
		return list;
		
	}
	
	/**
	 * 查
	 * @param con
	 * @param typeId
	 * @return
	 * @throws Exception
	 */
	public NewsType getNewsTypeByTypeId(Connection con, int typeId) throws Exception{
		NewsType newsType = new NewsType();
		String sql = "select * from t_newstype where newsTypeId=?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, typeId);
		ResultSet rs = ps.executeQuery();
		if(rs.next()) {
			newsType.setNewsTypeId(rs.getInt("newsTypeId"));
			newsType.setTypeName(rs.getString("typeName"));
		}
		return newsType;
	}
	
	/**
	 * 增
	 * @param con
	 * @param newsType
	 * @return
	 * @throws Exception
	 */
	public int addNewsType(Connection con, NewsType newsType) throws Exception{
		String sql = "insert into t_newstype values(null,?)";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, newsType.getTypeName());
		return ps.executeUpdate();
	}
	
	/**
	 * 改
	 * @param con
	 * @param newsType
	 * @return
	 * @throws Exception
	 */
	public int updateNewsType(Connection con, NewsType newsType) throws Exception {
		String sql = "update t_newstype set typeName=? where newsTypeId=?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, newsType.getTypeName());
		ps.setInt(2, newsType.getNewsTypeId());
		return ps.executeUpdate();
	}
	
	/**
	 * 删
	 * @param con
	 * @param newsTypeId
	 * @return
	 * @throws Exception
	 */
	public int deleteNewsType(Connection con, int newsTypeId) throws Exception {
		String sql = "delete from t_newstype where newsTypeId=?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, newsTypeId);
		return ps.executeUpdate();
	}
}
