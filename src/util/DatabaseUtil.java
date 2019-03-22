package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseUtil {
	/**
	 * 建立数据库连接
	 * @return
	 * @throws Exception
	 */
	public Connection getConnection() throws Exception {
		Class.forName(PropertiesUtil.getValue("jdbcDriverName"));
		Connection con = DriverManager.getConnection(PropertiesUtil.getValue("databaseUrl"),
				PropertiesUtil.getValue("userName"), PropertiesUtil.getValue("password"));
		return con;
	}

	/**
	 * 关闭数据库连接
	 * @param con
	 */
	public void closeConnection(Connection con) {
		try {
			if (con != null)
				con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 测试连接
	 * @param args
	 */
	public static void main(String[] args) {
		DatabaseUtil dbUtil = new DatabaseUtil();
		try {
			dbUtil.getConnection();
			System.out.println("数据库连接成功");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("数据库连接失败");
		}
	}

}
