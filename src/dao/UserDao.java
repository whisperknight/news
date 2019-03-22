package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.User;
import util.MD5Util;

public class UserDao {
	
	/**
	 * 登录查询
	 * @param con
	 * @param user
	 * @return
	 */
	public User login(Connection con, User user) {
		User resultUser = null;
		try {
			String sql = "select * from t_user where userName = ? and password = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, user.getUserName());
			ps.setString(2, MD5Util.encodingPasswordByMD5(user.getPassword()));
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				resultUser = new User();
				resultUser.setUserId(rs.getInt("userId"));
				resultUser.setUserName(rs.getString("userName"));
				resultUser.setPassword(user.getPassword());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultUser;
	}
	
	/*
	public int updateUser(Connection con, User user) throws Exception{
		String sql = "update t_user set nickName=?, imageName=?, mood=? where userId=?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, user.getNickName());
		ps.setString(2, user.getImage().substring(user.getImage().lastIndexOf("/") + 1));
		ps.setString(3, user.getMood());
		ps.setInt(4, user.getUserId());
		return ps.executeUpdate();
	}
	*/
}
