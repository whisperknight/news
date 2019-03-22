package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Link;

public class LinkDao {
	/**
	 * 以指定sql获取友情链接列表
	 * @param con
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public List<Link> getLinkList(Connection con, String sql) throws Exception{
		List<Link> list = new ArrayList<>();
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			Link link = new Link();
			link.setLinkId(rs.getInt("linkId"));
			link.setLinkName(rs.getString("linkName"));
			link.setLinkUrl(rs.getString("linkUrl"));
			link.setLinkEmail(rs.getString("linkEmail"));
			link.setOrderNum(rs.getInt("orderNum"));
			list.add(link);
		}
		return list;
	}
	
	/**
	 * 添加友情链接
	 * @param con
	 * @param link
	 * @return
	 * @throws Exception
	 */
	public int addLink(Connection con, Link link) throws Exception{
		String sql = "insert into t_link values(null,?,?,?,?)";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, link.getLinkName());
		ps.setString(2, link.getLinkUrl());
		ps.setString(3, link.getLinkEmail());
		ps.setInt(4, link.getOrderNum());
		return ps.executeUpdate();
	}
	
	/**
	 * 更新链接
	 * @param con
	 * @param link
	 * @return
	 * @throws Exception
	 */
	public int updateLink(Connection con, Link link) throws Exception {
		String sql = "update t_link set linkName=?,linkUrl=?,linkEmail=?,orderNum=? where linkId=?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, link.getLinkName());
		ps.setString(2, link.getLinkUrl());
		ps.setString(3, link.getLinkEmail());
		ps.setInt(4, link.getOrderNum());
		ps.setInt(5, link.getLinkId());
		return ps.executeUpdate();
	}
	
	/**
	 * 删除链接
	 * @param con
	 * @param linkId
	 * @return
	 * @throws Exception
	 */
	public int deleteLink(Connection con, int linkId) throws Exception {
		String sql = "delete from t_link where linkId=?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, linkId);
		return ps.executeUpdate();
	}
	
	/**
	 * 查询一条链接
	 * @param con
	 * @param linkId
	 * @return
	 * @throws Exception
	 */
	public Link getLinkByLinkId(Connection con, int linkId) throws Exception {
		Link link = null;
		String sql = "select * from t_link where linkId=?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, linkId);
		ResultSet rs = ps.executeQuery();
		if(rs.next()) {
			link = new Link();
			link.setLinkId(rs.getInt("linkId"));
			link.setLinkName(rs.getString("linkName"));
			link.setLinkUrl(rs.getString("linkUrl"));
			link.setLinkEmail(rs.getString("linkEmail"));
			link.setOrderNum(rs.getInt("orderNum"));
		}
		return link;
	}
}
