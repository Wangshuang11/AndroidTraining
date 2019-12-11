package org.turings.myself.course.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.turings.myself.entity.CourseInfo;
import org.turings.myself.util.DbUtil;

public class CoursesDao {

	public List<CourseInfo> getCoursesList(int id) {
		
		List<CourseInfo> list = new ArrayList<CourseInfo>();
		Connection conn = null;
		
		
		DbUtil dbUtil = DbUtil.getInstance();
		System.out.println("金鑫�?-getFansList"+"打开数据�?");
		
		try {
			conn = dbUtil.getConnection();
			String sql = "select * from tbl_indexcourse where parentid in (select cId from tbl_self_courses_my where uId= ?)";
			
			
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setInt(1, id);
			ResultSet rs=pstm.executeQuery();
			CourseInfo CourseInfo;
			
			while(rs.next()) {
				// 在这里把查到的学校加入list
				CourseInfo = new CourseInfo(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getInt(6));
				list.add(CourseInfo);
				
				
			}
			
			
		} catch (ClassNotFoundException e) {
			System.out.println("ERROR : from FansDao.getAttentionsList : 打开数据库失�?");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("ERROR : from FansDao.getAttentionsList : 数据库查询失�?");
			e.printStackTrace();
		} finally{
			
			try {
				dbUtil.closeConnection();
				System.out.println("金鑫�?-getList"+"关闭数据�?");
			} catch (SQLException e) {
				System.out.println("金鑫�?-getList"+"数据库关闭异�?");
			}
		}
		
		
		return list;
	}

	
}
