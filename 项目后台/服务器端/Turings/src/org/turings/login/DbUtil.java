package org.turings.login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtil {
	private final String DRIVER_STR = "com.mysql.jdbc.Driver";
	private final String CONN_STR = "jdbc:mysql://localhost:3306/cakeonlinedb?characterEncoding=utf-8";
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
	//��ȡ���ݿ�����
	public Connection getConnection() throws ClassNotFoundException, SQLException {
		if(null == conn || conn.isClosed()) {
			Class.forName(DRIVER_STR);
			conn = DriverManager.getConnection(CONN_STR, USER, PWD);
		}
		return conn;
		
	}
	//�ر�����
	public void closeConnection() throws SQLException {
		if(null != conn || !conn.isClosed()) {
			conn.close();
		}
	}

}
