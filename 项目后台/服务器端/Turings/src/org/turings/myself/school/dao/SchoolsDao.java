package org.turings.myself.school.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.turings.myself.entity.SchoolInfo;
import org.turings.myself.util.DbUtil;

public class SchoolsDao {

	public List<SchoolInfo> getSchoolsList(int id) {
		
		List<SchoolInfo> list = new ArrayList<SchoolInfo>();
		Connection conn = null;
		
		
		DbUtil dbUtil = DbUtil.getInstance();
		System.out.println("金鑫媛-getFansList"+"打开数据库");
		
		try {
			conn = dbUtil.getConnection();
			String sql = "select * from tbl_indexcollege where id IN (select sId from tbl_self_schools_favorite where uId= ?)";
			
			
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setInt(1, id);
			ResultSet rs=pstm.executeQuery();
			SchoolInfo schoolInfo;
			
			while(rs.next()) {
				// 在这里把查到的学校加入list
				schoolInfo = new SchoolInfo(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6));
				list.add(schoolInfo);
				
				
			}
			
			
		} catch (ClassNotFoundException e) {
			System.out.println("ERROR : from FansDao.getAttentionsList : 打开数据库失败");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("ERROR : from FansDao.getAttentionsList : 数据库查询失败");
			e.printStackTrace();
		} finally{
			
			try {
				dbUtil.closeConnection();
				System.out.println("金鑫媛-getList"+"关闭数据库");
			} catch (SQLException e) {
				System.out.println("金鑫媛-getList"+"数据库关闭异常");
			}
		}
		
		
		return list;
	}

}
