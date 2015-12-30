package com.execlgirl.weixin.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import com.sina.sae.util.SaeUserInfo;

public class DBUtil {

	// 主数据库，写
	private static final String WURL = "jdbc:mysql://w.rdc.sae.sina.com.cn:3307/app_excellencegirls";
	// 从数据库，读
	private static final String RURL = "jdbc:mysql://r.rdc.sae.sina.com.cn:3307/app_excellencegirls";
	private static final String USER = SaeUserInfo.getAccessKey();
	private static final String PASSWORD = SaeUserInfo.getSecretKey();
	private static final String DRIVER = "com.mysql.jdbc.Driver";

	// 链接到主数据库，用于写操作
	public static Connection getWConnection() {
		try {
			Class.forName(DRIVER);
			return DriverManager.getConnection(WURL, USER, PASSWORD);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	// 链接到从数据库，用于读操作
	public static Connection getRConnection() {
		try {
			Class.forName(DRIVER);
			return DriverManager.getConnection(RURL, USER, PASSWORD);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void closeStmt(Connection conn, Statement stmt) {

		try {
			if (conn != null) {
				conn.close();
			}
			if( stmt != null ) {
				stmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void closeAll(Connection conn, Statement stmt,
			PreparedStatement pstmt) {

		try {
			if (conn != null) {
				conn.close();
			}
			if( stmt != null ) {
				stmt.close();
			}
			if( pstmt != null ) {
				pstmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
