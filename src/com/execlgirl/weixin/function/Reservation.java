package com.execlgirl.weixin.function;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.execlgirl.weixin.util.MySQLUtil;

public class Reservation {

	public static int saveReservation(int number,String userid, String name, String grade) {
		MySQLUtil mysql = new MySQLUtil();
		Connection conn = mysql.getConnection();
		String sql = "insert into t_yy values(" + number + ",'" + userid + "','" + name
				+ "','" + grade + "')";
		System.out.println(sql);
		try {
			Statement stmt = conn.createStatement();
			int count = stmt.executeUpdate(sql);
			if ( count != -1 ) {
				return count;
			}
			stmt.close();
		} catch (SQLException e) {
			System.out.println("error " + e.getMessage());
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return -1;
	}
	
	public static User isExist(String userid) {
		
		MySQLUtil mysql = new MySQLUtil();
		Connection conn = mysql.getConnection();
		String sql = "select * from t_yy where userid = '" + userid + "'";
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if( rs.next() == false ) {
				rs.close();
				stmt.close();
				return null;
			}else {
				User u = new User();
				u.setNumber(rs.getInt("number"));
				u.setUserid(rs.getString("userid"));
				u.setName(rs.getString("name"));
				u.setGrade(rs.getString("grade"));
				rs.close();
				stmt.close();
				return u;
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	public static List<User> getAll(){
		
		List<User> list = new ArrayList<User>();
		MySQLUtil mysql = new MySQLUtil();
		Connection conn = mysql.getConnection();
		
		String sql = "select * from t_yy";
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				User user = new User();
				user.setNumber(rs.getInt("number"));
				user.setUserid(rs.getString("userid"));
				user.setName(rs.getString("name"));
				user.setGrade(rs.getString("grade"));
				list.add(user);
			}
			rs.close();
			stmt.close();
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	
	public static int getCount() {
		
		MySQLUtil mysql = new MySQLUtil();
		Connection conn = mysql.getConnection();
		
		String sql = "select count(*) as count from t_yy";
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			int count = rs.getInt("count");
			rs.close();
			stmt.close();
			return count;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return -1;
	}

	
	public static String dealContent(String content, String fromUserName){
		
		StringBuffer tm = new StringBuffer("");
		
		if(content.equalsIgnoreCase("reservation")){
			tm.append("the format about reservation is ：[reservation-name-class]");
		}
		else if(content.equalsIgnoreCase("check reservation")){
			User u = isExist(fromUserName);
			if (u != null) {
				tm.append("number:" + u.getNumber()).append("\n");
				tm.append("name:" + u.getName()).append("\n");
				tm.append("class:" + u.getGrade()).append("\n");
			} else if(u == null){
				tm.append("you have not sign.the format about reservation is ：[reservation-name-class]");
			} 
		}
		else if(content.equalsIgnoreCase("all reservation")){
			List<User> allUser = getAll();
			for( User user: allUser) {
				tm.append(user.getNumber()).append(" : ").append(user.getName());
				tm.append("\n").append(user.getGrade());
				tm.append("\n");
			}
		}
		else if(content.contains("reservation")){
			String[] info = content.split("-| ");
			if(info.length != 3){
				tm.append("format error! Please input again.");
			}else {
				String name = info[1];
				String grade = info[2];
				int count = getCount();
				
				if(++count > 40){
					tm.append("The house is crowded in every part.");
				}else {
					User existUser = isExist(fromUserName);
					if(existUser != null){
						tm.append("you have been signed! Don't repeat sign, please.");
					}else {
						int flag = saveReservation(count,fromUserName, name, grade);
						if (flag == -1) {
							tm.append("Error! Please input again.");
						} else {
							tm.append("reservate successful! Please input 'check reservation'!");
						}
					}
				}
			}
		}
  		
		return tm.toString();
	}
}




























