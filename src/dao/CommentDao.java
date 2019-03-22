package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Comment;
import model.PageBean;
import util.DateUtil;
import util.StringUtil;

public class CommentDao {

	/**
	 * 根据给定的条件查询所有满足条件的comment列表
	 * 
	 * @param con
	 * @param s_comment
	 * @return
	 * @throws Exception
	 */
	public List<Comment> getCommentList(Connection con, Comment s_comment, String startTime, String endTime,
			PageBean pageBean) throws Exception {
		List<Comment> list = new ArrayList<>();
		StringBuffer sb = new StringBuffer("select * from t_comment t1, t_news t2 where t1.newsId=t2.newsId");
		if (s_comment.getNewsId() != -1)
			sb.append(" and t1.newsId=" + s_comment.getNewsId());
		if (StringUtil.isNotEmpty(startTime))
			sb.append(" and timestampdiff(second,'" + startTime + "',commentTime) >=0");
		if (StringUtil.isNotEmpty(endTime))
			sb.append(" and timestampdiff(second,'" + endTime + "',commentTime) <=0");

		sb.append(" order by t1.commentTime desc");
		if(pageBean != null)
			sb.append(" limit " + pageBean.getStart() + "," + pageBean.getPageSize());

		PreparedStatement ps = con.prepareStatement(sb.toString());
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Comment comment = new Comment();
			comment.setCommentId(rs.getInt("commentId"));
			comment.setNewsId(rs.getInt("newsId"));
			comment.setTypeId(rs.getInt("typeId"));
			comment.setNewsTitle(rs.getString("title"));
			comment.setContent(rs.getString("content"));
			comment.setUserIP(rs.getString("userIP"));
			comment.setCommentTime(DateUtil.formatToDate(rs.getString("commentTime"), "yyyy-MM-dd HH:mm:ss"));
			list.add(comment);
		}
		return list;
	}

	/**
	 * 得到查询总数，便于分页
	 * 
	 * @param con
	 * @param s_comment
	 * @param bCommentDate
	 * @param aCommentDate
	 * @return
	 * @throws Exception
	 */
	public int commentCount(Connection con, Comment s_comment, String startTime, String endTime) throws Exception {
		StringBuffer sb = new StringBuffer("select count(*) as total from t_comment");
		if (s_comment.getNewsId() != -1)
			sb.append(" and newsId=" + s_comment.getNewsId());
		if (StringUtil.isNotEmpty(startTime))
			sb.append(" and timestampdiff(second,'" + startTime + "',commentTime) >=0");
		if (StringUtil.isNotEmpty(endTime))
			sb.append(" and timestampdiff(second,'" + endTime + "',commentTime) <=0");

		PreparedStatement ps = con.prepareStatement(sb.toString().replaceFirst("and", "where"));
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			return rs.getInt("total");
		}
		return 0;
	}

	/**
	 * 使用了存储过程，添加一条评论，返回评论的所有内容
	 * 
	 * @param con
	 * @param comment
	 * @return comment
	 * @throws Exception
	 */
	public Comment addComment(Connection con, Comment comment_add) throws Exception {
		String sql = "{call pro_addComment_returnCommentId(?, ?, ?, ?)}";
		CallableStatement cs = con.prepareCall(sql);
		cs.setInt(1, comment_add.getNewsId());
		cs.setString(2, comment_add.getContent());
		cs.setString(3, comment_add.getUserIP());

		cs.registerOutParameter(4, java.sql.Types.INTEGER);
		cs.execute();
		int commentId = cs.getInt(4);

		sql = "select * from t_comment where commentId=" + commentId;
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			Comment comment = new Comment();
			comment.setCommentId(rs.getInt("commentId"));
			comment.setNewsId(rs.getInt("newsId"));
			comment.setContent(rs.getString("content"));
			comment.setUserIP(rs.getString("userIP"));
			comment.setCommentTime(DateUtil.formatToDate(rs.getString("commentTime"), "yyyy-MM-dd HH:mm:ss"));
			return comment;
		}
		return null;
	}
	
	/**
	 * 批量删除
	 * @param con
	 * @param commentIds 删除的所有id，用逗号隔开
	 * @return
	 * @throws Exception
	 */
	public int deleteComment(Connection con, String commentIds) throws Exception {
		String sql = "delete from t_comment where commentId in ("+commentIds+")";
		PreparedStatement ps = con.prepareStatement(sql);
		return ps.executeUpdate();
	}
}
