package org.turings.index.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.turings.DbUtil;
import org.turings.index.entity.Course;

public class CourseDao {
	public List<Course> changeCourses() throws SQLException {
		List<Course> list = new ArrayList<Course>();
		DbUtil dbUtil = DbUtil.getInstance();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Connection connection = null;
		int a =(int) Math.floor(Math.random()*10+1);
		try {
			connection = dbUtil.getConnection();
			String sql = "";
			sql = "select * from tbl_indexcourse order by courseViews  limit ?,?";
			pstm = connection.prepareStatement(sql);
			pstm.setInt(1, a);
			pstm.setInt(2, a+1);	
			rs = pstm.executeQuery();
			while (rs.next()) {
				Course course = new Course(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5));
				list.add(course);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			dbUtil.closeConnection();
		}
	}
	
	
	public List<Course> scanMostPupularCourses() throws SQLException {
		List<Course> list = new ArrayList<Course>();
		DbUtil dbUtil = DbUtil.getInstance();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Connection connection = null;
		try {
			connection = dbUtil.getConnection();
			String sql = "";
			sql = "select * from tbl_indexcourse order by  courseviews desc limit 0,2";
			pstm = connection.prepareStatement(sql);
			rs = pstm.executeQuery();
			while (rs.next()) {
				Course course = new Course(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5));
				list.add(course);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			dbUtil.closeConnection();
		}
	}
	
	public List<Course> getChildCourses(String pString) throws SQLException {
		List<Course> list = new ArrayList<Course>();
		DbUtil dbUtil = DbUtil.getInstance();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Connection connection = null;
		try {
			connection = dbUtil.getConnection();
			String sql = "";
			sql = "select * from tbl_childvideo where childbelong = ? order by childrank";
			pstm = connection.prepareStatement(sql);
			pstm.setString(1, pString);
			rs = pstm.executeQuery();
			while (rs.next()) {
				Course course = new Course(rs.getString(4));
				list.add(course);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			dbUtil.closeConnection();
		}
	}
	
	
	
}
