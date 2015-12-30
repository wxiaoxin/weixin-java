package com.execlgirl.weixin.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 签到功能，访问数据库的工具类
 * @author Administrator
 *
 */
public class MySQLUtil_Xin {

	// 查询用户今天是否已经签到
	public static boolean isTodaySigned(String openId) {

		Connection conn = DBUtil.getRConnection();
		String sql = "select * FROM weixin_sign WHERE open_id = '" + openId
				+ "'";
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			System.out.println("error isTodaySigned " + e.getMessage());
			e.printStackTrace();
		} finally {
			DBUtil.closeStmt(conn, stmt);
		}
		return false;
	}

	// 签到
	public static int sign(String openId) {
		Connection conn = DBUtil.getWConnection();
		String sql = "insert into weixin_sign(open_id,sign_time) values('" + openId + "'," + "DATE_FORMAT(now(),'%Y-%m-%d')" + ")";
		System.out.println("LOG INSERT " + sql);
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			// 更新总的签到次数，并获取总得签到次数返回给用户
			int count = updateSignCount(openId);
			return count;
		} catch (SQLException e) {
			System.out.println("error sign " + e.getMessage());
			e.printStackTrace();
		} finally {
			DBUtil.closeStmt(conn, stmt);
		}
		return -1;
	}
	
	// 更新总的签到次数，然后返回给用户总的签到次数
	private static int updateSignCount(String openId){
		isHasSign(openId);
		Connection conn = DBUtil.getWConnection();
		String update_sql = "update weixin_user set total_count = total_count + 1 where open_id = '" + openId + "'";
		System.out.println("LOG " + update_sql);
		String getcount_sql = "select total_count from weixin_user where open_id = '" + openId + "'";
		System.out.println("LOG " + getcount_sql);
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(update_sql);
			ResultSet rs = stmt.executeQuery(getcount_sql);
			rs.next();
			int total_count = rs.getInt("total_count");
			return total_count;
		} catch (SQLException e) {
			System.out.println("error updateSignCount " + e.getMessage());
			e.printStackTrace();
		} finally {
			DBUtil.closeStmt(conn, stmt);
		}
		return -1;
	}
	
	// 判断用户曾经是否已经签到。即在 weixin_user表中是否有记录
	private static void isHasSign(String openId){
		Connection conn = DBUtil.getRConnection();
		String sql = "select * from weixin_user where open_id = '" + openId + "'";
		System.out.println("LOG " + sql);
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if(!rs.next()){
				// 如果用户曾经为签到，则插入第一条签到记录，签到次数为1
				String insert_sql = "insert into weixin_user(open_id,total_count) values('" + openId + "',1)";
				System.out.println("LOG " + insert_sql);
				stmt.executeUpdate(insert_sql);
			}
		} catch (SQLException e) {
			System.out.println("error isHasSign " + e.getMessage());
			e.printStackTrace();
		} finally {
			DBUtil.closeStmt(conn, stmt);
		}
		
	}
	
	// 获取总签到次数，为了测试方便用。测试指令 QDCS
		public static int getTotalCount(String openId){
			Connection conn = DBUtil.getRConnection();
			String sql = "select total_count from weixin_user where open_id = '" + openId + "'";;
			System.out.println("LOG " + sql);
			Statement stmt = null;
			try {
				stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				rs.next();
				int count = rs.getInt("total_count");
				return count;
			} catch (SQLException e) {
				System.out.println("error getTotalCount " + e.getMessage());
				e.printStackTrace();
			} finally {
				DBUtil.closeStmt(conn, stmt);
			}
			return -1;
		}

}
