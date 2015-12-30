package com.execlgirl.weixin.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sina.sae.util.SaeUserInfo;


public class MySQLUtil {
	
	public Connection getConnection(){
		Connection conn = null;
		String url = "jdbc:mysql://w.rdc.sae.sina.com.cn:3307/app_excellencegirls";
		// 
		String username = SaeUserInfo.getAccessKey();
		String password = SaeUserInfo.getSecretKey();
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	

	public void releaseResource(Connection conn, PreparedStatement ps, ResultSet rs){
		try{
			if(null != rs){
				rs.close();
			}
			if(null != ps){
				ps.close();
			}
			if(null != conn){
				conn.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void saveTextMessage(String openId, String content){
		
		MySQLUtil mysql = new MySQLUtil();
		Connection conn = mysql.getConnection();
		
		String sql = "insert into message_text(open_id,content,create_time) values (?,?,now())";
		PreparedStatement ps = null;
		
		try{
			ps = conn.prepareStatement(sql);
		    ps.setString(1,openId);
		    ps.setString(2,content);
		    ps.execute();
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			mysql.releaseResource(conn, ps, null);
		}	
	}
	
	public static void saveWeixinUser(String openId){
	
		MySQLUtil mysql = new MySQLUtil();
		Connection conn = mysql.getConnection();
		
		String sql = "insert into weixin_user(open_id,subscribe_time,total_count) values (?,now(),?)";
		PreparedStatement ps = null;
		
		try{
			ps = conn.prepareStatement(sql);
		    ps.setString(1,openId);
		    ps.setInt(1,0);
		    ps.execute();
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			mysql.releaseResource(conn, ps, null);
		}	
	}

	
	public static void saveWeixinSign(String openId){
		
		MySQLUtil mysql = new MySQLUtil();
		Connection conn = mysql.getConnection();
		
		String sql = "insert into weixin_sign(open_id,sign_time,count) values (?,now(),1)";
		PreparedStatement ps = null;
		
		try{
			ps = conn.prepareStatement(sql);
		    ps.setString(1,openId);
		    ps.execute();
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			mysql.releaseResource(conn, ps, null);
		}	
	}
	
	public static int checkSignCount(String openId){
		
		MySQLUtil mysql = new MySQLUtil();
		Connection conn = mysql.getConnection();
		
		String sql = "select total_count from weixin_user where open_id = ?";
		PreparedStatement ps = null;
        ResultSet rs = null;
        int totalCount = 0;
		
		try{
			ps = conn.prepareStatement(sql);
		    ps.setString(1,openId);
		    rs = (ResultSet) ps.executeQuery();
		    
		    if(rs.next()){
		    	totalCount = rs.getInt("total_count");
		    }
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			mysql.releaseResource(conn, ps, rs);
		}
		return totalCount;
	}
	
	public static void updateUserTotalCount(String openId){
		
		MySQLUtil mysql = new MySQLUtil();
		Connection conn = mysql.getConnection();
		
		String sql = "update weixin_user set tatal_count = total_count + ? where open_id = ? ";
		PreparedStatement ps = null;
		
		try{
			ps = conn.prepareStatement(sql);
		    ps.setInt(1,1);
		    ps.setString(2,openId);
		    ps.execute();
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			mysql.releaseResource(conn, ps, null);
		}	
	}
	
	
	public static boolean isTodaySigned(String openId){
		
		boolean result = false;
		
		MySQLUtil mysql = new MySQLUtil();
		Connection conn = mysql.getConnection();
		
		String sql = "select sign_count FROM weixin_sign WHERE open_id = ? AND DATE_RORMAT(sign_time,'%Y-%m-%d') = DATE_FORMAT(now(),'%Y-%m-%d')";
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try{
			ps = conn.prepareStatement(sql);
		    ps.setString(1,openId);
		    rs = (ResultSet) ps.executeQuery();
		    
		    int signCounts = 0;
		    
		    if(rs.next()){
		    	signCounts = rs.getInt("sign_count");
		    }
		    
		    if(signCounts == 1){
		    	result = true;
		    }
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			mysql.releaseResource(conn, ps, rs);
		}	
		
		return result;
	}
	

}
