package org.turings.login.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.turings.DbUtil;


public class UserDao {

	public UserDao() {
		// TODO Auto-generated constructor stub
	}

	public String queryUser(String sql) {
		String result="false";
		DbUtil dbUtil = DbUtil.getInstance();
		Connection conn;
		try {
			conn = dbUtil.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next()) {
				result=rs.getInt("uId")+"";
			} 
			
			dbUtil.closeConnection();
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return result;
	}

	public boolean modifyUser(String sql) {
		boolean result=false;
		DbUtil dbUtil = DbUtil.getInstance();
		Connection conn;
		try {
			conn = dbUtil.getConnection();
			Statement stmt = conn.createStatement();
			int n = stmt.executeUpdate(sql);
			if(n>0) {
				result=true;
			} 
			
			dbUtil.closeConnection();
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return result;
	}

}
