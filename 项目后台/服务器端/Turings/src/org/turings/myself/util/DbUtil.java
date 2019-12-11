package org.turings.myself.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtil {
	private final String DRIVER_STR = "com.mysql.jdbc.Driver";
	private final String CONN_STR = "jdbc:mysql://localhost:3306/turings_db?characterEncoding=utf-8";
	private final String USER = "root";
	private final String PWD = "";
	private static DbUtil dbUtil;
	private Connection conn;
	
	private DbUtil() {
		
	}
	public static DbUtil getInstance(){
		if(null == dbUtil) {
			dbUtil = new DbUtil();
		}
		return dbUtil;
	}
	//获取数据库连�?
	public Connection getConnection() throws ClassNotFoundException, SQLException {
		if(null == conn || conn.isClosed()) {
			Class.forName(DRIVER_STR);
			conn = DriverManager.getConnection(CONN_STR, USER, PWD);
		}
		return conn;
		
	}
	//关闭连接
	public void closeConnection() throws SQLException {
		if(null != conn || !conn.isClosed()) {
			conn.close();
		}
	}

}
