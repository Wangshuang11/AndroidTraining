package org.turings.myself.userinfomation.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.turings.myself.entity.Myself;
import org.turings.myself.util.DbUtil;

public class UserInformationDao {

	public Myself getInfomationList(int uid) {
		Connection conn = null;
		
		Myself user =null ;
		DbUtil dbUtil = DbUtil.getInstance();
		System.out.println("金鑫�?-getFansList"+"打开数据�?");
		
		try {
			conn = dbUtil.getConnection();
			String sql = "select * from tbl_self_user where uId = ? ";
			
			
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setInt(1, uid);
			ResultSet rs=pstm.executeQuery();
			
			
			while(rs.next()) {
				// 在这里把查到的粉丝名字加入list
				user = new Myself(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getInt(7),rs.getInt(8),rs.getInt(9),rs.getInt(10),rs.getInt(11));		
			}
			
			
		} catch (ClassNotFoundException e) {
			System.out.println("ERROR : from FansDao.getAttentionsList : 打开数据库失�?");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("ERROR : from FansDao.getAttentionsList : 数据库查询失�?");
			e.printStackTrace();
		} finally{
			
		}
		
		
		return user;
		
	}

}
